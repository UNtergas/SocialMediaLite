package org.socialnetwork.codebase.models;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Relation {
    @Id
    @GeneratedValue
    private UUID relationID;

    @Enumerated(EnumType.STRING)
    private RelationType relationType;

    @ManyToOne
    @JoinColumn(name="userInit_id")
    private User userInit;

    @ManyToOne
    @JoinColumn(name="userRecv_id")
    private User userRecv;

    public Relation(RelationType relationType, User userInit, User userRecv) {
        this.relationType = relationType;
        this.userInit = userInit;
        this.userRecv = userRecv;

        userInit.addRelationInitiation(this);
        userRecv.addRelationReceived(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relation)) return false;
        Relation relation = (Relation) o;
        return this.relationID.toString().equals(relation.relationID.toString());
    }
    @Override
    public int hashCode() {
        return Objects.hash(relationID);
    }

    @Override
    public String toString() {
        return relationID.toString() + " " + relationType.toString() + " " + userInit.toString() + " " + userRecv.toString();
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

    public User getUserRecv() {
        return userRecv;
    }

    public User getUserInit() {
        return userInit;
    }

    public void setUserRecv(User userRecv) {
        this.userRecv = userRecv;
    }

    public void setUserInit(User userInit) {
        this.userInit = userInit;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }


}
