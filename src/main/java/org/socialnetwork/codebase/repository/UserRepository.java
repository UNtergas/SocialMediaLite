package org.socialnetwork.codebase.repository;

import org.socialnetwork.codebase.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.description LIKE %:keyword%")
    List<User> findByDescriptionContaining(@Param("keyword") String keyword);

    @Query(
            "SELECT u FROM User u " +
            "LEFT JOIN Relation rInit ON u.userID = rInit.userInit.userID " +
            "LEFT JOIN Relation rRecv ON u.userID = rRecv.userRecv.userID " +
            "WHERE rInit.relationID IS NULL AND rRecv.relationID IS NULL"
    )
    List<User> findUsersWithNoRelations();

    /**
     * "SELECT resultUser FROM User resultUser " +
     * "JOIN Relation r ON resultUser = r.userInit OR resultUser = r.userRecv " +
     * "WHERE (r.userInit.userID = :targetUserID OR r.userRecv.userID = :targetUserID) AND resultUser.userId != :targetUserID"
     */
    @Query(
            "SELECT DISTINCT resultUser FROM User resultUser " +
            "JOIN Relation r ON resultUser = r.userInit OR resultUser = r.userRecv " +
            "JOIN User targetUser ON r.userInit = targetUser OR r.userRecv = targetUser " +
            "WHERE targetUser.username = :targetUsername AND resultUser.username != :targetUsername"
    )
    List<User> findUsersWithRelationToSpecificUser(@Param("targetUsername") String targetUsername);


    @Query("SELECT u FROM User u WHERE SIZE(u.relationsInit) + SIZE(u.relationsRecv) >= :count")
    List<User> findUsersWithMoreThanOrEqualsToNRelations(@Param("count") int count);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN Relation r1 ON u = r1.userInit OR u = r1.userRecv " +
            "JOIN Relation r2 ON u = r2.userInit OR u = r2.userRecv " +
            "WHERE r1.relationType <> r2.relationType ")
    List<User> findUsersWithMultipleRelationTypes();

}
