package com.SecondPersonalProject.Stones.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long personId;
	@Column(unique=true)
	String email;
	String firstName;
	String lastName;
	String password;
	
	@OneToMany(mappedBy="person")
	List<Stone> stones;
}
