package org.socialnetwork.codebase.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.socialnetwork.codebase.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("kan", "qwerty");
        user = userRepository.save(user);
    }

    @Test
    public void testCreateUser(){
        assertNotNull(user.getUserID());
        assertEquals("kan", user.getUsername());
    }

    @Test
    public void testFindUserByID(){
        Optional<User> fUser = userRepository.findById(user.getUserID());
        assertTrue(fUser.isPresent());
        assertEquals(user.getUserID(),fUser.get().getUserID());
    }

    @Test
    public void testUpdateUser(){
        user.setUsername("kan_updated");
        User updatedUser = userRepository.save(user);
        assertEquals("kan_updated", updatedUser.getUsername());
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteById(user.getUserID());
        Optional<User> dUser = userRepository.findById(user.getUserID());
        assertFalse(dUser.isPresent());
    }

}
