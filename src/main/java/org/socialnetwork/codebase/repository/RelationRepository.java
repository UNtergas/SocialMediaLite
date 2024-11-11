package org.socialnetwork.codebase.repository;

import org.socialnetwork.codebase.models.Person;
import org.socialnetwork.codebase.models.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.socialnetwork.codebase.models.Relation;

import java.util.UUID;

@Repository
public interface RelationRepository extends JpaRepository<Relation, UUID> {

     @Query("SELECT COUNT(r) > 0 FROM Relation r WHERE " +
           "(r.personInit = :person1 AND r.personRecv = :person2 AND r.relationType = :type) " +
           "OR (r.personInit = :person2 AND r.personRecv = :person1 AND r.relationType = :type)")
     boolean existsByPersonsAndType(@Param("person1") Person personInit,
                                   @Param("person2") Person personRecv,
                                   @Param("type") RelationType type);

}
