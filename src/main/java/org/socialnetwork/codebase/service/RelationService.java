package org.socialnetwork.codebase.service;

import jakarta.transaction.Transactional;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.repository.RelationRepository;
import org.socialnetwork.codebase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RelationService {
    private final RelationRepository relationRepository;
    private final UserRepository userRepository;

    @Autowired
    public RelationService(RelationRepository relationRepository, UserRepository userRepository) {
        this.relationRepository = relationRepository;
        this.userRepository = userRepository;
    }

    // Get all relations
    public List<Relation> getAllRelations() {
        return relationRepository.findAll();
    }

    // Get all relations of specific user
    public List<Relation> getAllRelationsOfUser(UUID userID) {
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


    @Transactional
    public Relation createRelation(UUID userInitID, UUID userRecvID, RelationType relationType) {
        User user1 = userRepository.findById(userInitID)
                .orElseThrow(() -> new RuntimeException("Person1 not found"));
        User user2 = userRepository.findById(userRecvID)
                .orElseThrow(() -> new RuntimeException("Person2 not found"));

        if(userInitID.compareTo(userRecvID) > 0) {
            User temp = user1;
            user1 = user2;
            user2 = temp;
        }
        if(!relationExists(user1, user2, relationType) && !user1.equals(user2)) {
            Relation relation = new Relation(relationType, user1, user2);
            relationRepository.save(relation);
            return relation;
        }
        throw new RuntimeException("Relation already exists");
    }

    @Transactional
    public Relation createRelationByUsernames(RelationType relationType, String usernameInit, String usernameRecv) {
        User userA = userRepository.findByUsername(usernameInit)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + usernameInit + " not found"));

        User userB = userRepository.findByUsername(usernameRecv)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + usernameRecv + " not found"));

        return createRelation(userA.getUserID(), userB.getUserID(), relationType);
    }


    @Transactional
    public Relation updateRelationType(UUID relationID, RelationType newRelationType) {
        Relation relation = relationRepository.findById(relationID)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        relation.setRelationType(newRelationType);
        return relationRepository.save(relation);
    }


    @Transactional
    public void deleteRelation(UUID relationID) {
        Relation relation = relationRepository.findById(relationID)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        relationRepository.deleteById(relationID);
    }
}
