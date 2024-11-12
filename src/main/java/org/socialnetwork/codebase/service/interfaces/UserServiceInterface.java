package org.socialnetwork.codebase.service.interfaces;

import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;

import java.util.*;

public interface UserServiceInterface {

      public List<User> getAllUsers();

      public User createUser(String firstName, String lastName, Date dateOfBirth);
      public Optional<User> getUser(UUID userID);
      public User updateUser(UUID userID, String firstName, String lastName, Date dateOfBirth);
      public User patchUser(UUID userID, Optional<String> firstName, Optional<String> lastName, Optional<Date> dateOfBirth);
      public void deleteUser(UUID userID);

      public List<Relation> getAllRelations(UUID userID);
      public Relation createRelation(UUID userInitID, UUID userRecvID, RelationType relationType);
      public Relation updateRelationType(UUID relationID, RelationType newRelationType);
      public void deleteRelation(UUID relationID);
}
