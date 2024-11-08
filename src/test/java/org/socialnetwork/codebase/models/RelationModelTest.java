package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class RelationModelTest {
    @Test
    public void testRelationModelCreationSimple() {
        Relation relation = new Relation(RelationType.friend);
        assertEquals(RelationType.friend, relation.getRelationType());
    }
    @Test
    public void testGetterSetter() {
        Relation relation = new Relation(RelationType.friend);
        assertEquals(RelationType.friend, relation.getRelationType());
        relation.setRelationType(RelationType.college);
        assertEquals(RelationType.college, relation.getRelationType());
    }
}
