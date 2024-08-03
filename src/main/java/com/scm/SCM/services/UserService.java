package com.scm.SCM.services;

import com.scm.SCM.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> getUserById(String id);
    List<User> getAllUsers();
    Optional<User> updateUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
}
