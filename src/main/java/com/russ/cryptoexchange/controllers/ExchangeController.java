package com.russ.cryptoexchange.controllers;

import org.springframework.stereotype.Controller;

import java.util.List;

import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.domains.Coin;
import com.russ.cryptoexchange.domains.Wallet;
import com.russ.cryptoexchange.services.CoinService;
import com.russ.cryptoexchange.services.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExchangeController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private CoinService coinService;

    @PostMapping("/exchange")
    public String getExchange(@ModelAttribute("user") CUser cUser, Model model) {
        Wallet myWallet = null;
        if (cUser.getWalletAddress() != null) {
            myWallet = walletService.loadWallet(cUser.getWalletAddress());
        }
        List<Coin> coins = coinService.loadExchange();
        model.addAttribute("user", cUser);
        model.addAttribute("wallet", myWallet);
        for (Coin coin : coins) {
            model.addAttribute(coin.getName(), coin);
        }
        return "exchange";
    }
}
