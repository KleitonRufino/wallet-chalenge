package com.example.query.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.query.transaction.event.TransactionCreatedEvent;
import com.example.query.transaction.event.TransactionUpdatedEvent;
import com.example.query.transaction.model.Transaction;
import com.example.query.transaction.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public void createTransaction(TransactionCreatedEvent event) {
		Transaction tx = new Transaction();
		tx.setId(event.getId());
		tx.setFromWalletId(event.getFromWalletId());
		tx.setToWalletId(event.getToWalletId());
		tx.setAmount(event.getAmount());
		tx.setType(event.getType());
		tx.setDate(event.getDate());
		tx.setStatus(event.getStatus());

		transactionRepository.save(tx);
	}

	public void updateTransactionStatus(TransactionUpdatedEvent event) {
		var transaction = transactionRepository.findById(event.getId());
		transaction.setStatus(event.getStatus());
		transactionRepository.save(transaction);
	}

	public Transaction getTransaction(String id) {
		return transactionRepository.findById(id);
	}
}