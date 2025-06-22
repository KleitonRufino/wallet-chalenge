package com.example.query.wallet.repository;

import com.example.query.wallet.model.Wallet;
import com.example.query.wallet.model.Balance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface WalletRepository {
    void save(Wallet wallet);
    Wallet findById(String id);
    Wallet findByUserId(String userId);
    List<Wallet> findAll();
    BigDecimal findBalanceByUserId(String userId);
    Balance findBalanceByDate(String walletId, LocalDate date);
}