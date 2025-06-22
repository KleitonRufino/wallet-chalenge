package com.example.query.wallet.event;

import java.util.List;

import com.example.query.wallet.vo.BalanceVO;

import lombok.Data;

@Data
public class WalletCreatedEvent {
	private String id;
	private String userId;
	private BalanceVO balance;
	private List<BalanceVO> balanceHistory;

}