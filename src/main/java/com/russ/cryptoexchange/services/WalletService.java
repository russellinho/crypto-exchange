package com.russ.cryptoexchange.services;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.xml.bind.ValidationException;

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

    public void updateCoinForWallet(Coin coin, Wallet wallet, double amount) throws ValidationException {
        if (coin.getId() == 0) {
            double newAmount = wallet.getBtc() + amount;
            if (newAmount < 0) {
                throw new ValidationException("You do not have enough BTC to send this amount.");
            }
            wallet.setBtc(newAmount);
        } else if (coin.getId() == 1) {
            double newAmount = wallet.getEth() + amount;
            if (newAmount < 0) {
                throw new ValidationException("You do not have enough ETH to send this amount.");
            }
            wallet.setEth(newAmount);
        } else if (coin.getId() == 2) {
            double newAmount = wallet.getCel() + amount;
            if (newAmount < 0) {
                throw new ValidationException("You do not have enough CEL to send this amount.");
            }
            wallet.setCel(newAmount);
        }
        walletRepository.save(wallet);
    }

    public void sendCoinToWallet(Wallet fromWallet, String toWalletAddress, String coinName, double amount) throws ValidationException {
        if (amount <= 0) {
            throw new ValidationException("Amounts sent must be greater than 0.");
        }
        Optional<Wallet> toWallet = walletRepository.findById(toWalletAddress);
        Wallet loadedWallet = null;
        try {
            loadedWallet = toWallet.get();
        } catch (NoSuchElementException e) {
            throw new ValidationException("Recipient address was not found!");
        }
        // Check if you have sufficient funds
        double myAmount = 0;
        if (coinName.equals("BTC")) {
            myAmount = fromWallet.getBtc();
            if (amount > myAmount) {
                throw new ValidationException("You do not have enough " + coinName + ".");
            }
            loadedWallet.setBtc(loadedWallet.getBtc() + amount);
            fromWallet.setBtc(fromWallet.getBtc() - amount);
        } else if (coinName.equals("ETH")) {
            myAmount = fromWallet.getEth();
            if (amount > myAmount) {
                throw new ValidationException("You do not have enough " + coinName + ".");
            }
            loadedWallet.setEth(loadedWallet.getEth() + amount);
            fromWallet.setEth(fromWallet.getEth() - amount);
        } else if (coinName.equals("CEL")) {
            myAmount = fromWallet.getCel();
            if (amount > myAmount) {
                throw new ValidationException("You do not have enough " + coinName + ".");
            }
            loadedWallet.setCel(loadedWallet.getCel() + amount);
            fromWallet.setCel(fromWallet.getCel() - amount);
        }
        walletRepository.save(fromWallet);
        walletRepository.save(loadedWallet);
    }
}
