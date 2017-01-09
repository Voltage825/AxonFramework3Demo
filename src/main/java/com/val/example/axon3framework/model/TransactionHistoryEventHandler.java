package com.val.example.axon3framework.model;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.val.example.axon3framework.coreapi.DepositedMoneyEvent;
import com.val.example.axon3framework.coreapi.MoneyWithdrawnEvent;
import com.val.example.axon3framework.entities.TransactionHistory;
import com.val.example.axon3framework.repositories.TransactionHistoryRepository;

@Component
@ProcessingGroup("TransactionHistory")
public class TransactionHistoryEventHandler {

    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public TransactionHistoryEventHandler(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @EventHandler
    public void on(DepositedMoneyEvent event) {
        transactionHistoryRepository.save(new TransactionHistory(event.getAccountId(), event.getAmount(), event.getTransactionId()));
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
        transactionHistoryRepository.save(new TransactionHistory(event.getAccountId(), -event.getAmount(), event.getTransactionId()));
    }
}
