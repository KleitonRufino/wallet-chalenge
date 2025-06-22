package com.example.query.transaction.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.query.transaction.model.Transaction;
import com.example.query.transaction.service.TransactionService;

@RestController
@RequestMapping("/query-service/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable String transactionId) {
		Transaction tx = transactionService.getTransaction(transactionId);
		return tx != null ? ResponseEntity.ok(tx) : ResponseEntity.notFound().build();
	}
}
