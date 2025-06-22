package com.example.query.user.event;

import lombok.Data;

@Data
public class UserCreatedEvent {
	private String id;
	private String name;
	private String email;
}