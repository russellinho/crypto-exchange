package com.russ.cryptoexchange.controllers;

import com.russ.cryptoexchange.services.CUserService;
import com.russ.cryptoexchange.domains.CUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "cuser")
public class CUserController {
    private final CUserService cUserService;

    @Autowired
    public CUserController(CUserService cUserService) {
        this.cUserService = cUserService;
    }

}
