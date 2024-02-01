package com.SecondPersonalProject.Stones.servicesAndImpls;


import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.PersonRegistrationException;
import com.SecondPersonalProject.Stones.models.LoginResponse;
import com.SecondPersonalProject.Stones.models.RegisterRequest;

public interface LoginService {

	LoginResponse login(String email, String password) throws PersonNotFoundException;

	LoginResponse register(RegisterRequest registerRequest) throws PersonRegistrationException, IllegalArgumentException;
}
