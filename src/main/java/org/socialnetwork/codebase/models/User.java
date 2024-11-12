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

    private String firstName;

    private String lastName;

    private Date dateOfBirth;


    // All Relations where this user is the one who sends relation invitation
    @OneToMany(mappedBy = "userInit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Relation> relationsInit = new HashSet<>();

    // All Relations where this user is the one who receives relation invitation
    @OneToMany(mappedBy = "userRecv", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Relation> relationsRecv = new HashSet<>();

    public User(String username, String password) {
        this("default", "default", new Date());
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
    public User() {
        this.firstName = "Empty";
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
    @PreRemove
    private void removeUser(){
        if(relationsInit!=null) {
            for (Relation relation : relationsInit) {
                relation.getUserRecv().getRelationsRecv().remove(relation);
                relation.setUserInit(null);
                relation.setUserRecv(null);
            }
            relationsInit.clear();
        }

        if (relationsRecv != null) {
            for(Relation relation : relationsRecv){
                relation.getUserInit().getRelationsInit().remove(relation);
                relation.setUserInit(null);
                relation.setUserRecv(null);
            }
            relationsRecv.clear();
        }
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

    public Set<Relation> getRelationsRecv() {
        return relationsRecv;
    }

    public Set<Relation> getRelationsInit() {
        return relationsInit;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
