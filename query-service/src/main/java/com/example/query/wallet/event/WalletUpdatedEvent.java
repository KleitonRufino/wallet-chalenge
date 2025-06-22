package com.example.query.wallet.event;

import java.util.List;

import com.example.query.wallet.vo.BalanceVO;

import lombok.Data;

@Data
public class WalletUpdatedEvent {
	private String id;
	private BalanceVO balance;
	private List<BalanceVO> balanceHistory;

}