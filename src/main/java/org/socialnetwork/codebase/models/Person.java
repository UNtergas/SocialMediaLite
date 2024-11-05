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
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private UUID personID;
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private Date dateOfBirth;


    @ManyToMany(mappedBy = "persons", cascade = CascadeType.ALL)
    private Set<Relation> relations;

}
