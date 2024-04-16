package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Entity.User;
import com.repository.UserRepo;

@Service
public class UserServiceIMPL implements UserService {

	@Autowired
	private UserRepo repo;

	@Override
	public String registerUser(User user) {

		User user1 = repo.findByUserName(user.getUsername());

		if (user1 != null) {
			return "user Already exist";
		}

		repo.save(user1);

		return "user Registered successfully";
	}

	@Override
	public String login(String userName, String password) {

		User user1 = repo.findByUserName(userName);

		if (user1 != null) {
			if (!user1.getPassword().equals(password)) {
				throw new BadCredentialsException("Invalid username or password");

			}

		}

		return "login successfully";

		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getUsers() {

		List<User> users = repo.findAll();

		if (users.size() == 0) {
			throw new UsernameNotFoundException("User not found");

		}
		return users;

	}

	@Override
	public User getUserById(int id) {

		Optional<User> user = repo.findById(id);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found with id : " + id);
		}

		return user.get();

	}

}
