package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.User;
import com.security.CustomUserDetailService;
import com.service.UserServiceIMPL;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceIMPL userService;

	

	@Autowired
	private CustomUserDetailService userDetailService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		String s = userService.registerUser(user);
		return ResponseEntity.ok(s);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
		try {
			// Load user details
			UserDetails userDetails = userDetailService.loadUserByUsername(username);

			// If userDetails is not null and passwords match
			if (userDetails != null && userDetails.getPassword().equals(password)) {
				// Return success message
				return ResponseEntity.ok("Login successful");
			} else {
				// Authentication failed
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
			}
		} catch (Exception e) {
			// Authentication exception occurred
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication exception: " + e.getMessage());
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/AllUsers")
	public ResponseEntity<List<User>> getAllUser() {

		List<User> user = userService.getUsers();

		return ResponseEntity.ok(user);
	}

	@GetMapping("/userById/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable int id) {

		User user = userService.getUserById(id);

		return ResponseEntity.ok(user);
	}

}
