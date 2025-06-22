package com.example.transactionservice.transaction.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transactionservice.transaction.application.TransactionApplication;
import com.example.transactionservice.transaction.event.TransactionFinishedEvent;

@RestController
@RequestMapping("/transaction-service/events/transaction")
public class EventListener {

	@Autowired
	private TransactionApplication transactionApplication;

	@PostMapping("/finished")
	public void handleFinished(@RequestBody TransactionFinishedEvent event) {
		transactionApplication.finishTransaction(event);
	}
}
