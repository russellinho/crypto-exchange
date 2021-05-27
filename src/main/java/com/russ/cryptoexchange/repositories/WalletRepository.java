package com.russ.cryptoexchange.repositories;

import com.russ.cryptoexchange.domains.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
    
}
