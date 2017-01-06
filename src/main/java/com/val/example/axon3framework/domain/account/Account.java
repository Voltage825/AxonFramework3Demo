package com.val.example.axon3framework.domain.account;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.val.example.axon3framework.coreapi.AccountCreatedEvent;
import com.val.example.axon3framework.coreapi.CreateAccountCommand;
import com.val.example.axon3framework.coreapi.DepositMoneyCommand;
import com.val.example.axon3framework.coreapi.DepositedMoneyEvent;
import com.val.example.axon3framework.coreapi.MoneyWithdrawnEvent;
import com.val.example.axon3framework.coreapi.WithdrawMoneyCommand;
import com.val.example.axon3framework.domain.account.exceptions.OverdraftLimitExceededException;

import lombok.NoArgsConstructor;

@Aggregate(repository = "jpaAccountRepository")
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @AggregateIdentifier
    private String accountId;

    @Basic
    private int balance;

    @Basic
    private int overdraftLimit;

    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getOverdraftLimit()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command) throws OverdraftLimitExceededException {
        if ((balance + overdraftLimit) >= command.getAmount()) {
            apply(new MoneyWithdrawnEvent(this.accountId, command.getTransactionId(), command.getAmount(), balance - command.getAmount()));
            // [1.1]Never do state change in command handler
        } else {
            throw new OverdraftLimitExceededException();
        }
    }

    @CommandHandler
    public void handle(DepositMoneyCommand command) {
        apply(new DepositedMoneyEvent(this.accountId, command.getTransactionId(), command.getAmount(), balance + command.getAmount()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.overdraftLimit = event.getOverdraftLimit();
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event) {
        this.balance = event.getBalance();
        // [1.2] assuming that our calculations are right in the command handler... we can apply the state change here
    }

    @EventSourcingHandler
    public void on(DepositedMoneyEvent event) {
        this.balance = event.getBalance();
    }
}
