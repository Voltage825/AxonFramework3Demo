package com.val.example.axon3framework.domain.transfer;

import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.val.example.axon3framework.coreapi.DepositMoneyCommand;
import com.val.example.axon3framework.coreapi.DepositedMoneyEvent;
import com.val.example.axon3framework.coreapi.MoneyWithdrawnEvent;
import com.val.example.axon3framework.coreapi.WithdrawMoneyCommand;
import com.val.example.axon3framework.transferapi.CancelMoneyTransferCommand;
import com.val.example.axon3framework.transferapi.CompleteMoneyTransferCommand;
import com.val.example.axon3framework.transferapi.MoneyTransferCancelledEvent;
import com.val.example.axon3framework.transferapi.MoneyTransferCompletedEvent;
import com.val.example.axon3framework.transferapi.RequestedMoneyTransferEvent;

// @Saga(sagaStore = "sagaStore") //to use your own saga store
@Saga
public class MoneyTransferSaga {

    @Inject
    private transient CommandGateway commandGateway;

    private String transferId;
    private String targetAccount;

    @StartSaga(forceNew = true)
    @SagaEventHandler(associationProperty = "transferId")
    public void on(RequestedMoneyTransferEvent event) {
        targetAccount = event.getTargetAccount();
        transferId = event.getTransferId();
        // try {
        // commandGateway.sendAndWait(new WithdrawMoneyCommand(event.getSourceAccount(), event.getTransferId(),
        // event.getAmount()));
        // } catch (CommandExecutionException e) {
        // if (OverdraftLimitExceededException.class.isInstance(e.getCause())) {
        // commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
        // }
        // e.printStackTrace();
        // }

        // or

        commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), transferId, event.getAmount()), new CommandCallback<WithdrawMoneyCommand, java.lang.Object>() {
            @Override
            public void onSuccess(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, java.lang.Object o) {
                // we just responding here
            }

            @Override
            public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable throwable) {
                commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));

            }
        });
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName = "transferId")
    public void on(MoneyWithdrawnEvent event) {
        commandGateway.send(new DepositMoneyCommand(targetAccount, event.getTransactionId(), event.getAmount()), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName = "transferId")
    public void on(DepositedMoneyEvent event) {
        commandGateway.send(new CompleteMoneyTransferCommand(transferId), LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCompletedEvent event) {
    }

    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCancelledEvent event) {
        // or import static org.axonframework.eventhandling.saga.SagaLifecycle.end;
        end();
    }

}
