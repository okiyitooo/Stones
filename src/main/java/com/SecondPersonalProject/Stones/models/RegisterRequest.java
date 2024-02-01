package com.SecondPersonalProject.Stones.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	String email;
	String password;
	String firstName;
	String lastName;
}
