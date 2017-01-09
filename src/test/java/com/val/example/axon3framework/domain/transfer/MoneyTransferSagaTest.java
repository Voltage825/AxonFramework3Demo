package com.val.example.axon3framework.domain.transfer;

import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import com.val.example.axon3framework.coreapi.DepositMoneyCommand;
import com.val.example.axon3framework.coreapi.DepositedMoneyEvent;
import com.val.example.axon3framework.coreapi.MoneyWithdrawnEvent;
import com.val.example.axon3framework.coreapi.WithdrawMoneyCommand;
import com.val.example.axon3framework.transferapi.CompleteMoneyTransferCommand;
import com.val.example.axon3framework.transferapi.MoneyTransferCompletedEvent;
import com.val.example.axon3framework.transferapi.RequestedMoneyTransferEvent;

public class MoneyTransferSagaTest {

    private SagaTestFixture<MoneyTransferSaga> fixture;

    @Before
    public void setUp() {
        fixture = new SagaTestFixture<>(MoneyTransferSaga.class);
    }

    @Test
    public void when_given_RequestedMoneyTransferEvent_expect_anActiveSagaAndAWithdrawMoneyCommand() throws Exception {
        fixture.givenNoPriorActivity()
                .whenPublishingA(new RequestedMoneyTransferEvent("tf1", "acc1", "acc2", 100))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new WithdrawMoneyCommand("acc1", "tf1", 100));
    }

    @Test
    public void when_given_RequestedMoneyTransferEvent_expect_publishingAMoneyWithdrawnEventReturnsADepositMoneyCommand() throws Exception {
        fixture.givenAPublished(new RequestedMoneyTransferEvent("tf1", "acc1", "acc2", 100))
                .whenPublishingA(new MoneyWithdrawnEvent("acc1", "tf1", 100, 500))
                .expectDispatchedCommands(new DepositMoneyCommand("acc2", "tf1", 100));
    }

    @Test
    public void when_given_RequestedMoneyTransferEventAndMoneyWithdrawnEvent_expect_whenPublishingADepositedMoneyEventCompleteMoneyExpectTransferCommand() throws Exception {
        fixture.givenAPublished(new RequestedMoneyTransferEvent("tf1", "acc1", "acc2", 100))
                .andThenAPublished(new MoneyWithdrawnEvent("acc1", "tf1", 100, 500))
                .whenPublishingA(new DepositedMoneyEvent("acct2", "tf1", 100, 400))
                .expectDispatchedCommands(new CompleteMoneyTransferCommand("tf1"));
    }

    @Test
    public void when_given_RequestedMoneyTransferEventAndMoneyWithdrawnEventAndDepositedMoneyEvent_expect_whenPublishingAMoneyTransferCompletedEventExpectNoActiveSagas() throws Exception {
        fixture.givenAPublished(new RequestedMoneyTransferEvent("tf1", "acc1", "acc2", 100))
                .andThenAPublished(new MoneyWithdrawnEvent("acc1", "tf1", 100, 500))
                .andThenAPublished(new DepositedMoneyEvent("acct2", "tf1", 100, 400))
                .whenPublishingA(new MoneyTransferCompletedEvent("tf1"))
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }

}
