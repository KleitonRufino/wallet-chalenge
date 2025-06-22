package com.example.query.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.query.user.event.UserCreatedEvent;
import com.example.query.user.model.User;
import com.example.query.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void createUser(UserCreatedEvent event) {
		User user = new User();
		user.setId(event.getId());
		user.setName(event.getName());
		user.setEmail(event.getEmail());
		userRepository.save(user);
	}

	public User getUser(String id) {
		return userRepository.findById(id);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}