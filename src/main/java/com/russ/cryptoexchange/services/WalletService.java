package com.russ.cryptoexchange.services;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.russ.cryptoexchange.domains.Wallet;
import com.russ.cryptoexchange.repositories.CUserRepository;
import com.russ.cryptoexchange.repositories.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.domains.Coin;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    @Autowired
    private CUserRepository cUserRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWalletForUser(String email) {
        Wallet w = new Wallet();
        walletRepository.save(w);
        CUser myUser = cUserRepository.findByEmail(email);
        myUser.setWalletAddress(w.getAddress());
        cUserRepository.save(myUser);
    }

    public Wallet loadWallet(String address) {
        Optional<Wallet> myWallet = walletRepository.findById(address);
        Wallet loadedWallet = null;
        try {
            loadedWallet = myWallet.get();
        } catch (NoSuchElementException e) {
            loadedWallet = null;
        }
        return loadedWallet;
    }
}
