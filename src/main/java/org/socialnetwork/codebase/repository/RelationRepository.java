package org.socialnetwork.codebase.repository;

import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.models.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.socialnetwork.codebase.models.Relation;

import java.util.UUID;

@Repository
public interface RelationRepository extends JpaRepository<Relation, UUID> {

     @Query("SELECT COUNT(r) > 0 FROM Relation r WHERE " +
           "(r.userInit = :userInit AND r.userRecv = :userRecv) " +
           "OR (r.userInit = :userRecv AND r.userRecv = :userInit)")
     boolean existsByUsers(@Param("userInit") User userInit,
                           @Param("userRecv") User userRecv);

     @Modifying
     @Query("DELETE FROM Relation r WHERE r.userInit = :user OR r.userRecv = :user")
     void deleteRelationByUserInitOrUserRecv(@Param("user") User user);
}
