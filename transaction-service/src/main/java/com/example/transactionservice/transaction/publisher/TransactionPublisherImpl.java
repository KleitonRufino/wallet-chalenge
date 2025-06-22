//package com.example.transactionservice.transaction.publisher;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import com.example.transactionservice.transaction.domain.model.TransactionDomainPublisher;
//import com.example.transactionservice.transaction.event.TransactionCreatedEvent;
//import com.example.transactionservice.transaction.event.TransactionUpdatedEvent;
//
//@Component
//public class TransactionPublisherImpl implements TransactionDomainPublisher {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    @Async
//    public void publishTransactionCreated(TransactionCreatedEvent event) {
//        restTemplate.postForObject("http://localhost:8080/query-service/events/transaction/create", event, Void.class);
//    }
//
//    @Override
//    @Async
//    public void publishTransactionUpdated(TransactionUpdatedEvent event) {
//        restTemplate.postForObject("http://localhost:8080/query-service/events/transaction/update", event, Void.class);
//    }
//}
