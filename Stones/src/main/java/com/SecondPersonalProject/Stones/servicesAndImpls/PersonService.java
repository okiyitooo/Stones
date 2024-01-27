package com.SecondPersonalProject.Stones.servicesAndImpls;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SecondPersonalProject.Stones.controllers.NoStonesException;
import com.SecondPersonalProject.Stones.exceptions.EmptyPageException;
import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.StoneAlreadyOwnedException;
import com.SecondPersonalProject.Stones.exceptions.StoneNotFoundException;
import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.models.Stone;

public interface PersonService {
	Person birthPerson( String email, String first_name, String last_name, String password);
	Person updatePerson(long personId, Person person) throws PersonNotFoundException;
	Person whoIsThis(long id) throws PersonNotFoundException;
	Person kill(long id) throws PersonNotFoundException;
	Person whoIsThis(String email) throws PersonNotFoundException;
	Person kill(String email) throws PersonNotFoundException;
//	Page<Person> getAll(PersonFilter filter, Pageable pageable); // -> later
	Page<Person> getAll(Pageable pageable) throws EmptyPageException;
	Person takeStone(long personId, long stoneId) throws StoneNotFoundException, StoneAlreadyOwnedException, PersonNotFoundException;
	List<Stone> getAllStones(long personId) throws PersonNotFoundException, NoStonesException;
	Person removeStone(long stoneId, long personId) throws StoneNotFoundException, PersonNotFoundException, IllegalStateException;
}
