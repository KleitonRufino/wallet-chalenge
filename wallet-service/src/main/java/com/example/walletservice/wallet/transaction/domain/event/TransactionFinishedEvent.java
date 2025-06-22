package com.example.walletservice.wallet.transaction.domain.event;

import com.example.walletservice.wallet.wallet.domain.event.DomainEvent;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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
