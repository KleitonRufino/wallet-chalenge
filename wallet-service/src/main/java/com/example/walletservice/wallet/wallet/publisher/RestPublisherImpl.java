package com.example.walletservice.wallet.wallet.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.walletservice.wallet.config.EventUrlProperties;
import com.example.walletservice.wallet.wallet.domain.WalletDomainPublisher;
import com.example.walletservice.wallet.wallet.domain.event.DomainEvent;

@Component
public class RestPublisherImpl implements WalletDomainPublisher {

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
