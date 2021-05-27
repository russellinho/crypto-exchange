package com.russ.cryptoexchange.controllers;

import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.domains.Wallet;
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
        return "home";
    }

    @PostMapping("/createWallet")
    public void createWalletForUser(@ModelAttribute("user") CUser cUser, Model model) {
        this.walletService.createWalletForUser(cUser.getEmail());
        // return "home";
    }
}
