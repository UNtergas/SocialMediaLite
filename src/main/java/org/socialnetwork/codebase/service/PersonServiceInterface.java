package org.socialnetwork.codebase.service;

import jakarta.transaction.Transactional;
import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;

import java.util.*;

public interface PersonServiceInterface {

      public List<Person> getAllPersons();

      public Person createPerson(String firstName, String lastName, Date dateOfBirth);
      public Optional<Person> getPerson(UUID personID);
      public Person updatePerson(UUID personID, String firstName, String lastName, Date dateOfBirth);
      public Person patchPerson(UUID personID, Optional<String> firstName, Optional<String> lastName, Optional<Date> dateOfBirth);
      public void deletePerson(UUID personID);

      public List<Relation> getAllRelations(UUID personID);
      public Relation createRelation(UUID person1Id, UUID person2Id, RelationType relationType);
      public Relation updateRelationType(UUID relationID, RelationType newRelationType);
      public void deleteRelation(UUID relationID);
}
