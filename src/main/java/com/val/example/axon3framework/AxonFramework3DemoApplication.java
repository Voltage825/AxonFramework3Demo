package com.val.example.axon3framework;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.val.example.axon3framework.coreapi.CreateAccountCommand;
import com.val.example.axon3framework.transferapi.RequestMoneyTransferCommand;

@SpringBootApplication
@EnableAxon
public class AxonFramework3DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AxonFramework3DemoApplication.class, args);
        CommandBus commandBus = context.getBean(CommandBus.class);

        commandBus.dispatch(asCommandMessage(new CreateAccountCommand("1", 500)), LoggingCallback.INSTANCE);
        commandBus.dispatch(asCommandMessage(new CreateAccountCommand("2", 500)), LoggingCallback.INSTANCE);
        commandBus.dispatch(asCommandMessage(new RequestMoneyTransferCommand("tf1", "1", "2", 100)), LoggingCallback.INSTANCE);
    }
}
