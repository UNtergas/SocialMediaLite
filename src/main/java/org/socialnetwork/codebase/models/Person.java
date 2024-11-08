package org.socialnetwork.codebase.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity

public class Person {
    @Id
    @GeneratedValue
    private UUID personID;


    private String firstName;

    private String lastName;

    private Date dateOfBirth;


    @ManyToMany(mappedBy = "persons", cascade = CascadeType.ALL)
    private Set<Relation> relations;

    public Person(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
    public Person() {
        this.firstName = "Empty";
    }

    public Person(String firstName, String lastName, Date dateOfBirth, Set<Relation> relations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.relations = relations;
    }

    public UUID getPersonID() {
        return personID;
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

    public Set<Relation> getRelations() {
        return relations;
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
}
