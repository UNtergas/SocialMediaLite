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
        List<User> people = Arrays.asList(new User("John", "Doe", new Date()), new User("Jane", "Doe", new Date()));
        Mockito.when(userRepository.findAll()).thenReturn(people);
        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        UUID userID = UUID.randomUUID();
        User user = new User("John", "Doe", new Date());
        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUser(userID);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(userID);

    }
    @Test
    void testCreateUser() {
        User user = new User("John", "Doe", new Date());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.createUser("John", "Doe", new Date());
        assertEquals("John", result.getFirstName());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        UUID userID = UUID.randomUUID();
        User user = new User("John", "Doe", new Date());
        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.updateUser(userID, "Jane", "Smith", new Date());
        assertEquals("Jane", result.getFirstName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(userID);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testPatchUser(){
        UUID userID = UUID.randomUUID();
        User user = new User("John", "Doe", new Date());
        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userService.patchUser(userID, Optional.of("Jane"), Optional.empty(), Optional.empty());

        assertEquals("Jane", result.getFirstName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(userID);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        UUID userID = UUID.randomUUID();

        userService.deleteUser(userID);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userID);
    }
}
