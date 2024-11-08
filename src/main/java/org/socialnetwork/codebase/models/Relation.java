package org.socialnetwork.codebase.models;

import jakarta.persistence.*;
import org.socialnetwork.codebase.models.RelationType;
import java.util.Set;
import java.util.UUID;

@Entity
public class Relation {
    @Id
    @GeneratedValue
    private UUID relationID;


    private RelationType relationType;

    @ManyToMany
    @JoinTable(
            name = "relation_person",
            joinColumns = @JoinColumn(name = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> persons;


    @OneToOne
    @JoinColumn(name="person1_id")
    private Person person1;


    @OneToOne
    @JoinColumn(name="person2_id")
    private Person person2;

    public Relation(RelationType relationType) {
        this.relationType = relationType;
    }
    public Relation(RelationType relationType, Person person1, Person person2) {
        this.relationType = relationType;
        this.person1 = person1;
        this.person2 = person2;
    }

    public Relation() {
        this.relationType = RelationType.friend;
    }

    public Relation(RelationType relationType, Set<Person> persons, Person person1, Person person2) {
        this.relationType = relationType;
        this.persons = persons;
        this.person1 = person1;
        this.person2 = person2;
    }

    public UUID getRelationID() {
        return relationID;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }
}
