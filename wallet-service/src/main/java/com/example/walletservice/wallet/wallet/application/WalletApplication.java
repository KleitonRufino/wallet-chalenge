package com.example.walletservice.wallet.wallet.application;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.walletservice.wallet.transaction.domain.TransactionStatus;
import com.example.walletservice.wallet.transaction.domain.event.TransactionDepositEvent;
import com.example.walletservice.wallet.transaction.domain.event.TransactionFinishedEvent;
import com.example.walletservice.wallet.transaction.domain.event.TransactionTransferEvent;
import com.example.walletservice.wallet.transaction.domain.event.TransactionWithdrawEvent;
import com.example.walletservice.wallet.wallet.api.dto.WalletDTO;
import com.example.walletservice.wallet.wallet.domain.Balance;
import com.example.walletservice.wallet.wallet.domain.Wallet;
import com.example.walletservice.wallet.wallet.domain.WalletDomainPublisher;
import com.example.walletservice.wallet.wallet.domain.WalletDomainRepository;
import com.example.walletservice.wallet.wallet.domain.event.WalletCreatedEvent;
import com.example.walletservice.wallet.wallet.domain.event.WalletUpdatedEvent;
import com.example.walletservice.wallet.wallet.domain.vo.BalanceVO;
import com.example.walletservice.wallet.wallet.exception.InsufficientBalanceException;
import com.example.walletservice.wallet.wallet.exception.WalletAlreadyExistsException;

@Service
public class WalletApplication {
	@Autowired
	private WalletDomainRepository walletRepository;

	@Autowired
	private WalletDomainPublisher walletPublisher;

	public String createWallet(WalletDTO dto) {
		if (walletRepository.findByUserId(dto.getUserId()) != null)
			throw new WalletAlreadyExistsException("Wallet for this user already exists");

		Wallet wallet = new Wallet(UUID.randomUUID().toString(), dto.getUserId());
		walletRepository.save(wallet);

		walletPublisher.publish(new WalletCreatedEvent(wallet.getId(), wallet.getUserId(),
				toBalanceVO(wallet.getBalance()), toBalanceVOList(wallet.getBalanceHistory())));

		return wallet.getId();
	}

	public void handleDeposit(TransactionDepositEvent event) {
		Wallet wallet = walletRepository.findById(event.getWalletId());
		TransactionFinishedEvent finishedEvent;
		try {
			if (wallet != null) {
				wallet.addBalance(event.getAmount());
				walletRepository.save(wallet);
				walletPublisher.publish(new WalletUpdatedEvent(wallet.getId(),
						toBalanceVO(wallet.getBalance()), toBalanceVOList(wallet.getBalanceHistory())));
				finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.COMPLETED.name(),
						"Deposit successful");
			} else {
				finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.FAILED.name(),
						"Wallet not found");
			}
		} catch (Exception e) {
			finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.FAILED.name(),
					e.getMessage());
		}
		walletPublisher.publish(finishedEvent);
	}

	public void handleWithdraw(TransactionWithdrawEvent event) {
		Wallet wallet = walletRepository.findById(event.getWalletId());
		TransactionFinishedEvent finishedEvent;
		try {
			if (wallet != null) {
				wallet.subtractBalance(event.getAmount());
				walletRepository.save(wallet);
				walletPublisher.publish(new WalletUpdatedEvent(wallet.getId(),
						toBalanceVO(wallet.getBalance()), toBalanceVOList(wallet.getBalanceHistory())));
				finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.COMPLETED.name(),
						"Withdraw successful");
			} else {
				finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.FAILED.name(),
						"Wallet not found");
			}
		} catch (InsufficientBalanceException e) {
			finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.FAILED.name(),
					e.getMessage());
		}
		walletPublisher.publish(finishedEvent);
	}

	public void handleTransfer(TransactionTransferEvent event) {
		Wallet fromWallet = walletRepository.findById(event.getFromWalletId());
		Wallet toWallet = walletRepository.findById(event.getToWalletId());
		TransactionFinishedEvent finishedEvent;
		try {
			if (fromWallet != null && toWallet != null) {
				fromWallet.subtractBalance(event.getAmount());
				toWallet.addBalance(event.getAmount());
				walletRepository.save(fromWallet);
				walletRepository.save(toWallet);
				walletPublisher.publish(new WalletUpdatedEvent(fromWallet.getId(),
						toBalanceVO(fromWallet.getBalance()), toBalanceVOList(fromWallet.getBalanceHistory())));
				walletPublisher.publish(new WalletUpdatedEvent(toWallet.getId(),
						toBalanceVO(toWallet.getBalance()), toBalanceVOList(toWallet.getBalanceHistory())));
				finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.COMPLETED.name(),
						"Transfer successful");
			} else {
				finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.FAILED.name(),
						"Wallet not found");
			}
		} catch (InsufficientBalanceException e) {
			finishedEvent = new TransactionFinishedEvent(event.getId(), TransactionStatus.FAILED.name(),
					e.getMessage());
		}
		walletPublisher.publish(finishedEvent);
	}

	private java.util.List<BalanceVO> toBalanceVOList(java.util.List<Balance> balances) {
		if (balances == null)
			return null;
		return balances.stream().map(this::toBalanceVO).collect(Collectors.toList());
	}

	private BalanceVO toBalanceVO(Balance balance) {
		if (balance == null)
			return null;
		return new BalanceVO(balance.getId(), balance.getBalance(), balance.getDate());
	}

}
