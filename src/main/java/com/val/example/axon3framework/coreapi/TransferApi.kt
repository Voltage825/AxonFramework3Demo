package com.`val`.example.axon3framework.transferapi

import org.axonframework.commandhandling.TargetAggregateIdentifier

class RequestMoneyTransferCommand(val transferId: String, val sourceAccount: String, val targetAccount: String)
class CompleteMoneyTransferCommand(@TargetAggregateIdentifier val transferId: String)
class CancelMoneyTransferCommand(@TargetAggregateIdentifier val transferId: String)

class RequestedMoneyTransferEvent(val transferId: String, val sourceAccount: String, val targetAccount: String)
class MoneyTransferCompletedEvent(val transferId: String)
class MoneyTransferCancelledEvent(val transferId: String)

