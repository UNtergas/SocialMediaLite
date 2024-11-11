package org.socialnetwork.codebase.service;

import org.socialnetwork.codebase.models.Person;

public interface AuthServiceInterface {
    public Person registerPerson(String username, String password);

    public Person authenticatePerson(String username, String password);
}
