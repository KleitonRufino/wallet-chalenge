package com.example.query.wallet.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.query.wallet.model.Balance;
import com.example.query.wallet.model.Wallet;
import com.example.query.wallet.service.WalletService;

@RestController
@RequestMapping("/query-service/wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@GetMapping("/{walletId}")
	public ResponseEntity<Wallet> getWallet(@PathVariable String walletId) {
		var wallet = walletService.getWallet(walletId);
		return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Wallet> getWalletByUserId(@PathVariable String userId) {
		var wallet = walletService.getWalletByUserId(userId);
		return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
	}

	@GetMapping("/user/{userId}/balance")
	public ResponseEntity<BigDecimal> getCurrentBalance(@PathVariable String userId) {
		var balance = walletService.getCurrentBalance(userId);
		return balance != null ? ResponseEntity.ok(balance) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{walletId}/balance")
	public ResponseEntity<Balance> getBalanceAtDate(@PathVariable String walletId,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		var balance = walletService.getBalanceAt(walletId, date);
		return balance != null ? ResponseEntity.ok(balance) : ResponseEntity.notFound().build();
	}
}