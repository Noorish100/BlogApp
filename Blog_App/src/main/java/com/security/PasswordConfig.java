package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        // No need to define the PasswordEncoder bean here, as it's already defined in PasswordEncoderConfig
        return new BCryptPasswordEncoder();
    }

}
