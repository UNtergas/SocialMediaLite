package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
public class PersonModelTest {

    private Person person;
    private Person person2;
    @BeforeEach
    public void setUp() {
        person = new Person("John", "Doe", new Date());
        person2 = new Person("Jane", "Macarel", new Date());
    }
    @Test
    public void testPersonCreation() {

        assertNotNull(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testGetterSetter() {
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        person.setLastName("Doe2");
        assertEquals("Doe2", person.getLastName());
    }

    @Test
    public void testAddRelationshipInitiated() {
        Relation relation = new Relation(RelationType.friend, person, person2);

        assertTrue(person.getRelationsInit().contains(relation));
        assertTrue(person2.getRelationsRecv().contains(relation));
    }

    @Test
    public void testRemoveRelationshipInitiated() {
        Relation relation = new Relation(RelationType.friend, person, person2);

        person.removeRelationInitiation(relation);

        assertFalse(person.getRelationsInit().contains(relation));
        assertFalse(person2.getRelationsRecv().contains(relation));
    }
    @Test
    public void testRemoveRelationshipRecv() {
        Relation relation = new Relation(RelationType.friend, person, person2);

        person2.removeRelationReceived(relation);
        assertFalse(person.getRelationsInit().contains(relation));
        assertFalse(person2.getRelationsRecv().contains(relation));
    }

}
