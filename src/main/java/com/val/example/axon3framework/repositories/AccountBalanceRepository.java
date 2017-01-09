package com.val.example.axon3framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.val.example.axon3framework.entities.AccountBalance;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {
}
