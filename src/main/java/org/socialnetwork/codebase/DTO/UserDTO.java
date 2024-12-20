package org.socialnetwork.codebase.DTO;

import java.util.UUID;

public class UserDTO {
    private final UUID userID;
    private final String username;
    private final String description;

    public UserDTO(UUID userID, String username, String description) {
        this.userID = userID;
        this.username = username;
        this.description = description;
    }

    public UUID getUserID() { return userID; }
    public String getUsername() { return username; }

    public String getDescription() {
        return description;
    }
}
