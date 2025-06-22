package com.example.walletservice.wallet.wallet.domain;

import com.example.walletservice.wallet.wallet.domain.event.DomainEvent;

public interface WalletDomainPublisher {
    void publish(DomainEvent event);
}
