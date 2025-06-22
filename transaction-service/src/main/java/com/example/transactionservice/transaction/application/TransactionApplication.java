package com.example.transactionservice.transaction.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.transactionservice.transaction.api.dto.TransactionDTO;
import com.example.transactionservice.transaction.domain.model.Transaction;
import com.example.transactionservice.transaction.domain.model.TransactionDomainPublisher;
import com.example.transactionservice.transaction.domain.model.TransactionDomainRepository;
import com.example.transactionservice.transaction.domain.model.TransactionStatusEnum;
import com.example.transactionservice.transaction.event.TransactionCreatedEvent;
import com.example.transactionservice.transaction.event.TransactionDepositEvent;
import com.example.transactionservice.transaction.event.TransactionFinishedEvent;
import com.example.transactionservice.transaction.event.TransactionTransferEvent;
import com.example.transactionservice.transaction.event.TransactionUpdatedEvent;
import com.example.transactionservice.transaction.event.TransactionWithdrawEvent;

@Service
public class TransactionApplication {
    @Autowired
    private TransactionDomainRepository transactionRepository;

    @Autowired
    private TransactionDomainPublisher transactionPublisher;

    public String processTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), dto.getFromWalletId(),
                dto.getToWalletId(), dto.getType(), dto.getAmount());
        transactionRepository.save(transaction);

        transactionPublisher.publish(new TransactionCreatedEvent(transaction.getId(),
                transaction.getFromWalletId(), transaction.getToWalletId(), transaction.getAmount(),
                transaction.getType().name(), transaction.getDate(), transaction.getStatus().name()));

        switch (dto.getType()) {
            case DEPOSIT:
                transactionPublisher.publish(new TransactionDepositEvent(transaction.getId(),
                        transaction.getFromWalletId(), transaction.getAmount(), transaction.getDate()));
                break;
            case WITHDRAW:
                transactionPublisher.publish(new TransactionWithdrawEvent(transaction.getId(),
                        transaction.getFromWalletId(), transaction.getAmount(), transaction.getDate()));
                break;
            case TRANSFER:
                transactionPublisher.publish(new TransactionTransferEvent(transaction.getId(),
                        transaction.getFromWalletId(), transaction.getToWalletId(), transaction.getAmount(),
                        transaction.getDate()));
                break;
        }
        return transaction.getId();
    }

    public void finishTransaction(TransactionFinishedEvent event) {
        Transaction transaction = transactionRepository.findById(event.getId());
        if (transaction != null) {
            transaction.changeStatus(TransactionStatusEnum.valueOf(event.getStatus()));
            transactionRepository.save(transaction);
            transactionPublisher.publish(new TransactionUpdatedEvent(transaction.getId(),
                    transaction.getStatus().name()));
        }
    }
}
