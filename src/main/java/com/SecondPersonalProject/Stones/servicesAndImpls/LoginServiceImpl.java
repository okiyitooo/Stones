package com.SecondPersonalProject.Stones.servicesAndImpls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SecondPersonalProject.Stones.exceptions.InvalidPersonPasswordException;
import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.PersonRegistrationException;
import com.SecondPersonalProject.Stones.models.LoginResponse;
import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.models.RegisterRequest;
import com.SecondPersonalProject.Stones.models.Role;
import com.SecondPersonalProject.Stones.repositories.PersonRepository;
import com.SecondPersonalProject.Stones.repositories.RoleRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonService personService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public LoginResponse login(String email, String password) throws PersonNotFoundException {
	    Person person = personService.whoIsThis(email);
	    if (!passwordEncoder.matches(password, person.getPassword())) {
	        throw new InvalidPersonPasswordException("Invalid Password");
	    }
        
        String generatedToken = person.generateToken();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(generatedToken);
        loginResponse.setPersonId(person.getPersonId());
        loginResponse.setFirstName(person.getFirstName());
        loginResponse.setLastName(person.getLastName());
        loginResponse.setEmail(email);
        loginResponse.setRoles(person.getRoles());

        return loginResponse;
	}
	@Transactional
	@Override
	public LoginResponse register(RegisterRequest registerRequest) throws PersonRegistrationException, IllegalArgumentException {
		try {
			personService.whoIsThis(registerRequest.getEmail());
			throw new PersonRegistrationException("The email " + registerRequest.getEmail()+" already belongs to an account");
		} catch (PersonNotFoundException e) {
		}
		Person person = personService.birthPerson(registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getPassword());
		List<Role> roles = new ArrayList<>();
        
        Role role = roleRepository.findByName("USER").orElse(roleRepository.save(new Role("USER")));
        
        roles.add(role);
        person.setRoles(roles);
        
        personRepository.save(person);
        LoginResponse loginResponse = new LoginResponse(person.generateToken(), person);
		return loginResponse;
	}
}
