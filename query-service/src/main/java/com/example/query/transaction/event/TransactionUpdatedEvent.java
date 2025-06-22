package com.example.query.transaction.event;

import lombok.Data;

@Data
public class TransactionUpdatedEvent {

	private String id;
	private String status;

}
