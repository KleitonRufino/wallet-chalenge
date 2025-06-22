package com.example.walletservice.wallet.transaction.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.walletservice.wallet.wallet.domain.event.DomainEvent;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransactionTransferEvent extends DomainEvent {
    private String id;
    private String fromWalletId;
    private String toWalletId;
    private BigDecimal amount;
    private LocalDateTime date;

    public TransactionTransferEvent(String id, String fromWalletId, String toWalletId, BigDecimal amount,
            LocalDateTime date) {
        this.id = id;
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String getEventType() {
        return "transaction.transfer";
    }
}
