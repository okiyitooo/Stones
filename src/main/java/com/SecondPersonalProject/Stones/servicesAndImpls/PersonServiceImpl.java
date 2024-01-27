package com.SecondPersonalProject.Stones.servicesAndImpls;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SecondPersonalProject.Stones.controllers.NoStonesException;
import com.SecondPersonalProject.Stones.exceptions.EmptyPageException;
import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.StoneAlreadyOwnedException;
import com.SecondPersonalProject.Stones.exceptions.StoneNotFoundException;
import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.models.Stone;
import com.SecondPersonalProject.Stones.repositories.PersonRepository;
import com.SecondPersonalProject.Stones.repositories.StoneRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private StoneRepository stoneRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private StoneService stoneService;
	
	@Override
    public Person birthPerson(String email, String firstName, String lastName, String password) {
        if (email == null || firstName == null || lastName == null || password == null) {
            throw new IllegalArgumentException("All fields are required to create a user.");
        }

        if (personRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("User with the email " + email + " already exists.");
        }

        Person person = new Person();
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);
//        person.setPassword(passwordEncoder.encode(password)); // Encode password securely
        person.setPassword(password);

        return personRepository.save(person);
    }

	@Override
	public Person updatePerson(long personId, Person person) throws PersonNotFoundException {

		Person personToUpdate = personRepository.findById(personId)
	            .orElseThrow(() -> new PersonNotFoundException("Person with ID " + personId + " not found"));

	    // Validate input data (optional, but recommended)
	    // ...

	    personToUpdate.setFirstName(person.getFirstName());
	    personToUpdate.setLastName(person.getLastName());
	    personToUpdate.setEmail(person.getEmail());
	    personToUpdate.setPassword(person.getPassword());

	    Person updatedPerson = personRepository.save(personToUpdate);

	    return updatedPerson;
	    
	}

	@Override
	public Person whoIsThis(long id) throws PersonNotFoundException {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person with ID " + id + " not found"));
		return person;
	}

	@Transactional
	@Override
	public Person kill(long id) throws PersonNotFoundException {
		Person person = whoIsThis(id);
		personRepository.deleteById(id);
		return person;
	}

	@Override
	public Person whoIsThis(String email) throws PersonNotFoundException {
		Person person = personRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Person with email " + email + " not found"));
		return person;
	}

	@Transactional
	@Override
	public Person kill(String email) throws PersonNotFoundException {
		Person person = whoIsThis(email);
		personRepository.deleteByEmail(email);
		return person;
	}
	
	

	// In PersonServiceImpl:
	@Override
	public Page<Person> getAll(Pageable pageable) throws EmptyPageException {
	    // Delegate pagination to the repository
		try {
		    Page<Person> page =  personRepository.findAll(
		        pageable
		    );
		    if (page.isEmpty())
	        	throw new EmptyPageException("No People in this page");
	        return page;
	    } catch (EmptyPageException e) {
	    	throw e;
	    } 
	    catch (Exception ex) {
	        throw new ServiceException("Error retrieving People", ex);
	    }
	}
	
	@Override
    @Transactional 
    public Person takeStone(long personId, long stoneId) throws StoneNotFoundException, StoneAlreadyOwnedException, PersonNotFoundException {
		Person person = whoIsThis(personId);
        Stone stone = stoneService.whatIsThis(stoneId);

        if (stone.getPerson()!=null) {
            throw new StoneAlreadyOwnedException("Stone is not available for taking");
        }
        
        stone.setPerson(person);
        person.getStones().add(stone);

        stoneRepository.save(stone);
        personRepository.save(person);

        // (e.g., logging, notifications) ->later

        return person;
    }

	@Override
    @Transactional
    public Person removeStone(long stoneId, long personId) throws StoneNotFoundException, PersonNotFoundException, IllegalStateException {

		Person person = whoIsThis(personId);
        Stone stone = stoneService.whatIsThis(stoneId);

        if (!person.equals(stone.getPerson())) {
            throw new IllegalStateException("Stone does not belong to the specified person");
        }

        person.getStones().remove(stone);
        stone.setPerson(null);
        stoneRepository.save(stone);

        return personRepository.save(person);
    }

	@Override
    public List<Stone> getAllStones(long personId) throws PersonNotFoundException, NoStonesException {
            Person person = whoIsThis(personId);
            List<Stone> stones = person.getStones();
            if (stones.isEmpty()) throw new NoStonesException("Person with id " + personId + " has no stones");
            return stones;
    }
}
