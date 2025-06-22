package com.example.walletservice.wallet.wallet.exception;

public class WalletAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -4203495594656292908L;

	public WalletAlreadyExistsException(String message) {
		super(message);
	}
}
