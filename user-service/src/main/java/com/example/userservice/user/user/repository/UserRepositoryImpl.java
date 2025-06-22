package com.example.userservice.user.user.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.userservice.user.user.domain.User;
import com.example.userservice.user.user.domain.UserDomainRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public final class UserRepositoryImpl implements UserDomainRepository {
	private static final String FILE_PATH = "users.json";
	private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	private List<User> users = loadFromFile();

	private List<User> loadFromFile() {
		try {
			File file = new File(FILE_PATH);
			if (file.exists()) {
				return mapper.readValue(file, new TypeReference<List<User>>() {
				});
			}
		} catch (IOException e) {
			log.error("Error loading users from file", e);
		}
		return new ArrayList<>();
	}

	private void saveToFile() {
		try {
			mapper.writeValue(new File(FILE_PATH), users);
		} catch (IOException e) {
			log.error("Error saving users to file", e);
		}
	}

	@Override
	public void save(User user) {
		users.removeIf(u -> u.getId().equals(user.getId())); // avoids duplicates
		users.add(user);
		saveToFile();
		log.info("Saved user: {}", user);
	}

	@Override
	public User findById(String id) {
		List<User> users = loadFromFile();
		return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public User findByEmail(String email) {
		List<User> users = loadFromFile();
		return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(loadFromFile());
	}
}
