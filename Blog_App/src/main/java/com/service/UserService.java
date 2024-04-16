package com.service;

import java.util.List;

import com.Entity.User;

public interface UserService {
	
	String registerUser(User user);
	
	String login(String userName, String password);
	
    List<User> getUsers();
    
    User getUserById(int id);

}
