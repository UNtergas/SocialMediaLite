package org.socialnetwork.codebase.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.repository.UserRepository;
import org.socialnetwork.codebase.repository.RelationRepository;


import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RelationRepository relationRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> people = Arrays.asList(new User("kan", "qwerty"), new User("tan", "qwerty"));
        Mockito.when(userRepository.findAll()).thenReturn(people);
        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        UUID userID = UUID.randomUUID();
        User user = new User("tan", "qwerty");
        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUser(userID);

        assertTrue(result.isPresent());
        assertEquals("tan", result.get().getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).findById(userID);

    }

    @Test
    void testDeleteUser() {
        UUID userID = UUID.randomUUID();

        userService.deleteUser(userID);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userID);
    }
}
