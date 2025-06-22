package com.example.transactionservice.transaction.event;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransactionFinishedEvent extends DomainEvent {
    private String id;
    private String status;
    private String message;

    public TransactionFinishedEvent(String id, String status, String message) {
        this.id = id;
        this.status = status;
        this.message = message;
    }

    @Override
    public String getEventType() {
        return "transaction.finished";
    }
}
