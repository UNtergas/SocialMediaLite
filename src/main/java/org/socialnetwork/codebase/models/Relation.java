package org.socialnetwork.codebase.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
    @Id
    @GeneratedValue
    private UUID relationID;

    @Setter
    private RelationType relationType;

    @ManyToMany
    @JoinTable(
            name = "relation_person",
            joinColumns = @JoinColumn(name = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> persons;

    @Setter
    @OneToOne
    @JoinColumn(name="person1_id")
    private Person person1;

    @Setter
    @OneToOne
    @JoinColumn(name="person2_id")
    private Person person2;


}
