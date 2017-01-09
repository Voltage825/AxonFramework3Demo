package com.val.example.axon3framework.resource.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.val.example.axon3framework.transferapi.RequestMoneyTransferCommand;

@RestController
@RequestMapping(value = "/TransferMoney")
public class TransferMoneyResource {

    private CommandGateway commandGateway;

    @Autowired
    public TransferMoneyResource(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(value = "/simple", method = GET)
    @ResponseStatus(OK)
    public String getTransactions(
            @RequestParam(name = "sourceId") String sourceId,
            @RequestParam(name = "destinationId") String destinationId,
            @RequestParam(name = "amount") int amount) {
        String log = String.format("%d :%s->%s", amount, sourceId, destinationId);
        System.out.println(log);

        commandGateway.send(new RequestMoneyTransferCommand(UUID.randomUUID().toString(), sourceId, destinationId, amount));
        return log;
    }
}
