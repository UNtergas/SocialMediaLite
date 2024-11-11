package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class RelationModelTest {

    private Person person;
    private Person person2;
    private Relation relation;

    @BeforeEach
    public void setUp() {
        person = new Person("John", "Doe", new Date());
        person2 = new Person("Jane", "Macarel", new Date());
        relation = new Relation(RelationType.friend, person, person2);
    }
    @Test
    public void testRelationInit() {
        assertEquals(RelationType.friend, relation.getRelationType());
        assertEquals(person,relation.getPersonInit());
        assertEquals(person2,relation.getPersonRecv());

        assertTrue(person.getRelationsInit().contains(relation));
        assertTrue(person2.getRelationsRecv().contains(relation));
    }
    @Test
    public void testSetRelationType(){
        relation.setRelationType(RelationType.college);
        assertEquals(RelationType.college,relation.getRelationType());
    }
}
