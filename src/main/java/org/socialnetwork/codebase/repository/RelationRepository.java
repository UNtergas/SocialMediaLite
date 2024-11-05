package org.socialnetwork.codebase.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.socialnetwork.codebase.models.Relation;

import java.util.UUID;

@Repository
public interface RelationRepository extends CrudRepository<Relation, UUID> {

}
