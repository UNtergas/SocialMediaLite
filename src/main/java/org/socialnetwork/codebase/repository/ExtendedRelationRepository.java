/***
 * !NOT CURRENT IN USE
 */

package org.socialnetwork.codebase.repository;

import java.util.UUID;


public interface ExtendedRelationRepository {
    void deleteRelationBubbling(UUID relationID);
}
