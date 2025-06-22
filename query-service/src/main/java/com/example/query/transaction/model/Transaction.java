package com.example.query.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transaction {

	private String id;
	private String fromWalletId;
	private String toWalletId;
	private BigDecimal amount;
	private String type;  // Should be "TRANSFER", "DEPOSITT", or "WITHDRAW"
	private LocalDateTime date;
	private String status;  // Should be "PENDING", "COMPLETED", or "FAILED"

}
