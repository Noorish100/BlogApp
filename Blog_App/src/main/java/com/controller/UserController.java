package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceIMPL userService;

	@Autowired
	private CustomUserDetailService userDetailService;

//	@Autowired
//	private PasswordEncoder customPasswordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		String s = userService.registerUser(user);
		return ResponseEntity.ok(s);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(HttpServletRequest request) {
		String username = request.getHeader("Authorization");
		String password = request.getParameter("password"); // Extract password from the request if needed

		try {
			// Load user details from the database
			UserDetails userDetails = userDetailService.loadUserByUsername(username);

			// Check if the provided password matches the stored password
			System.out.println("Password "+password);
			System.out.println("userdetailPassword "+userDetails.getPassword());
			
			if (userDetails != null && userDetails.getPassword().equals(password)) {
				return ResponseEntity.ok("Login successful");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
			}
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found");
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
