package com.example.walletservice.wallet.wallet.domain.event;

import java.util.List;

import com.example.walletservice.wallet.wallet.domain.vo.BalanceVO;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WalletCreatedEvent extends DomainEvent {
    private String id;
    private String userId;
    private BalanceVO balance;
    private List<BalanceVO> balanceHistory;

    public WalletCreatedEvent(String id, String userId, BalanceVO balance, List<BalanceVO> balanceHistory) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.balanceHistory = balanceHistory;
    }

    @Override
    public String getEventType() {
        return "wallet.created";
    }
}
