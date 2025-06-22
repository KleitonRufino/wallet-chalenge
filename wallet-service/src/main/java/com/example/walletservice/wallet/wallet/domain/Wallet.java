package com.example.walletservice.wallet.wallet.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.example.walletservice.wallet.wallet.exception.InsufficientBalanceException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor 
public class Wallet {

	private String id;
	private String userId;
	private Balance balance;
	private List<Balance> balanceHistory = new ArrayList<>();

	public Wallet(String id, String userId) {
		this.id = id;
		this.userId = userId;
		this.balance = new Balance(UUID.randomUUID().toString(), BigDecimal.ZERO, LocalDateTime.now());
	}

	public void addBalance(BigDecimal amount) {
		balanceHistory.add(balance);

		BigDecimal newValue = balance.getBalance().add(amount);
		balance = new Balance(UUID.randomUUID().toString(), newValue, LocalDateTime.now());
	}

	public void subtractBalance(BigDecimal amount) {
		if (amount.compareTo(balance.getBalance()) > 0) {
			throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
		}
		balanceHistory.add(balance);
		BigDecimal newValue = balance.getBalance().subtract(amount);
		balance = new Balance(UUID.randomUUID().toString(), newValue, LocalDateTime.now());
	}

    public List<Balance> getBalanceHistory() {
        return balanceHistory.stream()
                .sorted(Comparator.comparing(Balance::getDate).reversed())
                .toList();
    }
}