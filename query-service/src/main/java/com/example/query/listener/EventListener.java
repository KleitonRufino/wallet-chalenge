package com.example.query.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.query.transaction.event.TransactionCreatedEvent;
import com.example.query.transaction.event.TransactionUpdatedEvent;
import com.example.query.transaction.service.TransactionService;
import com.example.query.user.event.UserCreatedEvent;
import com.example.query.user.service.UserService;
import com.example.query.wallet.event.WalletCreatedEvent;
import com.example.query.wallet.event.WalletUpdatedEvent;
import com.example.query.wallet.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/query-service/events")
public class EventListener {

	@Autowired
	private UserService userService;

	@Autowired
	private WalletService walletService;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/user/create")
	public void handleUserCreated(@RequestBody UserCreatedEvent event) {
		log.info("[Listener] Received event: {}", UserCreatedEvent.class.getSimpleName());
		userService.createUser(event);
	}

	@PostMapping("/wallet/create")
	public void handleWalletCreated(@RequestBody WalletCreatedEvent event) {
		log.info("[Listener] Received event: {}", WalletCreatedEvent.class.getSimpleName());
		walletService.createWallet(event);
	}

	@PostMapping("/wallet/update")
	public void handleWalletUpdated(@RequestBody WalletUpdatedEvent event) {
		log.info("[Listener] Received WalletUpdatedEvent: {}", WalletUpdatedEvent.class.getSimpleName());
		walletService.updateWallet(event);
	}

	@PostMapping("/transaction/create")
	public void handleTransactionCreated(@RequestBody TransactionCreatedEvent event) {
		log.info("[Listener] Received event: {}", TransactionCreatedEvent.class.getSimpleName());
		transactionService.createTransaction(event);
	}

	@PostMapping("/transaction/update")
	public void handleTransactionUpdated(@RequestBody TransactionUpdatedEvent event) {
		log.info("[Listener] Received event: {}", TransactionUpdatedEvent.class.getSimpleName());
		transactionService.updateTransactionStatus(event);
	}
}