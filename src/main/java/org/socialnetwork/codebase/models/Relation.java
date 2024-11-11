package org.socialnetwork.codebase.models;

import jakarta.persistence.*;
import org.socialnetwork.codebase.models.RelationType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Relation {
    @Id
    @GeneratedValue
    private UUID relationID;

    @Enumerated(EnumType.STRING)
    private RelationType relationType;

    @ManyToOne
    @JoinColumn(name="personInit_id")
    private Person personInit;

    @ManyToOne
    @JoinColumn(name="personRecv_id")
    private Person personRecv;

    public Relation(RelationType relationType, Person person1, Person person2) {
        this.relationType = relationType;
        this.personInit = person1;
        this.personRecv = person2;

        person1.addRelationInitiation(this);
        person2.addRelationReceived(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relation)) return false;
        Relation relation = (Relation) o;
        return Objects.equals(relationID, relation.relationID);
    }
    @Override
    public int hashCode() {
        return Objects.hash(relationID);
    }

    @PreRemove
    private void removeRelation() {
        if(personInit!=null) {
            personInit.getRelationsInit().remove(this);
            this.personInit=null;
        }
        if(personRecv!=null) {
            personRecv.getRelationsInit().remove(this);
            this.personRecv=null;
        }
    }

    public Relation() {
        this.relationType = RelationType.friend;
    }

    public UUID getRelationID() {
        return relationID;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public Person getPersonRecv() {
        return personRecv;
    }

    public Person getPersonInit() {
        return personInit;
    }

    public void setPersonRecv(Person personRecv) {
        this.personRecv = personRecv;
    }

    public void setPersonInit(Person personInit) {
        this.personInit = personInit;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }


}
