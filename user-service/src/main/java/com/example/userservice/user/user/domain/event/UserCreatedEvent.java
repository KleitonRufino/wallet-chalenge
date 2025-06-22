package com.example.userservice.user.user.domain.event;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserCreatedEvent implements DomainEvent {
    private String id;
    private String name;
    private String email;

    public UserCreatedEvent(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String getEventType() {
        return "USER_CREATED";
    }
}