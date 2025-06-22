package com.example.transactionservice.transaction.event;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransactionUpdatedEvent extends DomainEvent {
    private String id;
    private String status;

    public TransactionUpdatedEvent(String id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String getEventType() {
        return "transaction.updated";
    }
}
