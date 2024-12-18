package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    private User user;
    private User user2;
    @BeforeEach
    public void setUp() {
        user = new User("kan", "qwerty");
        user2 = new User("tan", "qwerty");
    }

    @Test
    public void testAddRelationshipInitiated() {
        Relation relation = new Relation(RelationType.FRIEND, user, user2);

        assertTrue(user.getRelationsInit().contains(relation));
        assertTrue(user2.getRelationsRecv().contains(relation));
    }

    @Test
    public void testRemoveRelationshipInitiated() {
        Relation relation = new Relation(RelationType.FRIEND, user, user2);

        user.removeRelationInitiation(relation);

        assertFalse(user.getRelationsInit().contains(relation));
        assertFalse(user2.getRelationsRecv().contains(relation));
    }
    @Test
    public void testRemoveRelationshipRecv() {
        Relation relation = new Relation(RelationType.FRIEND, user, user2);

        user2.removeRelationReceived(relation);
        assertFalse(user.getRelationsInit().contains(relation));
        assertFalse(user2.getRelationsRecv().contains(relation));
    }

}
