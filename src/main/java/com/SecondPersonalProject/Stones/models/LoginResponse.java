package com.SecondPersonalProject.Stones.models; // Adjust package name if needed

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private long personId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;

    public LoginResponse(String tiken, Person person) {
    	this.token = tiken;
    	this.personId = person.getPersonId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.roles = person.getRoles();
    }
}