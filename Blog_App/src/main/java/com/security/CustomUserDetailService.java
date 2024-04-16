package com.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Entity.User;
import com.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("usernot found exception");
		}
  return user;
//		User user = org.springframework.security.core.userdetails.User.builder()
//				.username(user.getUserName()).password(user.getPassword())
//				.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))).build();
//
//		return userDetails;

	}

}
