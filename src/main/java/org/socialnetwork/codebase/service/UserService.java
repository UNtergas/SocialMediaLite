package org.socialnetwork.codebase.service;

import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUser(UUID userID) {
        return userRepository.findById(userID);
    }

    public List<User> getUsersNotConnectedToUser(String username) {
        List<User> usersConnectedToUser = userRepository.findUsersWithRelationToSpecificUser(username);
        return getAllUsers().stream()
                .filter(user -> !usersConnectedToUser.contains(user) && !user.getUsername().equals(username))
                .toList();
    }

    public List<User> getUsersWithNoRelations() {
        return userRepository.findUsersWithNoRelations();
    }

    public List<User> getUserWithRelationToSpecificUser(String username) {
        return userRepository.findUsersWithRelationToSpecificUser(username);
    }

    public List<User> getUserByDescription(String keyword) {
        return userRepository.findByDescriptionContaining(keyword);
    }

    public List<User> getUsersWithMoreThanOrEqualsToNRelations(int count) {
        return userRepository.findUsersWithMoreThanOrEqualsToNRelations(count);
    }
    public List<User> getUserWithMultipleRelationTypes() {
        return userRepository.findUsersWithMultipleRelationTypes();
    }


    public void deleteUser(UUID userID) {
        userRepository.deleteById(userID);
    }

}
