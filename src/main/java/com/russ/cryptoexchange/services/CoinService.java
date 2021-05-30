package com.russ.cryptoexchange.services;

import java.util.List;

import javax.xml.bind.ValidationException;

import com.russ.cryptoexchange.domains.Coin;
import com.russ.cryptoexchange.repositories.CoinRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
    
    private final CoinRepository coinRepository;

    @Autowired
    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public List<Coin> loadExchange() {
        return coinRepository.findAll();
    }

    public void updateLiquidity(Coin coin, double amount) throws ValidationException {
        if (coin.getQuantity() < amount) {
            throw new ValidationException("There is not enough of this coin to purchase!");
        }
        coin.setQuantity(coin.getQuantity() - amount);
        coinRepository.save(coin);
    }
}
