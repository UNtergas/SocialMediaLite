package org.socialnetwork.codebase.service;

import org.socialnetwork.codebase.exceptions.UsernameAlreadyTakenException;
import org.socialnetwork.codebase.models.User;
import org.socialnetwork.codebase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        User user = new User(username, password);
        return this.userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        Optional<User> userOptional = this.userRepository.findByUsername(username);
        if(userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional.get();
        }
        return null;
    }
}
