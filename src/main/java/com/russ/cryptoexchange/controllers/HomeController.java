package com.russ.cryptoexchange.controllers;

import java.util.List;

import javax.xml.bind.ValidationException;

import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.domains.Coin;
import com.russ.cryptoexchange.domains.DepositForm;
import com.russ.cryptoexchange.domains.ReceiveForm;
import com.russ.cryptoexchange.domains.SendForm;
import com.russ.cryptoexchange.domains.TransactForm;
import com.russ.cryptoexchange.domains.Wallet;
import com.russ.cryptoexchange.domains.WithdrawForm;
import com.russ.cryptoexchange.services.CUserService;
import com.russ.cryptoexchange.services.CoinService;
import com.russ.cryptoexchange.services.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private CUserService cUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CoinService coinService;

    @GetMapping("/")
    public String getCurrentUser(@AuthenticationPrincipal User user, Model model) {
        String username = user.getUsername();
        CUser myUser = cUserService.loadCUser(username);
        Wallet myWallet = null;
        if (myUser.getWalletAddress() != null) {
            myWallet = walletService.loadWallet(myUser.getWalletAddress());
        }
        model.addAttribute("username", username);
        model.addAttribute("user", myUser);
        model.addAttribute("wallet", myWallet);
        model.addAttribute("depositForm", new DepositForm());
        model.addAttribute("withdrawForm", new WithdrawForm());
        model.addAttribute("transactForm", new TransactForm(myUser, myWallet));
        model.addAttribute("sendForm", new SendForm(myUser, myWallet, null, null));
        model.addAttribute("receiveForm", new ReceiveForm(myUser));
        return "home";
    }

    @PostMapping("/createWallet")
    public String createWalletForUser(@ModelAttribute("user") CUser cUser, Model model) {
        this.walletService.createWalletForUser(cUser.getEmail());
        return "redirect:/";
    }

    @PostMapping("/exchange")
    public String goToExchange(@ModelAttribute("transactForm") TransactForm transactForm, Model model) {
        List<Coin> coins = coinService.loadExchange();
        model.addAttribute("transactForm", transactForm);
        for (Coin coin : coins) {
            model.addAttribute(coin.getName(), coin);
        }
        return "exchange";
    }

    @PostMapping("/deposit")
    public String depositFiatForUser(@ModelAttribute("user") CUser cUser, Model model) {
        DepositForm d = new DepositForm(cUser, null);
        model.addAttribute("depositForm", d);
        model.addAttribute("user", cUser);
        return "deposit";
    }

    @PostMapping("/withdraw")
    public String withdrawFiatForUser(@ModelAttribute("user") CUser cUser, Model model) {
        WithdrawForm w = new WithdrawForm(cUser, null);
        model.addAttribute("withdrawForm", w);
        return "withdraw";
    }

    @PostMapping("/send")
    public String sendCoin(@ModelAttribute("sendForm") SendForm sendForm, Model model) {
        model.addAttribute("sendForm", sendForm);
        return "send";
    }

    @PostMapping("receive")
    public String receiveCoin(@ModelAttribute("receiveForm") ReceiveForm receiveForm, Model model) {
        model.addAttribute("receiveForm", receiveForm);
        return "receive";
    }

    @PostMapping("/confirmSend")
    public String confirmSend(@ModelAttribute("sendForm") SendForm sendForm, Model model) {
        try {
            walletService.sendCoinToWallet(sendForm.getWallet(), sendForm.getRecipientAddress(), sendForm.getSelectedCoin(), sendForm.getAmount());
            model.addAttribute("message", sendForm.getSelectedCoin() + " sent successfully!");
        } catch (ValidationException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("error", true);
        }
        return sendCoin(sendForm, model);
    }

    @PostMapping("/confirmDeposit")
    public String confirmDeposit(@ModelAttribute("depositForm") DepositForm depositForm, Model model) {
        cUserService.depositFiatForUser(depositForm.getCUser(), depositForm.getAmount());
        model.addAttribute("message", "Deposit successful!");
        return depositFiatForUser(depositForm.getCUser(), model);
    }

    @PostMapping("/confirmWithdraw")
    public String confirmWithdraw(@ModelAttribute("withdrawForm") WithdrawForm withdrawForm, Model model) {
        try {
            cUserService.withdrawFiatForUser(withdrawForm.getCUser(), withdrawForm.getAmount());
            model.addAttribute("message", "Withdrawal successful!");
        } catch (ValidationException e) {
            model.addAttribute("message", "Withdrawal failed for the following reasons:\n" + e.getMessage());
            model.addAttribute("error", true);
        }
        return withdrawFiatForUser(withdrawForm.getCUser(), model);
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute("transactForm") TransactForm transactForm, Model model) {
        CUser user = transactForm.getCUser();
        Coin coin = coinService.getCoinBySymbol(transactForm.getSelectedCoin());
        Wallet wallet = transactForm.getWallet();
        // Determine if it's a buy or sell order
        if (transactForm.getBuy()) {
            // If it's a buy, determine if the user has enough funds to complete the purchase
            double totalCost = coin.getPrice() * transactForm.getCoinAmount();
            if (totalCost > user.getFiat()) {
                model.addAttribute("message", "Insufficient funds!");
                model.addAttribute("error", true);
                return goToExchange(transactForm, model);
            }
            // After that, determine if there's enough liquidity to complete the purchase
            if (coin.getQuantity() < transactForm.getCoinAmount()) {
                model.addAttribute("message", "There is not enough " + coin.getName() + " available to complete the transaction!");
                model.addAttribute("error", true);
                return goToExchange(transactForm, model);
            }
            try {
                // After both pass, make the transaction
                // Deduct funds from user
                cUserService.withdrawFiatForUser(user, totalCost);
                // Save new coins to wallet
                walletService.updateCoinForWallet(coin, wallet, transactForm.getCoinAmount());
                // Deduct from coin liquidity
                coinService.updateLiquidity(coin, -transactForm.getCoinAmount());
                model.addAttribute("message", "Market buy successful!");
            } catch (ValidationException e) {
                model.addAttribute("message", e.getMessage());
                model.addAttribute("error", true);
            }
        } else {
            // If it's a sell, add to the coin liquidity, remove from the origin wallet, add to the user's fiat
            if (coin.getId() == 0) {
                if (wallet.getBtc() < transactForm.getCoinAmount()) {
                    model.addAttribute("message", "You do not have enough BTC to complete this transaction.");
                    model.addAttribute("error", true);
                    return goToExchange(transactForm, model);
                }
            } else if (coin.getId() == 1) {
                if (wallet.getEth() < transactForm.getCoinAmount()) {
                    model.addAttribute("message", "You do not have enough ETH to complete this transaction.");
                    model.addAttribute("error", true);
                    return goToExchange(transactForm, model);
                }
            } else if (coin.getId() == 2) {
                if (wallet.getCel() < transactForm.getCoinAmount()) {
                    model.addAttribute("message", "You do not have enough CEL to complete this transaction.");
                    model.addAttribute("error", true);
                    return goToExchange(transactForm, model);
                }
            }
            double totalSaleAmount = coin.getPrice() * transactForm.getCoinAmount();
            try {
                cUserService.depositFiatForUser(user, totalSaleAmount);
                walletService.updateCoinForWallet(coin, wallet, -transactForm.getCoinAmount());
                coinService.updateLiquidity(coin, transactForm.getCoinAmount());
                model.addAttribute("message", "Market sell successful!");
            } catch (ValidationException e) {
                model.addAttribute("message", e.getMessage());
                model.addAttribute("error", true);
            }
        }
        return goToExchange(transactForm, model);
    }
}
