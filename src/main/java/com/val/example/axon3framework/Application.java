package com.val.example.axon3framework;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import com.val.example.axon3framework.coreapi.CreateAccountCommand;
import com.val.example.axon3framework.domain.account.Account;
import com.val.example.axon3framework.domain.transfer.MoneyTransfer;
import com.val.example.axon3framework.domain.transfer.MoneyTransferSaga;
import com.val.example.axon3framework.eventhandlers.LoggingEventHandler;
import com.val.example.axon3framework.transferapi.RequestMoneyTransferCommand;

public class Application {
    // Plain Java
    public static void main(String[] args) {
        Configuration config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Account.class)
                .configureAggregate(MoneyTransfer.class)
                .registerModule(SagaConfiguration.subscribingSagaManager(MoneyTransferSaga.class))
                .registerModule(new EventHandlingConfiguration().registerEventHandler(configuration -> new LoggingEventHandler()))
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .buildConfiguration();

        config.start();
        // config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("54321", 500)));
        // config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("54321", "tx1", 250)));

        config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("1", 500)), LoggingCallback.INSTANCE);
        config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("2", 500)), LoggingCallback.INSTANCE);

        config.commandBus().dispatch(asCommandMessage(new RequestMoneyTransferCommand("tf1", "1", "2", 100)), LoggingCallback.INSTANCE);
    }
}
