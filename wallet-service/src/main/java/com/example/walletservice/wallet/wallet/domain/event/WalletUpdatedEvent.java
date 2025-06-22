package com.example.walletservice.wallet.wallet.domain.event;

import java.util.List;

import com.example.walletservice.wallet.wallet.domain.vo.BalanceVO;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WalletUpdatedEvent extends DomainEvent {
	private String id;
	private BalanceVO balance;
	private List<BalanceVO> balanceHistory;

	public WalletUpdatedEvent(String id, BalanceVO balance, List<BalanceVO> balanceHistory) {
		this.id = id;
		this.balance = balance;
		this.balanceHistory = balanceHistory;
	}

	@Override
	public String getEventType() {
		return "wallet.updated";
	}
}
