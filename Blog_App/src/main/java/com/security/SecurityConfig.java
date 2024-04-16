package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
		public InMemoryUserDetailsManager userDetails() {
		
	    	InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		    UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("12345")).roles("ADMIN").build();
		    UserDetails user = User.withUsername("user").password(passwordEncoder().encode("12345")).roles("USER").build();
//		    userDetailsService.createUser(admin);
//		    userDetailsService.createUser(user);
		    return new InMemoryUserDetailsManager(admin,user);
		}
		
	     @Bean
		 public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		 }
		}
