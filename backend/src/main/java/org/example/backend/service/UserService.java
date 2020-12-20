package org.example.backend.service;


import org.example.backend.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();

    void deleteUserById(String id);
}