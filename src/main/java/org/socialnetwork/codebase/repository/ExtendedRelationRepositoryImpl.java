/***
 * !NOT CURRENT IN USE
 */



package org.socialnetwork.codebase.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.socialnetwork.codebase.models.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ExtendedRelationRepositoryImpl implements ExtendedRelationRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void deleteRelationBubbling(UUID relationID) {
        Relation relation= em.find(Relation.class,relationID);
        if (relation == null) {
            throw new RuntimeException("Relation not found");
        }

        relation.getPersonInit().removeRelationInitiation(relation);

        em.remove(relation);
    }
}
