package org.socialnetwork.codebase.service;

import jakarta.transaction.Transactional;
import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;
import org.socialnetwork.codebase.repository.PersonRepository;
import org.socialnetwork.codebase.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService implements PersonServiceInterface {

    private PersonRepository personRepository;
    private RelationRepository relationRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, RelationRepository relationRepository) {
        this.personRepository = personRepository;
        this.relationRepository = relationRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person createPerson(String firstName, String lastName, Date dateOfBirth) {
        Person person = new Person(firstName, lastName, dateOfBirth);
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> getPerson(UUID personID) {
        return personRepository.findById(personID);
    }

    @Override
    public Person updatePerson(UUID personID, String firstName, String lastName, Date dateOfBirth) {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);
        return personRepository.save(person);
    }

    @Override
    public Person patchPerson(UUID personID, Optional<String> firstName, Optional<String> lastName, Optional<Date> dateOfBirth) {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        firstName.ifPresent(person::setFirstName);
        lastName.ifPresent(person::setLastName);
        dateOfBirth.ifPresent(person::setDateOfBirth);
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(UUID personID) {
        personRepository.deleteById(personID);
    }

    @Override
    public List<Relation> getAllRelations(UUID personID) {
        Person person = personRepository.findById(personID)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        List<Relation> allRelations = new ArrayList<>();
        allRelations.addAll(person.getRelationsInit());
        allRelations.addAll(person.getRelationsRecv());
        return allRelations;
    }


    boolean relationExists(Person person1 , Person person2, RelationType relationType) {
        return relationRepository.existsByPersonsAndType(person1, person2, relationType);
    }
    @Override
    @Transactional
    public Relation createRelation(UUID person1Id, UUID person2Id, RelationType relationType) {

        Person person1 = personRepository.findById(person1Id)
                .orElseThrow(() -> new RuntimeException("Person1 not found"));
        Person person2 = personRepository.findById(person2Id)
                .orElseThrow(() -> new RuntimeException("Person2 not found"));

        if(person1Id.compareTo(person2Id) >0) {
            Person temp = person1;
            person1 = person2;
            person2 = temp;
        }
        if(!relationExists(person1, person2, relationType)) {
            Relation relation = new Relation(relationType, person1, person2);
            relationRepository.save(relation);
            return relation;

        }
        throw new RuntimeException("Relation already exists");
    }


    @Override
    @Transactional
    public Relation updateRelationType(UUID relationID, RelationType newRelationType) {
        Relation relation = relationRepository.findById(relationID)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        relation.setRelationType(newRelationType);
        return relationRepository.save(relation);
    }

    @Override
    @Transactional
    public void deleteRelation(UUID relationID) {
        Relation relation = relationRepository.findById(relationID)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        relationRepository.deleteById(relationID);
    }


}
