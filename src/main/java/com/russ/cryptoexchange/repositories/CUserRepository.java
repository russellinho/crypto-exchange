package com.russ.cryptoexchange.repositories;

import com.russ.cryptoexchange.domains.CUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CUserRepository extends JpaRepository<CUser, String> {
    CUser findByEmail(String email);
}
