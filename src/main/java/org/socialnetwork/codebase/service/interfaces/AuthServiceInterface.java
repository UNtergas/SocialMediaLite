package org.socialnetwork.codebase.service.interfaces;

import org.socialnetwork.codebase.models.User;

public interface AuthServiceInterface {
    public User registerUser(String username, String password);

    public User authenticateUser(String username, String password);
}
