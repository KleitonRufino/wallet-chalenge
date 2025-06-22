package com.example.query.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.query.wallet.event.WalletCreatedEvent;
import com.example.query.wallet.event.WalletUpdatedEvent;
import com.example.query.wallet.model.Balance;
import com.example.query.wallet.model.Wallet;
import com.example.query.wallet.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	public void createWallet(WalletCreatedEvent event) {

		Wallet wallet = new Wallet();
		wallet.setId(event.getId());
		wallet.setUserId(event.getUserId());

		Balance balance = new Balance();
		balance.setId(event.getBalance().getId());
		balance.setBalance(event.getBalance().getBalance());
		balance.setDate(event.getBalance().getDate());

		wallet.setBalance(balance);
		wallet.setBalanceHistory(event.getBalanceHistory().stream()
				.map(b -> new Balance(b.getId(), b.getBalance(), b.getDate())).toList());

		walletRepository.save(wallet);
	}

	public void updateWallet(WalletUpdatedEvent event) {
		var wallet = walletRepository.findById(event.getId());
		Balance balance = new Balance();
		balance.setId(event.getBalance().getId());
		balance.setBalance(event.getBalance().getBalance());
		balance.setDate(event.getBalance().getDate());

		wallet.setBalance(balance);
		wallet.setBalanceHistory(event.getBalanceHistory().stream()
				.map(b -> new Balance(b.getId(), b.getBalance(), b.getDate())).toList());
		walletRepository.save(wallet);
	}

	public Wallet getWallet(String walletId) {
		return walletRepository.findById(walletId);
	}

	public Wallet getWalletByUserId(String userId) {
		return walletRepository.findByUserId(userId);
	}

	public BigDecimal getCurrentBalance(String userId) {
		return walletRepository.findBalanceByUserId(userId);
	}

	public Balance getBalanceAt(String walletId, LocalDate date) {
		return walletRepository.findBalanceByDate(walletId, date);
	}
}
