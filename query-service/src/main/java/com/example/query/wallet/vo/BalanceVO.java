package com.example.query.wallet.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BalanceVO {
	private String id;
	private BigDecimal balance;
	private LocalDateTime date;
}
