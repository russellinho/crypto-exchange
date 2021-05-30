package com.russ.cryptoexchange.controllers;

import javax.xml.bind.ValidationException;

import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.domains.DepositForm;
import com.russ.cryptoexchange.domains.Wallet;
import com.russ.cryptoexchange.domains.WithdrawForm;
import com.russ.cryptoexchange.services.CUserService;
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
        return "home";
    }

    @PostMapping("/createWallet")
    public void createWalletForUser(@ModelAttribute("user") CUser cUser, Model model) {
        this.walletService.createWalletForUser(cUser.getEmail());
        // return "home";
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
}
