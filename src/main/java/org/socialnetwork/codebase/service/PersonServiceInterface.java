package org.socialnetwork.codebase.service;

import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.models.Relation;
import org.socialnetwork.codebase.models.RelationType;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface PersonServiceInterface {
      public Person createPerson(String firstName, String lastName, Date dateOfBirth);
      public Optional<Person> getPerson(UUID personID);
      public Person updatePerson(UUID personID, String firstName, String lastName, Date dateOfBirth);
      public Person patchPerson(UUID personID, Optional<String> firstName, Optional<String> lastName, Optional<Date> dateOfBirth);
      public void deletePerson(UUID personID);

      public Relation createRelation(UUID person1Id, UUID person2Id, RelationType relationType);
      public Relation updateRelationType(UUID relationID, RelationType newRelationType);
    public void deleteRelation(UUID relationID);
}
