package org.socialnetwork.codebase.DTO;

import java.util.UUID;

public class RelationDTO {
    private final String usernameInit;
    private final String usernameRecv;
    private final String relationType;

    public RelationDTO(String usernameInit, String usernameRecv, String relationType) {
        this.usernameInit = usernameInit;
        this.usernameRecv = usernameRecv;
        this.relationType = relationType;
    }

    public String getUsernameInit() {
        return usernameInit;
    }

    public String getUsernameRecv() {
        return usernameRecv;
    }

    public String getRelationType() { return relationType; }
}
