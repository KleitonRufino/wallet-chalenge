package com.example.query.user.repository;

import com.example.query.user.model.User;
import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(String id);
    User findByEmail(String email);
    List<User> findAll();
}