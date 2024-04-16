package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



	@Configuration
	@EnableMethodSecurity
	public class SecurityConfig {
		@Bean
		public SecurityFilterChain masaiSecurityConfig(HttpSecurity http) throws Exception {
		
		
			http.authorizeHttpRequests( (auth)->auth
					.anyRequest().authenticated()).csrf().disable().httpBasic(Customizer.withDefaults()).logout((l)-> l.logoutSuccessUrl("/api/logout").permitAll());
		
			return http.build();
		}


		
		@Bean
		public UserDetailsService userDetail() {
		
			  return new CustomUserDetailService();
	    	
		  
		}
		
	     @Bean
		 public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		 }
		}
