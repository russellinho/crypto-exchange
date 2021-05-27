package com.russ.cryptoexchange.controllers;

import com.russ.cryptoexchange.services.WalletService;
import com.russ.cryptoexchange.domains.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // @GetMapping
    // public Wallet getWallet() {
    //     return walletService.getWallet();
    // }
}
