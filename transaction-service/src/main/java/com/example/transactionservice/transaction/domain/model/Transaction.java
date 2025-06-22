package com.example.transactionservice.transaction.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class Transaction {
	private String id;
	private String fromWalletId;
	private String toWalletId;
	private TransactionTypeEnum type;
	private BigDecimal amount;
	private LocalDateTime date;
	private TransactionStatusEnum status;

	public Transaction(String id, String fromWalletId, String toWalletId, TransactionTypeEnum type, BigDecimal amount) {
		this.id = id;
		this.fromWalletId = fromWalletId;
		this.toWalletId = toWalletId;
		this.type = type;
		this.amount = amount;
		this.date = LocalDateTime.now();
		this.status = TransactionStatusEnum.PENDING;
	}

	public void changeStatus(TransactionStatusEnum status) {
		this.status = status;
	}
}
