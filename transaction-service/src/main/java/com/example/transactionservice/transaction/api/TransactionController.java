package com.example.transactionservice.transaction.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transactionservice.transaction.api.dto.TransactionDTO;
import com.example.transactionservice.transaction.application.TransactionApplication;

@RestController
@RequestMapping("/transaction-service/transaction")
public class TransactionController {
	@Autowired
	private TransactionApplication transactionApplication;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody TransactionDTO dto) {
		return ResponseEntity.status(201).body(transactionApplication.processTransaction(dto));
	}

}
