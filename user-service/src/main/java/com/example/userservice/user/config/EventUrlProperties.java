package com.example.userservice.user.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "event")
public class EventUrlProperties {
    private Map<String, String> urls;

    public Map<String, String> getUrls() {
        return urls;
    }
    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }
}
