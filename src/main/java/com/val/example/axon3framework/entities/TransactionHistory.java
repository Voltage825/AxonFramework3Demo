package com.val.example.axon3framework.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String accountId;

    @NonNull
    private int amount;

    @NonNull
    private String transactionId;
}
