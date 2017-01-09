package com.val.example.axon3framework.configuration;

import org.axonframework.config.EventHandlingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    @Autowired
    public void configure(EventHandlingConfiguration configuration) {
        configuration.registerTrackingProcessor("TransactionHistory");
    }
    // Below is sample code on how you would register your own SagaStore, JpaStore... etc
    // @Autowired
    // private SagaStore<Object> sagaStore;
    //
    // @Autowired
    // private TransactionManager transactionManager;
    //
    // @PersistenceContext
    // private EntityManager entityManager;
    //
    // @Bean
    // public SagaStore<Object> sagaStore() {
    // return new JpaSagaStore(entityManagerProvider());
    // }
    //
    // @Bean
    // public SpringResourceInjector resourceInjector() {
    // return new SpringResourceInjector();
    // }
    //
    // @Bean
    // public SagaRepository<MoneyTransferSaga> moneyTransferSagaRepository() {
    // return new AnnotatedSagaRepository<>(MoneyTransferSaga.class, sagaStore, resourceInjector());
    // }
    //
    // @Bean
    // public AbstractSagaManager<MoneyTransferSaga> moneyTransferSagaManager() {
    // return new AnnotatedSagaManager<>(
    // MoneyTransferSaga.class,
    // moneyTransferSagaRepository());
    // }
    //
    // @Bean
    // public JpaEventStorageEngine eventStorageEngine() {
    // return new JpaEventStorageEngine(new SimpleEntityManagerProvider(entityManager), transactionManager);
    // }
    //
    // @Bean
    // public Repository<Account> jpaAccountRepository(EventBus eventBus) {
    // return new GenericJpaRepository<>(entityManagerProvider(), Account.class, eventBus);
    // }
    //
    // @Bean
    // public Repository<MoneyTransfer> jpaMoneyTransferRepository(EventBus eventBus) {
    // return new GenericJpaRepository<>(entityManagerProvider(), MoneyTransfer.class, eventBus);
    // }
    //
    // @Bean
    // public EntityManagerProvider entityManagerProvider() {
    // return new ContainerManagedEntityManagerProvider();
    // }
    //
    // @Bean
    // public TransactionManager axonTransactionManager(PlatformTransactionManager platformTransactionManager) {
    // return new SpringTransactionManager(platformTransactionManager);
    // }
    //
    // @Autowired
    // public void configure(EventHandlingConfiguration configuration) {
    // configuration.registerTrackingProcessor("TransactionHistory");
    // }
}
