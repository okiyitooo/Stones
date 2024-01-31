package com.SecondPersonalProject.Stones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SecondPersonalProject.Stones.exceptions.InvalidPersonPasswordException;
import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.PersonRegistrationException;
import com.SecondPersonalProject.Stones.models.LoginRequest;
import com.SecondPersonalProject.Stones.models.LoginResponse;
import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.servicesAndImpls.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	LoginService loginService;


    @PutMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(loginResponse);
        } catch (InvalidPersonPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (PersonNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody Person person) {
		try {
			LoginResponse loginResponse = loginService.register(person);
			return ResponseEntity.ok(loginResponse);
		} catch (PersonRegistrationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
    }
}