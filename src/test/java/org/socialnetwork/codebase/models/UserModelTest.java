package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
public class UserModelTest {

    private User user;
    private User user2;
    @BeforeEach
    public void setUp() {
        user = new User("John", "Doe", new Date());
        user2 = new User("Jane", "Macarel", new Date());
    }
    @Test
    public void testUserCreation() {

        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testGetterSetter() {
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        user.setLastName("Doe2");
        assertEquals("Doe2", user.getLastName());
    }

    @Test
    public void testAddRelationshipInitiated() {
        Relation relation = new Relation(RelationType.friend, user, user2);

        assertTrue(user.getRelationsInit().contains(relation));
        assertTrue(user2.getRelationsRecv().contains(relation));
    }

    @Test
    public void testRemoveRelationshipInitiated() {
        Relation relation = new Relation(RelationType.friend, user, user2);

        user.removeRelationInitiation(relation);

        assertFalse(user.getRelationsInit().contains(relation));
        assertFalse(user2.getRelationsRecv().contains(relation));
    }
    @Test
    public void testRemoveRelationshipRecv() {
        Relation relation = new Relation(RelationType.friend, user, user2);

        user2.removeRelationReceived(relation);
        assertFalse(user.getRelationsInit().contains(relation));
        assertFalse(user2.getRelationsRecv().contains(relation));
    }

}