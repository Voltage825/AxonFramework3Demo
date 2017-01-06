package com.val.example.axon3framework.domain.transfer;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.val.example.axon3framework.transferapi.CancelMoneyTransferCommand;
import com.val.example.axon3framework.transferapi.CompleteMoneyTransferCommand;
import com.val.example.axon3framework.transferapi.MoneyTransferCancelledEvent;
import com.val.example.axon3framework.transferapi.MoneyTransferCompletedEvent;
import com.val.example.axon3framework.transferapi.RequestMoneyTransferCommand;
import com.val.example.axon3framework.transferapi.RequestedMoneyTransferEvent;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Aggregate(repository = "jpaMoneyTransferRepository")
@Entity
public class MoneyTransfer {
    @Id
    @AggregateIdentifier
    private String transferId;

    @CommandHandler
    public MoneyTransfer(RequestMoneyTransferCommand command) {
        apply(new RequestedMoneyTransferEvent(command.getTransferId(), command.getSourceAccount(), command.getTargetAccount(), command.getAmount()));
    }

    @CommandHandler
    public void handle(CompleteMoneyTransferCommand command) {
        apply(new MoneyTransferCompletedEvent(command.getTransferId()));
    }

    @CommandHandler
    public void handle(CancelMoneyTransferCommand command) {
        apply(new MoneyTransferCancelledEvent(command.getTransferId()));
    }

    @EventSourcingHandler
    public void on(RequestedMoneyTransferEvent event) {
        this.transferId = event.getTransferId();
    }

    @EventSourcingHandler
    public void on(MoneyTransferCompletedEvent event) {
        markDeleted();
    }
}
