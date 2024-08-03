package com.scm.SCM.services.implementation;

import com.scm.SCM.entities.User;
import com.scm.SCM.helpers.AppConstants;
import com.scm.SCM.helpers.ResourceNotFoundException;
import com.scm.SCM.repositories.UserRepo;
import com.scm.SCM.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void saveUser(User user) {
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> updateUser(User user){
        User updatedUser=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        updatedUser.setAbout(user.getAbout());
        updatedUser.setProfileLink(user.getProfileLink());
        updatedUser.setEnabled(user.isEnabled());

        updatedUser.setEmailVerified(user.isEmailVerified());
        updatedUser.setPhoneVerified(user.isPhoneVerified());
        updatedUser.setProvider(user.getProvider());
        updatedUser.setProviderUserId(user.getProviderUserId());
        User savedUser = userRepo.save(updatedUser);
        return Optional.of(savedUser);
    }

    public void deleteUser(User user){
        User updatedUser=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
        userRepo.delete(updatedUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
