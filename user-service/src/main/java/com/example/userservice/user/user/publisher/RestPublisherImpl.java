package com.example.userservice.user.user.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.userservice.user.config.EventUrlProperties;
import com.example.userservice.user.user.domain.UserDomainPublisher;
import com.example.userservice.user.user.domain.event.DomainEvent;

@Component
public class RestPublisherImpl implements UserDomainPublisher {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EventUrlProperties eventUrlProperties;

    @Override
    public void publish(DomainEvent event) {
        String url = eventUrlProperties.getUrls().get(event.getEventType());
        if (url == null) throw new IllegalArgumentException("No URL for event type: " + event.getEventType());
        restTemplate.postForObject(url, event, Void.class);
    }
}
