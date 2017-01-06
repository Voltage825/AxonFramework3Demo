package com.val.example.axon3framework;

import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.saga.AbstractSagaManager;
import org.axonframework.eventhandling.saga.AnnotatedSagaManager;
import org.axonframework.eventhandling.saga.SagaRepository;
import org.axonframework.eventhandling.saga.repository.AnnotatedSagaRepository;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.jpa.JpaSagaStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.axonframework.spring.saga.SpringResourceInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import com.val.example.axon3framework.domain.account.Account;
import com.val.example.axon3framework.domain.transfer.MoneyTransfer;
import com.val.example.axon3framework.domain.transfer.MoneyTransferSaga;

@SpringBootApplication
public class AxonFramework3DemoApplication {

    @Autowired
    private SagaStore<Object> sagaStore;

    @Bean
    public SagaStore<Object> sagaStore() {
        return new JpaSagaStore(entityManagerProvider());
    }

    @Bean
    public SpringResourceInjector resourceInjector() {
        return new SpringResourceInjector();
    }

    @Bean
    public SagaRepository<MoneyTransferSaga> moneyTransferSagaRepository() {
        return new AnnotatedSagaRepository<>(MoneyTransferSaga.class, sagaStore, resourceInjector());
    }

    @Bean
    public AbstractSagaManager<MoneyTransferSaga> moneyTransferSagaManager() {
        return new AnnotatedSagaManager<>(
                MoneyTransferSaga.class,
                moneyTransferSagaRepository());
    }

    @Bean
    public EventStorageEngine getEventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    public Repository<Account> jpaAccountRepository(EventBus eventBus) {
        return new GenericJpaRepository<>(entityManagerProvider(), Account.class, eventBus);
    }

    @Bean
    public Repository<MoneyTransfer> jpaMoneyTransferRepository(EventBus eventBus) {
        return new GenericJpaRepository<>(entityManagerProvider(), MoneyTransfer.class, eventBus);
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    public static void main(String[] args) {
        SpringApplication.run(AxonFramework3DemoApplication.class, args);
    }
}
