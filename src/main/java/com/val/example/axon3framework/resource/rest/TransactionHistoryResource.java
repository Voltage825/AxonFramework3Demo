package com.val.example.axon3framework.resource.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.val.example.axon3framework.entities.TransactionHistory;
import com.val.example.axon3framework.repositories.TransactionHistoryRepository;

@ProcessingGroup("TransactionHistory")
@RestController
@RequestMapping(value = "/TransactionHistory")
public class TransactionHistoryResource {
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public TransactionHistoryResource(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @RequestMapping(value = "/{accountId}", method = GET)
    @ResponseStatus(OK)
    public List<TransactionHistory> getTransactions(@PathVariable String accountId) {
        return transactionHistoryRepository.findAllByAccountId(accountId);
    }
}
