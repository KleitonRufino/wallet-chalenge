package com.example.transactionservice.transaction.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransactionCreatedEvent extends DomainEvent {
    private String id;
    private String fromWalletId;
    private String toWalletId;
    private BigDecimal amount;
    private String type;
    private LocalDateTime date;
    private String status;

    public TransactionCreatedEvent(String id, String fromWalletId, String toWalletId, BigDecimal amount, String type,
                                   LocalDateTime date, String status) {
        this.id = id;
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.status = status;
    }

    @Override
    public String getEventType() {
        return "transaction.created";
    }
}
