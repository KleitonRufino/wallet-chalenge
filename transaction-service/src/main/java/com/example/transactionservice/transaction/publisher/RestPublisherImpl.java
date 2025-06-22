package com.example.transactionservice.transaction.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.transactionservice.transaction.domain.model.TransactionDomainPublisher;
import com.example.transactionservice.transaction.event.DomainEvent;
import com.example.transactionservice.transaction.config.EventUrlProperties;

@Component
public class RestPublisherImpl implements TransactionDomainPublisher {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EventUrlProperties eventUrlProperties;

    @Override
    @Async
    public void publish(DomainEvent event) {
        String url = eventUrlProperties.getUrls().get(event.getEventType());
        if (url == null) {
            throw new IllegalArgumentException("No URL configured for event type: " + event.getEventType());
        }
        restTemplate.postForObject(url, event, Void.class);
    }
}
