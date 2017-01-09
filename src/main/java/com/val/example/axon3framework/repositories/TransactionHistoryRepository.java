package com.val.example.axon3framework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.val.example.axon3framework.entities.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    public List<TransactionHistory> findAllByAccountId(String accountId);
}
