package org.socialnetwork.codebase.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.socialnetwork.codebase.models.*;
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

    /**
     * This setup script runs before every test
     * We create two users userA and userB, one relation of type college between them
     */
    @BeforeEach
    public void setUp() {
        userA = userRepository.save(new User("kan", "qwerty"));
        userB = userRepository.save(new User("tan", "qwerty"));
        relation = relationRepository.save(new Relation(RelationType.COLLEGE, userA, userB));

        relationRepository.flush();
        userRepository.flush();
    }

    /**
     * We have written a SQL instruction to check whether relation exists in both side of users
     * This test is to test this SQL instruction
     */
    @Test
    public void testRelationExistBiDirectional() {
        boolean exists = relationRepository.existsByUsers(userA, userB);
        assertTrue(exists);

        exists = relationRepository.existsByUsers(userB, userA);
        assertTrue(exists);
    }

    /**
     * Test the capability of removing relation.
     * Note: if the relation is already inside collections relationInit and relationRecv of
     * user, we can not remove it via relationRepository.deleteById(relation.relationId).
     * To remove the Relation, we must remove it in both Users in the relationship
     * This deleted Relation becomes 'Orphan child' and JPA will delete it in database
     */
    @Test
    public void ifUserRemoveRelationsInAppRelationInDatabaseMustBeDeleted() {
        // UserA want to remove relation with the UserB
        userA.getRelationsInit().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userB.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userB.getUsername())
        );
        userA.getRelationsRecv().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userB.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userB.getUsername())
        );

        userB.getRelationsInit().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userA.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userA.getUsername())
        );
        userB.getRelationsRecv().removeIf(targetRelation ->
                targetRelation.getUserInit().getUsername().equals(userA.getUsername()) ||
                        targetRelation.getUserRecv().getUsername().equals(userA.getUsername())
        );

        // The relation will be automatically removed on database level
        userA = userRepository.findByUsername(userA.getUsername()).get();
        userB = userRepository.findByUsername(userB.getUsername()).get();

        assertFalse(relationRepository.findById(relation.getRelationID()).isPresent());
        assertTrue(userA.getRelationsInit().isEmpty());
        assertTrue(userA.getRelationsRecv().isEmpty());
        assertTrue(userB.getRelationsInit().isEmpty());
        assertTrue(userB.getRelationsRecv().isEmpty());
    }

    /**
     * Test the scenario where the user is deleted.
     * The way we delete user is pretty tricky because the complexity of bidirectional relation between user and relation
     * we adopted at the beginning.
     * Here is the steps to delete user:
     * - Step 1: Find the user to be deleted. It is called to-be-deleted user
     * - Step 2: Find all other users to whom the to-be-deleted user has relation
     * - Step 3: In relations collection of previous found users, we remove the relations that are associated to to-be-deleted user
     * - Step 4: Clear all relations of to-be-deleted user
     * - Step 5: [Database level] Remove all associated relations of to-be-deleted user in database
     * - Step 6: [Database level] Remove the to-be-deleted user in database
     * Note that: Flush calls is optional.
     */
    @Test
    public void ifUserIsDeletedAssociatedRelationsMustBeDeleted(){
        userA = userRepository.findByUsername(userA.getUsername()).get();

        List<User> usersWhoHasRelationWithUserA = new ArrayList<>();
        for (Relation relationInit : userA.getRelationsInit()) {
            usersWhoHasRelationWithUserA.add(relationInit.getUserRecv());
        }
        for (Relation relationRecv : userA.getRelationsRecv()) {
            usersWhoHasRelationWithUserA.add(relationRecv.getUserInit());
        }

        for (User user : usersWhoHasRelationWithUserA) {
            User user_ = userRepository.findByUsername(user.getUsername()).get();
            user_.getRelationsInit().removeIf(
                    relation -> relation.getUserRecv().getUsername().equals(userA.getUsername())
            );
            user_.getRelationsRecv().removeIf(
                    relation -> relation.getUserInit().getUsername().equals(userA.getUsername())
            );
        }
        userA.getRelationsInit().clear();
        userA.getRelationsRecv().clear();
        relationRepository.deleteRelationByUserInitOrUserRecv(userA);
        userRepository.deleteById(userA.getUserID());
        relationRepository.flush();
        userRepository.flush();

        assertFalse(userRepository.existsById(userA.getUserID()));
        assertFalse(relationRepository.findById(relation.getRelationID()).isPresent());
        assertTrue(userRepository.existsById(userB.getUserID()));
        assertTrue(userB.getRelationsInit().isEmpty());
        assertTrue(userB.getRelationsRecv().isEmpty());
    }
}
