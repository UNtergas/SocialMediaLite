package org.socialnetwork.codebase.service;

import org.socialnetwork.codebase.exceptions.UsernameAlreadyTakenException;
import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements AuthServiceInterface{
    private final PersonRepository personRepository;

    @Autowired
    public AuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person registerPerson(String username, String password) {
        if (personRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        Person person = new Person(username, password);
        return this.personRepository.save(person);
    }

    @Override
    public Person authenticatePerson(String username, String password) {
        Optional<Person> personOptional = this.personRepository.findByUsername(username);
        if(personOptional.isPresent() && personOptional.get().getPassword().equals(password)) {
            return personOptional.get();
        }
        return null;
    }
}
