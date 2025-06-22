package com.example.query.transaction.repository;

import com.example.query.transaction.model.Transaction;

public interface TransactionRepository {
    void save(Transaction transaction);
    Transaction findById(String id);
}
