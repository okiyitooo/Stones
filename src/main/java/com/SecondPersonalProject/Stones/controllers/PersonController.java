package com.SecondPersonalProject.Stones.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SecondPersonalProject.Stones.exceptions.EmptyPageException;
import com.SecondPersonalProject.Stones.exceptions.NoStonesException;
import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.ResourceNotFoundException;
import com.SecondPersonalProject.Stones.exceptions.StoneAlreadyOwnedException;
import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.models.Stone;
import com.SecondPersonalProject.Stones.servicesAndImpls.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	@Autowired
    private PersonService personService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody Person person) {
        try {
            Person createdPerson = personService.birthPerson(person.getEmail(), person.getFirstName(), person.getLastName(), person.getPassword());
            return ResponseEntity.created(null).body(createdPerson);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/update/{personId}")
    public ResponseEntity<Person> updatePerson(@PathVariable("personId") long personId, @RequestBody Person person) {
        try {
            Person updatedPerson = personService.updatePerson(personId, person);
            return ResponseEntity.ok(updatedPerson);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/get/{personId}")
    public ResponseEntity<Person> whoIsThis(@PathVariable(value = "personId") long personId) {
        try {
            Person person = personService.whoIsThis(personId);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/kill/{personId}")
    public ResponseEntity<Person> kill(@PathVariable(value = "personId") long personId) {
        try {
            Person person = personService.kill(personId);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/get/email/{email}")
    public ResponseEntity<Person> whoIsThis(@PathVariable("email") String email) {
        try {
            Person person = personService.whoIsThis(email);
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/kill/email/{email}")
    public ResponseEntity<Void> kill(@PathVariable("email") String email) {
        try {
            personService.kill(email);
            return ResponseEntity.noContent().build();
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/all")
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam( name="pageSize",defaultValue = "10") int pageSize) {
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			Page<Person> peopleFromPage = personService.getAll(pageable);
			return ResponseEntity.ok(peopleFromPage.get().collect(Collectors.toList()));
		} catch (EmptyPageException e) {
			return ResponseEntity.notFound().build();
		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @GetMapping("/{personId}/stones")
    public ResponseEntity<?> getAllStones(@PathVariable long personId) {
        try {
            List<Stone> stones = personService.getAllStones(personId);
            return ResponseEntity.ok(stones);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NoStonesException e) {
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @PutMapping("/{personId}/takeStone/{stoneId}")
    public ResponseEntity<?> takeStone(@PathVariable long personId, @PathVariable long stoneId) {
        try {
            Person updatedPerson = personService.takeStone(personId, stoneId);
            return ResponseEntity.ok(updatedPerson); // Return the updated person
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (StoneAlreadyOwnedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{personId}/removeStone/{stoneId}")
    public ResponseEntity<?> removeStone(@PathVariable long personId, @PathVariable long stoneId) {
        try {
            Person updatedPerson = personService.removeStone(stoneId, personId);
            return ResponseEntity.ok(updatedPerson);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }    
}