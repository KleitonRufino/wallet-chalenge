package com.example.walletservice.wallet.wallet.exception;

public class InsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = -1872729817870071636L;

	public InsufficientBalanceException(String message) {
		super(message);
	}
}
