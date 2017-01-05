package com.val.example.axon3framework;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import com.val.example.axon3framework.coreapi.CreateAccountCommand;
import com.val.example.axon3framework.coreapi.WithdrawMoneyCommand;
import com.val.example.axon3framework.domain.account.Account;

public class Application {
    // Plain Java
    public static void main(String[] args) {
        Configuration config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Account.class)
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .buildConfiguration();

        config.start();
        config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("54321", 500)));
        config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("54321", 250)));
    }
}
