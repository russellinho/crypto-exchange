package com.russ.cryptoexchange.services;

import java.util.ArrayList;
import java.util.Optional;

import javax.xml.bind.ValidationException;

import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.repositories.CUserRepository;
import com.russ.cryptoexchange.repositories.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.russ.cryptoexchange.domains.Coin;
import com.russ.cryptoexchange.domains.Wallet;

@Service
public class CUserService {

    private final CUserRepository cUserRepository;
    
    @Autowired
    public CUserService(CUserRepository cUserRepository) {
        this.cUserRepository = cUserRepository;
    }

    public CUser loadCUser(String email) {
        return cUserRepository.findByEmail(email);
    }

    public void depositFiatForUser(CUser cUser, double amount) {
        cUser.setFiat(cUser.getFiat() + amount);
        cUserRepository.save(cUser);
    }

    public void withdrawFiatForUser(CUser cUser, double amount) throws ValidationException {
        if (amount > cUser.getFiat()) {
            throw new ValidationException("Insufficient funds! Please check your balance and try again.");
        }
        cUser.setFiat(cUser.getFiat() - amount);
        cUserRepository.save(cUser);
    }
}
