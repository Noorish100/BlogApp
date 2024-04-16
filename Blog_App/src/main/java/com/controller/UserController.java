package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.User;
import com.service.UserServiceIMPL;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	
	    @Autowired
	    private UserServiceIMPL userService;
        
	    
	    @PreAuthorize("hasRole('USER')")
	    @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@RequestBody User user) {
	        String s=userService.registerUser(user);
	        return ResponseEntity.ok(s);
	    }
        
	    @PreAuthorize("hasRole('USER')")
	    @PostMapping("/login")
	    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
	        String token = userService.login(username, password);
	        return ResponseEntity.ok(token);
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/AllUsers")
	    public ResponseEntity<List<User>> getAllUser(){
	    	
	    	List<User> user=userService.getUsers();
	    	
	    	return ResponseEntity.ok(user);
	    }
	    
	    
	    @GetMapping("/userById/{id}")
	    public ResponseEntity<User> getUserByID(@PathVariable int id){
	    	
	    	User user=userService.getUserById(id);
	    	
	    	return ResponseEntity.ok(user);
	    }

}
