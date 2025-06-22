//package com.example.walletservice.wallet.wallet.publisher;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import com.example.walletservice.wallet.wallet.domain.WalletDomainPublisher;
//import com.example.walletservice.wallet.wallet.domain.event.WalletCreatedEvent;
//import com.example.walletservice.wallet.wallet.domain.event.WalletUpdatedEvent;
//import com.example.walletservice.wallet.transaction.domain.event.TransactionFinishedEvent;
//
//@Component
//public class WalletPublisherImpl implements WalletDomainPublisher {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    @Async
//    public void publishWalletCreated(WalletCreatedEvent event) {
//        restTemplate.postForObject("http://localhost:8080/query-service/events/wallet/create", event, Void.class);
//    }
//
//    @Override
//    @Async
//    public void publishWalletUpdated(WalletUpdatedEvent event) {
//        restTemplate.postForObject("http://localhost:8080/query-service/events/wallet/update", event, Void.class);
//    }
//
//    @Override
//    @Async
//    public void publishTransactionFinished(TransactionFinishedEvent event) {
//        restTemplate.postForObject("http://localhost:8083/transaction-service/events/transaction/finished", event, Void.class);
//    }
//}
