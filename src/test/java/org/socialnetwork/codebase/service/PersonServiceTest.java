package org.socialnetwork.codebase.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.repository.PersonRepository;
import org.socialnetwork.codebase.repository.RelationRepository;


import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private RelationRepository relationRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPersons() {
        List<Person> people = Arrays.asList(new Person("John", "Doe", new Date()), new Person("Jane", "Doe", new Date()));
        Mockito.when(personRepository.findAll()).thenReturn(people);
        List<Person> result = personService.getAllPersons();
        assertEquals(2, result.size());
        Mockito.verify(personRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetPersonById() {
        UUID personID = UUID.randomUUID();
        Person person = new Person("John", "Doe", new Date());
        Mockito.when(personRepository.findById(personID)).thenReturn(Optional.of(person));

        Optional<Person> result = personService.getPerson(personID);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        Mockito.verify(personRepository, Mockito.times(1)).findById(personID);

    }
    @Test
    void testCreatePerson() {
        Person person = new Person("John", "Doe", new Date());
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Person result = personService.createPerson("John", "Doe", new Date());
        assertEquals("John", result.getFirstName());
        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

    @Test
    void testUpdatePerson() {
        UUID personID = UUID.randomUUID();
        Person person = new Person("John", "Doe", new Date());
        Mockito.when(personRepository.findById(personID)).thenReturn(Optional.of(person));
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Person result = personService.updatePerson(personID, "Jane", "Smith", new Date());
        assertEquals("Jane", result.getFirstName());
        Mockito.verify(personRepository, Mockito.times(1)).findById(personID);
        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

    @Test
    void testPatchPerson(){
        UUID personId = UUID.randomUUID();
        Person person = new Person("John", "Doe", new Date());
        Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        Mockito.when(personRepository.save(person)).thenReturn(person);

        Person result = personService.patchPerson(personId, Optional.of("Jane"), Optional.empty(), Optional.empty());

        assertEquals("Jane", result.getFirstName());
        Mockito.verify(personRepository, Mockito.times(1)).findById(personId);
        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

    @Test
    void testDeletePerson() {
        UUID personId = UUID.randomUUID();

        personService.deletePerson(personId);

        Mockito.verify(personRepository, Mockito.times(1)).deleteById(personId);
    }
}
