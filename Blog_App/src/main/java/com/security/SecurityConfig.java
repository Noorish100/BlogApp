package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


	
	
	@Configuration
	@EnableWebSecurity
	@EnableMethodSecurity
	public class SecurityConfig {

	    @Autowired
	    private CustomUserDetailService customUserDetailService;

	   @Autowired
	   private PasswordConfig config;

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailService).passwordEncoder(config.passwordEncoder());
	    }

	    @Bean
	    public SecurityFilterChain masaiSecurityConfig(HttpSecurity http) throws Exception {
	        http.authorizeRequests((auth) -> auth.anyRequest().authenticated())
	            .csrf().disable()
	            .httpBasic(Customizer.withDefaults())
	            .logout((l) -> l.logoutSuccessUrl("/api/logout").permitAll());
	        
	        return http.build();
	    }
	}


