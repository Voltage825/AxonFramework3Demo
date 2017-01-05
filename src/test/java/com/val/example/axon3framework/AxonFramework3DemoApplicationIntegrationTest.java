package com.val.example.axon3framework;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.axonframework.commandhandling.CommandBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.val.example.axon3framework.coreapi.CreateAccountCommand;
import com.val.example.axon3framework.coreapi.WithdrawMoneyCommand;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AxonFramework3DemoApplicationIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void when_startingUpTheApplicationContext_expect_successful() throws SQLException {
        assertNotNull("Application context not initialised successfully", context);
    }

    @Test
    public void when_startingUpTheApplicationContext_expect_CommandBussuccessful() throws SQLException {
        CommandBus commandBus = context.getBean(CommandBus.class);
        assertNotNull("Application context not initialised successfully", context);
        assertNotNull("Application CommandBus not initialised successfully", commandBus);

        commandBus.dispatch(asCommandMessage(new CreateAccountCommand("54321", 500)));
        commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("54321", 250)));
    }
}
