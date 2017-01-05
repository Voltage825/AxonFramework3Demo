package com.val.example.axon3framework.domain.transfer;

import org.axonframework.test.saga.FixtureConfiguration;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

public class MoneyTransferSagaTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new SagaTestFixture<>(MoneyTransfer.class);
    }

    @Test
    public void when_given_something_expect_something() throws Exception {
        // fixture.givenNoPriorActivity()
        // .whenPublishingA(new RequestedMoneyTransferEvent("tf1","acc1","acc2",100))
        // .expectActiveSagas(1)
        // .expectDispatchedCommandsMatching(Matchers.equalTo())

    }
}
