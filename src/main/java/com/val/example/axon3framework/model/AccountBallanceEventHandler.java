package com.val.example.axon3framework.model;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.val.example.axon3framework.coreapi.BalanceUpdatedEvent;
import com.val.example.axon3framework.entities.AccountBalance;
import com.val.example.axon3framework.repositories.AccountBalanceRepository;

@Component
public class AccountBallanceEventHandler {

    private final AccountBalanceRepository accountBalanceRepository;

    @Autowired
    public AccountBallanceEventHandler(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    @EventHandler
    public void on(BalanceUpdatedEvent event) {
        accountBalanceRepository.save(new AccountBalance(event.getAccountId(), event.getBalance()));
    }
}
