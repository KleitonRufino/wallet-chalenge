package com.example.userservice.user.user.domain;

import java.util.List;

public interface UserDomainRepository {
    void save(User user);
    User findById(String id);
    User findByEmail(String email);
    List<User> findAll();
}
