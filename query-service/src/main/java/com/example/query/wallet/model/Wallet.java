package com.example.query.wallet.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Data;

@Data
public class Wallet {
	private String id;
	private String userId;
	private Balance balance;
	private List<Balance> balanceHistory = new ArrayList<>();

    public List<Balance> getBalanceHistory() {
        return balanceHistory.stream()
                .sorted(Comparator.comparing(Balance::getDate).reversed())
                .toList();
    }
}
