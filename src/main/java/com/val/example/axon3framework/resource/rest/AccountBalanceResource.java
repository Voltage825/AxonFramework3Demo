package com.val.example.axon3framework.resource.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.val.example.axon3framework.entities.AccountBalance;
import com.val.example.axon3framework.repositories.AccountBalanceRepository;

@RestController
@RequestMapping(value = "/AccountBallance")
// @RequestMapping(
// value = "/AccountBallance",
// produces = {
// APPLICATION_XML_VALUE,
// APPLICATION_JSON_VALUE
// })

public class AccountBalanceResource {

    private final AccountBalanceRepository accountBalanceRepository;

    @Autowired
    public AccountBalanceResource(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    @RequestMapping(value = "/{id}", method = GET)
    @ResponseStatus(OK)
    public AccountBalance getBallance(@PathVariable String id) {
        return accountBalanceRepository.findOne(id);
    }
}
