package com.example.walletservice.wallet.wallet.domain;

import java.util.List;

public interface WalletDomainRepository {
    void save(Wallet wallet);
    Wallet findById(String id);
    Wallet findByUserId(String userId);
    List<Wallet> findAll();
}
