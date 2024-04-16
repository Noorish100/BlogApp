package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	
	User findByUsername(String username);

}
