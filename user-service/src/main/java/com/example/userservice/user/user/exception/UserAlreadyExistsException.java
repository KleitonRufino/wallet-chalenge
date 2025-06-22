package com.example.userservice.user.user.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2249535871865671585L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
