package org.socialnetwork.codebase.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private UUID personID;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;


    // All Relations where this person is the one who sends relation invitation
    @OneToMany(mappedBy = "personInit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Relation> relationsInit = new HashSet<>();

    // All Relations where this person is the one who receives relation invitation
    @OneToMany(mappedBy = "personRecv", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Relation> relationsRecv = new HashSet<>();

    public Person(String username, String password) {
        this("default", "default", new Date());
        this.username = username;
        this.password = password;
    }

    public Person(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
    public Person() {
        this.firstName = "Empty";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID);
    }
    @PreRemove
    private void removePerson(){
        if(relationsInit!=null) {
            for (Relation relation : relationsInit) {
                relation.getPersonRecv().getRelationsRecv().remove(relation);
                relation.setPersonInit(null);
                relation.setPersonRecv(null);
            }
            relationsInit.clear();
        }

        if (relationsRecv != null) {
            for(Relation relation : relationsRecv){
                relation.getPersonInit().getRelationsInit().remove(relation);
                relation.setPersonInit(null);
                relation.setPersonRecv(null);
            }
            relationsRecv.clear();
        }
    }

    public UUID getPersonID() {
        return personID;
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
        relation.setPersonInit(this);
    }
    public void addRelationReceived(Relation relation) {
        relationsRecv.add(relation);
        relation.setPersonRecv(this);
    }
    public void removeRelationInitiation(Relation relation) {
        relationsInit.remove(relation);
        relation.getPersonRecv().getRelationsRecv().remove(relation);
        relation.setPersonRecv(null);
        relation.setPersonInit(null);
    }
    public void removeRelationReceived(Relation relation) {
        relationsRecv.remove(relation);
        relation.getPersonInit().getRelationsInit().remove(relation);
        relation.setPersonInit(null);
        relation.setPersonRecv(null);
    }
}
