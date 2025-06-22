package com.example.userservice.user.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.user.user.api.dto.UserDTO;
import com.example.userservice.user.user.application.UserApplication;
import com.example.userservice.user.user.exception.UserAlreadyExistsException;

@RestController
@RequestMapping("/user-service/user")
public class UserController {

	@Autowired
	private UserApplication userApplication;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody UserDTO dto) {
		try {
			return ResponseEntity.status(201).body(userApplication.save(dto)); // HTTP 201 (Created)
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.status(409).body(e.getMessage()); // HTTP 409 Conflict
		}
	}
}
