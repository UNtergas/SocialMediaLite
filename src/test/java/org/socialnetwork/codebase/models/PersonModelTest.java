package org.socialnetwork.codebase.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
public class PersonModelTest {
    @Test
    public void testPersonCreation() {
        Person person = new Person("John", "Doe", new Date());

        assertNotNull(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testGetterSetter() {
        Person person = new Person("John", "Doe", new Date());
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        person.setLastName("Doe2");
        assertEquals("Doe2", person.getLastName());
    }
}
