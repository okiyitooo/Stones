package com.SecondPersonalProject.Stones.servicesAndImpls;


import com.SecondPersonalProject.Stones.exceptions.PersonRegistrationException;
import com.SecondPersonalProject.Stones.models.LoginResponse;
import com.SecondPersonalProject.Stones.models.Person;

public interface LoginService {

	LoginResponse login(String email, String password);

	LoginResponse register(Person person) throws PersonRegistrationException;
}
