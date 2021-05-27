package com.russ.cryptoexchange.services;

import java.util.ArrayList;
import com.russ.cryptoexchange.domains.CUser;
import com.russ.cryptoexchange.repositories.CUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.russ.cryptoexchange.domains.Coin;

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
}
