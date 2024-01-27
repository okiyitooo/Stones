package com.SecondPersonalProject.Stones.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SecondPersonalProject.Stones.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	Optional<Person> findByEmail(String email);

	Long deleteByEmail(String email);

}
