package com.example.query.transaction.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionCreatedEvent {

	private String id;
	private String fromWalletId;
	private String toWalletId;
	private BigDecimal amount;
	private String type;
	private LocalDateTime date;
	private String status;

}
