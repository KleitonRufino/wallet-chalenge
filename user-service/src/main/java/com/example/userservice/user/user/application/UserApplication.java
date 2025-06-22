package com.example.userservice.user.user.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userservice.user.user.api.dto.UserDTO;
import com.example.userservice.user.user.domain.User;
import com.example.userservice.user.user.domain.UserDomainPublisher;
import com.example.userservice.user.user.domain.UserDomainRepository;
import com.example.userservice.user.user.domain.event.UserCreatedEvent;
import com.example.userservice.user.user.exception.UserAlreadyExistsException;

@Service
public class UserApplication {

	@Autowired
	private UserDomainRepository userRepository;

	@Autowired
	private UserDomainPublisher userPublisher;

	public String save(UserDTO dto) {
		if (userRepository.findByEmail(dto.getEmail()) != null)
			throw new UserAlreadyExistsException("User with this email already exists");

		User user = new User(UUID.randomUUID().toString(), dto.getName(), dto.getEmail());
		userRepository.save(user);

		UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getName(), user.getEmail());
		userPublisher.publish(event);
		return user.getId();
	}
}