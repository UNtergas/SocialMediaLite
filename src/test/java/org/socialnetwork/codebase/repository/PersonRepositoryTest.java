package org.socialnetwork.codebase.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.socialnetwork.codebase.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person("John", "Doe", new Date());
        person = personRepository.save(person);
    }

    @Test
    public void testCreatePerson(){
        assertNotNull(person.getPersonID());
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testFindPersonByID(){
        Optional<Person> fPerson = personRepository.findById(person.getPersonID());
        assertTrue(fPerson.isPresent());
        assertEquals(person.getPersonID(),fPerson.get().getPersonID());
    }

    @Test
    public void testUpdatePerson(){
        person.setFirstName("William");
        Person updatedPerson = personRepository.save(person);
        assertEquals("William", updatedPerson.getFirstName());
    }

    @Test
    public void testDeletePerson(){
        personRepository.deleteById(person.getPersonID());
        Optional<Person> dPerson = personRepository.findById(person.getPersonID());
        assertFalse(dPerson.isPresent());
    }

}
