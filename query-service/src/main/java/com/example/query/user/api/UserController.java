package com.example.query.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.query.user.model.User;
import com.example.query.user.service.UserService;

@RestController
@RequestMapping("/query-service/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getUser() {
		return userService.getAllUsers();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable String userId) {
		var user = userService.getUser(userId);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

	@GetMapping("/login")
	public ResponseEntity<User> login(@RequestParam String email) {
		var user = userService.getUserByEmail(email);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}
}