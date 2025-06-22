package com.example.transactionservice.transaction.domain.model;

import java.util.List;

public interface TransactionDomainRepository {
    void save(Transaction transaction);
    Transaction findById(String id);
    List<Transaction> findAll();
}
