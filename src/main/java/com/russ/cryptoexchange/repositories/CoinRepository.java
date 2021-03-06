package com.russ.cryptoexchange.repositories;

import com.russ.cryptoexchange.domains.Coin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {
    Coin findByName(String name);
}
