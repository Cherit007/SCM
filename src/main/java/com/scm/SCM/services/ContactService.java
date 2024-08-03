package com.scm.SCM.services;

import com.scm.SCM.entities.Contact;
import com.scm.SCM.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface ContactService {
    void saveContact(Contact contact);
    Page<Contact> getByUser(User user, int page,int size,String sortBy,String direction);
//    Optional<User> getUserById(String id);
//    List<User> getAllUsers();
//    Optional<User> updateUser(User user);
//    void deleteUser(User user);
//    User getUserByEmail(String email);
}


