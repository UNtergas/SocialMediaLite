package org.socialnetwork.codebase.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue
    private UUID userID;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String description;


    // All Relations where this user is the one who sends relation invitation
    @OneToMany(mappedBy = "userInit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relation> relationsInit = new ArrayList<>();

    // All Relations where this user is the one who receives relation invitation
    @OneToMany(mappedBy = "userRecv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relation> relationsRecv = new ArrayList<>();

    public User(String username, String password, String description) {
        this.username = username;
        this.password = password;
        this.description = description;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }

    public UUID getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Relation> getRelationsRecv() {
        return relationsRecv;
    }

    public List<Relation> getRelationsInit() {
        return relationsInit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addRelationInitiation(Relation relation) {
        relationsInit.add(relation);
        relation.setUserInit(this);
    }
    public void addRelationReceived(Relation relation) {
        relationsRecv.add(relation);
        relation.setUserRecv(this);
    }
    public void removeRelationInitiation(Relation relation) {
        relationsInit.remove(relation);
        relation.getUserRecv().getRelationsRecv().remove(relation);
        relation.setUserRecv(null);
        relation.setUserInit(null);
    }
    public void removeRelationReceived(Relation relation) {
        relationsRecv.remove(relation);
        relation.getUserInit().getRelationsInit().remove(relation);
        relation.setUserInit(null);
        relation.setUserRecv(null);
    }
}
