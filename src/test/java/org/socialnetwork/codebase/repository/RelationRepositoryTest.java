package org.socialnetwork.codebase.repository;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RelationRepositoryTest {
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private PersonRepository  personRepository;

    private Person personA;
    private Person personB;
    private Relation relation;

    @BeforeEach
    public void setUp(){
        personA = new Person("Alice", "Smith", new Date());
        personB = new Person("Bob", "Jones", new Date());
        personRepository.save(personA);
        personRepository.save(personB);

        relation = new Relation(RelationType.college, personA, personB);
        relationRepository.save(relation);
    }

    @Test
    public void testCreateRelation(){
        assertNotNull(relation.getRelationID());
        assertEquals(relation.getRelationType(), RelationType.college);
        assertEquals(personA,relation.getPersonInit());
        assertEquals(personB,relation.getPersonRecv());
    }

    @Test
    public void testFindRelation(){
        Optional<Relation> fRelation = relationRepository.findById(relation.getRelationID());
        assertNotNull(fRelation.get());
        assertEquals(relation.getRelationType(), fRelation.get().getRelationType());
    }

    @Test
    public void testRelationExistBiDirectional(){
        boolean exists = relationRepository.existsByPersonsAndType(personA,personB,RelationType.college);
        assertTrue(exists);

        exists = relationRepository.existsByPersonsAndType(personB,personA,RelationType.college);
        assertTrue(exists);
    }

    @Test
    public void testDeleteRelation(){
        System.out.println(personA.getRelationsInit());
        relationRepository.deleteById(relation.getRelationID());
        Optional<Relation> fRelation = relationRepository.findById(relation.getRelationID());
        assertFalse(fRelation.isPresent());

        System.out.println(personA.getRelationsInit());
        assertFalse(personA.getRelationsInit().contains(relation));
        assertFalse(personB.getRelationsRecv().contains(relation));
    }


    @Test
//    @Transactional
    public void testDeletePersonRelation(){
        personRepository.deleteById(personA.getPersonID());
        assertFalse(personRepository.existsById(personA.getPersonID()));
        /**
         * !Warn :: CASCADING NOT WORKING
         */
        assertFalse(relationRepository.findById(relation.getRelationID()).isPresent());
    }

}
