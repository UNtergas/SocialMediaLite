package org.socialnetwork.codebase.service.interfaces;

import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;

import java.util.*;

public interface UserServiceInterface {

      public List<User> getAllUsers();

      public Optional<User> getUser(UUID userID);
      public void deleteUser(UUID userID);

      public List<Relation> getAllRelations(UUID userID);
      public Relation createRelation(UUID userInitID, UUID userRecvID, RelationType relationType);
      public Relation updateRelationType(UUID relationID, RelationType newRelationType);
      public void deleteRelation(UUID relationID);
}
