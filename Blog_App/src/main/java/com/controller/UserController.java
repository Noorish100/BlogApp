package com.controller;

import java.util.Base64;
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
import com.security.PasswordConfig;
import com.service.UserServiceIMPL;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceIMPL userService;

	@Autowired
	private CustomUserDetailService userDetailService;

	@Autowired
	private PasswordConfig customPasswordEncoder;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	 @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@RequestBody User user) {
	        // Encode the password before saving to the database
//	        String encodedPassword = customPasswordEncoder.passwordEncoder()
//	        		.encode(user.getPassword());
//	        user.setPassword(encodedPassword);
	        
	        String response = userService.registerUser(user);
	        return ResponseEntity.ok(response);
	    }
	 
	 
	 @GetMapping("/login")
	 public ResponseEntity<String> login(HttpServletRequest request) {
	     String authorizationHeader = request.getHeader("Authorization");

	     try {
	         if (authorizationHeader != null && authorizationHeader.startsWith("Basic")) {
	             // Extract the base64 encoded credentials part from the Authorization header
	             String base64Credentials = authorizationHeader.substring("Basic".length()).trim();

	             // Decode the base64 encoded credentials to get the actual username and password
	             byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
	             String credentials = new String(decodedBytes);

	             // Split the credentials into username and password
	             String[] usernameAndPassword = credentials.split(":", 2);
	             String username = usernameAndPassword[0];
	             String password = usernameAndPassword[1];

	             System.out.println("login method started=================");
	             System.out.println("username " + username);

	             // Load user details from the database
	             UserDetails userDetails = userDetailService.loadUserByUsername(username);

	             // Check if the provided password matches the stored password
	             if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
	                 return ResponseEntity.ok("Login successful");
	             } else {
	                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	             }
	         }
	     } catch (UsernameNotFoundException e) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found");
	     }

	     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
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
