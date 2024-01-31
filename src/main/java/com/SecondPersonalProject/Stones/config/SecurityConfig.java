package com.SecondPersonalProject.Stones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	InMemoryUserDetailsManager userDetailsManager() {
		UserDetails Tochi = User.builder()
				.username("tochi")
//				.password("Password123!@#")
				.password("$2a$10$7RXyxzahRNXlvLB6nmFnzOkltUW7vEneSVX0BsN0BsFeztirSG0x2")
				.roles("USER","ADMIN")
				.build();
		return new InMemoryUserDetailsManager(Tochi);
	}
	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) {
		try {
			http.authorizeHttpRequests(configurer -> configurer
				    .requestMatchers(HttpMethod.PUT, "/login").permitAll()
				    .requestMatchers(HttpMethod.POST, "/login").permitAll()
					.requestMatchers(HttpMethod.GET,"/person").hasAnyRole("USER")
					.requestMatchers(HttpMethod.GET,"/person/**").hasAnyRole("USER")
					.requestMatchers(HttpMethod.GET,"/**").hasAnyRole("ADMIN")
					.requestMatchers(HttpMethod.POST,"/**").hasAnyRole("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/**").hasAnyRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/**").hasAnyRole("ADMIN")
					);
			http.httpBasic(Customizer.withDefaults());
			http.csrf(csrf->csrf.disable());
			return http.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
