package com.example.transactionservice.transaction.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransactionWithdrawEvent extends DomainEvent {
    private String id;
    private String walletId;
    private BigDecimal amount;
    private LocalDateTime date;

    public TransactionWithdrawEvent(String id, String walletId, BigDecimal amount, LocalDateTime date) {
        this.id = id;
        this.walletId = walletId;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String getEventType() {
        return "transaction.withdraw";
    }
}
