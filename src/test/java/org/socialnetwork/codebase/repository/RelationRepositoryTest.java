package org.socialnetwork.codebase.repository;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RelationRepositoryTest {
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private UserRepository userRepository;

    private User userA;
    private User userB;
    private Relation relation;

    @BeforeEach
    @Transactional
    public void setUp() {
        userA = userRepository.save(new User("kan", "qwerty"));
        userB = userRepository.save(new User("tan", "qwerty"));

        relation = relationRepository.save(new Relation(RelationType.college, userA, userB));
        relationRepository.flush();
        userRepository.flush();
    }

    @Test
    public void testCreateRelation() {
        assertNotNull(relation.getRelationID());
        assertEquals(relation.getRelationType(), RelationType.college);
        assertEquals(userA, relation.getUserInit());
        assertEquals(userB, relation.getUserRecv());
    }

    @Test
    public void testFindRelation() {
        Optional<Relation> fRelation = relationRepository.findById(relation.getRelationID());
        assertTrue(fRelation.isPresent());
        assertNotNull(fRelation.get());
        assertEquals(relation.getRelationType(), fRelation.get().getRelationType());
        assertEquals(relation.getUserInit().getUsername(), userA.getUsername());
        assertEquals(relation.getUserRecv().getUsername(), userB.getUsername());
    }

    @Test
    public void testRelationExistBiDirectional() {
        boolean exists = relationRepository.existsByUsersAndType(userA, userB, RelationType.college);
        assertTrue(exists);

        exists = relationRepository.existsByUsersAndType(userB, userA, RelationType.college);
        assertTrue(exists);
    }

    @Test
    public void testDeleteRelationThroughUser() {
        // Remove relations matching the condition in application level
        userA.getRelationsInit().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userB.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userB.getUsername())
        );
        userA.getRelationsRecv().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userB.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userB.getUsername())
        );

        userB.getRelationsInit().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userB.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userB.getUsername())
        );
        userB.getRelationsRecv().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userB.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userB.getUsername())
        );

        // The relation will be automatically removed on database level
        List<Relation> relations = relationRepository.findAll();
        assertEquals(0, relations.size());
    }


//    @Test
//    public void testDeleteUserRelation(){
//        userRepository.deleteById(userA.getUserID());
//        assertFalse(userRepository.existsById(userA.getUserID()));
//        /**
//         * !Warn :: CASCADING NOT WORKING
//         */
//        assertFalse(relationRepository.findById(relation.getRelationID()).isPresent());
//    }

}
