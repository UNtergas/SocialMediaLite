package org.socialnetwork.codebase.DTO;

import java.util.UUID;

public class UserDTO {
    private final UUID userID;
    private final String username;

    public UserDTO(UUID userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public UUID getUserID() { return userID; }
    public String getUsername() { return username; }
}
