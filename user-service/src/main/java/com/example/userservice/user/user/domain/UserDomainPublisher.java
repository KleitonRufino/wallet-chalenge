package com.example.userservice.user.user.domain;

import com.example.userservice.user.user.domain.event.DomainEvent;

public interface UserDomainPublisher {
    void publish(DomainEvent event);
}
