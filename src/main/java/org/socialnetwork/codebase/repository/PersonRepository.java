package org.socialnetwork.codebase.repository;

import org.socialnetwork.codebase.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {
}
