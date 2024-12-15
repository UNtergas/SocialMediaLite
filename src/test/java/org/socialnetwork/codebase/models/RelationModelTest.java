package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class RelationModelTest {
    private User user;
    private User user2;
    private Relation relation;

    @BeforeEach
    public void setUp() {
        user = new User("kan", "qwerty");
        user2 = new User("tan", "qwerty");
        relation = new Relation(RelationType.friend, user, user2);
    }
    @Test
    public void testRelationInit() {
        assertEquals(RelationType.friend, relation.getRelationType());
        assertEquals(user,relation.getUserInit());
        assertEquals(user2,relation.getUserRecv());

        assertTrue(user.getRelationsInit().contains(relation));
        assertTrue(user2.getRelationsRecv().contains(relation));
    }
    @Test
    public void testSetRelationType(){
        relation.setRelationType(RelationType.college);
        assertEquals(RelationType.college,relation.getRelationType());
    }
}
