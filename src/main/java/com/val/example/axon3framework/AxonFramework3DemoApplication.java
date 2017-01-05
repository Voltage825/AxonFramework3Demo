package com.val.example.axon3framework;

import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import com.val.example.axon3framework.domain.account.Account;

@SpringBootApplication
public class AxonFramework3DemoApplication {

    @Bean
    public EventStorageEngine getEventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    public Repository<Account> jpaAccountRepository(EventBus eventBus) {
        return new GenericJpaRepository<>(entityManagerProvider(), Account.class, eventBus);
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
