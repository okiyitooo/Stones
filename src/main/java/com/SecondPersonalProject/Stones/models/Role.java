package com.SecondPersonalProject.Stones.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long roleId;
	@Column(unique=true)
	String name;
	@ManyToMany
	List<Person> people;
	
	public Role (String name) {
		this.name=name;
	}
	
	public enum RoleType {
		ADMIN,
		USER
	}
}
