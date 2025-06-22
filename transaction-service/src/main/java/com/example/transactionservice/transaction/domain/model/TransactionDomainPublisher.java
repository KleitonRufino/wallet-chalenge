package com.example.transactionservice.transaction.domain.model;

import com.example.transactionservice.transaction.event.DomainEvent;

public interface TransactionDomainPublisher {
    void publish(DomainEvent event);
}
