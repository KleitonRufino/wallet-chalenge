package com.example.walletservice.wallet.wallet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletservice.wallet.wallet.api.dto.WalletDTO;
import com.example.walletservice.wallet.wallet.application.WalletApplication;
import com.example.walletservice.wallet.wallet.exception.WalletAlreadyExistsException;

@RestController
@RequestMapping("/wallet-service/wallet")
public class WalletController {

	@Autowired
	private WalletApplication walletApplication;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody WalletDTO dto) {
		try {
			return ResponseEntity.status(201).body(walletApplication.createWallet(dto));
		} catch (WalletAlreadyExistsException e) {
			return ResponseEntity.status(409).body(e.getMessage()); 
		}
	}
}
