//TODO: FIX THIS TEST
//package org.socialnetwork.codebase.repository;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.socialnetwork.codebase.models.User;
//import org.socialnetwork.codebase.models.Relation;
//import org.socialnetwork.codebase.models.RelationType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//import java.util.Date;
//import java.util.Optional;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//public class RelationRepositoryTest {
//    @Autowired
//    private RelationRepository relationRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    private User userA;
//    private User userB;
//    private Relation relation;
//
//    @BeforeEach
//    public void setUp(){
//        userA = new User("Alice", "Smith", new Date());
//        userB = new User("Bob", "Jones", new Date());
//        userRepository.save(userA);
//        userRepository.save(userB);
//
//        relation = new Relation(RelationType.college, userA, userB);
//        relationRepository.save(relation);
//    }
//
//    @Test
//    public void testCreateRelation(){
//        assertNotNull(relation.getRelationID());
//        assertEquals(relation.getRelationType(), RelationType.college);
//        assertEquals(userA,relation.getUserInit());
//        assertEquals(userB,relation.getUserRecv());
//    }
//
//    @Test
//    public void testFindRelation(){
//        Optional<Relation> fRelation = relationRepository.findById(relation.getRelationID());
//        assertNotNull(fRelation.get());
//        assertEquals(relation.getRelationType(), fRelation.get().getRelationType());
//    }
//
//    @Test
//    public void testRelationExistBiDirectional(){
//        boolean exists = relationRepository.existsByUsersAndType(userA, userB,RelationType.college);
//        assertTrue(exists);
//
//        exists = relationRepository.existsByUsersAndType(userB, userA,RelationType.college);
//        assertTrue(exists);
//    }
//
//    @Test
//    public void testDeleteRelation(){
//        System.out.println(userA.getRelationsInit());
//        relationRepository.deleteById(relation.getRelationID());
//        Optional<Relation> fRelation = relationRepository.findById(relation.getRelationID());
//        assertFalse(fRelation.isPresent());
//
//        System.out.println(userA.getRelationsInit());
//        assertFalse(userA.getRelationsInit().contains(relation));
//        assertFalse(userB.getRelationsRecv().contains(relation));
//    }
//
//
////    @Test
//////    @Transactional
////    public void testDeleteUserRelation(){
////        userRepository.deleteById(userA.getUserID());
////        assertFalse(userRepository.existsById(userA.getUserID()));
////        /**
////         * !Warn :: CASCADING NOT WORKING
////         */
////        assertFalse(relationRepository.findById(relation.getRelationID()).isPresent());
////    }
//
//}
