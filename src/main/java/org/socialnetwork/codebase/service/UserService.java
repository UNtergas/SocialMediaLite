package org.socialnetwork.codebase.service;

import jakarta.transaction.Transactional;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;
import org.socialnetwork.codebase.repository.UserRepository;
import org.socialnetwork.codebase.repository.RelationRepository;
import org.socialnetwork.codebase.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    @Autowired
    public UserService(UserRepository userRepository, RelationRepository relationRepository) {
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String firstName, String lastName, Date dateOfBirth) {
        User user = new User(firstName, lastName, dateOfBirth);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(UUID userID) {
        return userRepository.findById(userID);
    }

    @Override
    public User updateUser(UUID userID, String firstName, String lastName, Date dateOfBirth) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        return userRepository.save(user);
    }

    @Override
    public User patchUser(UUID userID, Optional<String> firstName, Optional<String> lastName, Optional<Date> dateOfBirth) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        firstName.ifPresent(user::setFirstName);
        lastName.ifPresent(user::setLastName);
        dateOfBirth.ifPresent(user::setDateOfBirth);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userID) {
        userRepository.deleteById(userID);
    }

    @Override
    public List<Relation> getAllRelations(UUID userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Relation> allRelations = new ArrayList<>();
        allRelations.addAll(user.getRelationsInit());
        allRelations.addAll(user.getRelationsRecv());
        return allRelations;
    }


    boolean relationExists(User user1, User user2, RelationType relationType) {
        return relationRepository.existsByUsersAndType(user1, user2, relationType);
    }
    @Override
    @Transactional
    public Relation createRelation(UUID userInitID, UUID userRecvID, RelationType relationType) {

        User user1 = userRepository.findById(userInitID)
                .orElseThrow(() -> new RuntimeException("Person1 not found"));
        User user2 = userRepository.findById(userRecvID)
                .orElseThrow(() -> new RuntimeException("Person2 not found"));

        if(userInitID.compareTo(userRecvID) >0) {
            User temp = user1;
            user1 = user2;
            user2 = temp;
        }
        if(!relationExists(user1, user2, relationType)) {
            Relation relation = new Relation(relationType, user1, user2);
            relationRepository.save(relation);
            return relation;

        }
        throw new RuntimeException("Relation already exists");
    }


    @Override
    @Transactional
    public Relation updateRelationType(UUID relationID, RelationType newRelationType) {
        Relation relation = relationRepository.findById(relationID)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        relation.setRelationType(newRelationType);
        return relationRepository.save(relation);
    }

    @Override
    @Transactional
    public void deleteRelation(UUID relationID) {
        Relation relation = relationRepository.findById(relationID)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        relationRepository.deleteById(relationID);
    }


}
