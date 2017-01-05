package com.val.example.axon3framework.domain.account;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.val.example.axon3framework.coreapi.AccountCreatedEvent;
import com.val.example.axon3framework.coreapi.CreateAccountCommand;
import com.val.example.axon3framework.coreapi.MoneyWithdrawnEvent;
import com.val.example.axon3framework.coreapi.WithdrawMoneyCommand;
import com.val.example.axon3framework.domain.account.exceptions.OverdraftLimitExceededException;

public class AccountTest {

    public static final String ACCOUNT_ID = "1234";
    public static final int OVERDRAFT_LIMIT = 1_000;
    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture(Account.class);
    }

    @Test
    public void when_createAccountCommandGiven_expect_accountCreatedEvent() throws Exception {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand(ACCOUNT_ID, OVERDRAFT_LIMIT))
                .expectEvents(new AccountCreatedEvent(ACCOUNT_ID, OVERDRAFT_LIMIT));
    }

    @Test
    public void when_withdrawingMoney_expect_moneyWithdrawnEvent() throws Exception {
        fixture.given(new AccountCreatedEvent(ACCOUNT_ID, OVERDRAFT_LIMIT))
                .when(new WithdrawMoneyCommand(ACCOUNT_ID, 600))
                .expectEvents(new MoneyWithdrawnEvent(ACCOUNT_ID, 600, -600));

    }

    @Test
    public void when_withdrawingTooMuchMoney_expect_OverdraftLimitExceededException() throws Exception {
        fixture.given(new AccountCreatedEvent(ACCOUNT_ID, OVERDRAFT_LIMIT))
                .when(new WithdrawMoneyCommand(ACCOUNT_ID, 1001))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }

    @Test
    public void when_withdrawingTooMuchMoneyBySecondWithdrawal_expect_OverdraftLimitExceededException() throws Exception {
        fixture.given(new AccountCreatedEvent(ACCOUNT_ID, OVERDRAFT_LIMIT), new MoneyWithdrawnEvent(ACCOUNT_ID, 999, -999))
                .when(new WithdrawMoneyCommand(ACCOUNT_ID, 2))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }
}
