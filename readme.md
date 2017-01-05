#Axon Framework 3.0 with Spring Boot
---
###Credit - Getting started with Axon 3 Live coding sessions 1-3
**by**: [Trifork Webinar Channel](https://www.youtube.com/channel/UCz9eNSe8kY7z8DEyvv-slZg)

I followed these three videos and added my own spin as I wanted:


 * [Getting started with Axon 3 Live coding 1](https://www.youtube.com/watch?v=s2zH7BsqtAk)
 * [Getting started with Axon 3 Live coding 2](https://www.youtube.com/watch?v=Fj365BufWNU)
 * [Getting started with Axon 3 Live coding 3](ttps://www.youtube.com/watch?v=qqk2Df_0Pm8)
 
 ___
 ## Maven:
 
 In my maven pom I added these two dependencies rather than the milestones:
 
 ```XML
 ...
<dependency>
	<groupId>org.axonframework</groupId>
	<artifactId>axon-core</artifactId>
	<version>3.0</version>
</dependency>
<dependency>
	<groupId>org.axonframework</groupId>
	<artifactId>axon-test</artifactId>
	<version>3.0</version>
</dependency>
<dependency>
	<groupId>org.axonframework</groupId>
	<artifactId>axon-spring-boot-autoconfigure</artifactId>
	<version>3.0</version>
</dependency>
...
 ```

**(December 30th 2016) Version 3.0 had just come out so it makes sense to use the latest.**
___

## Differences
**What I had to change to make things work**

###Video 1
1. The way you instantiate an Aggregate fixture is not:
``` Java
...
    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Account.class);
    }
...
```
And is
``` Java
...
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
...

    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture(Account.class);
    }
```
2. You no longer need to use the annotation @EnableAxonAutoConfiguration on your Spring Boot Application class. The `axon-spring-boot-autoconfigure` dependency now takes care of that.
3. When testing instead of placing the "test code" in the main class, I create a proper Integration test and check that the context runs:
```Java
    @Test
    public void when_startingUpTheApplicationContext_expect_CommandBussuccessful() throws SQLException {
        CommandBus commandBus = context.getBean(CommandBus.class);
        assertNotNull("Application context not initialised successfully", context);
        assertNotNull("Application CommandBus not initialised successfully", commandBus);

        commandBus.dispatch(asCommandMessage(new CreateAccountCommand("54321", 500)));
        commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("54321", 250)));
    }
```
4. I do not implement the AsynchronousCommandBus because at this point we don't need it. However here is the bean code you will need to add to your config:
```Java
    @Bean
    public CommandBus getCommandBus() {
        return new AsynchronousCommandBus();
    }
```

###Video 2
1. The way you instantiate a Saga fixture is not:
``` Java
...
    private FixtureConfiguration fixture;
    
    @Before
    public void setUp() {
        fixture = new AnnotatedSagaTestFixture<>(MoneyTransfer.class);
    }
...
```
And is
``` Java
...
import org.axonframework.test.saga.FixtureConfiguration;
import org.axonframework.test.saga.SagaTestFixture;
...

    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new SagaTestFixture<>(MoneyTransfer.class);
    }
```