package com.scm.SCM.repositories;

import com.scm.SCM.entities.Contact;
import com.scm.SCM.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ContactRepo extends JpaRepository<Contact,String> {
    ArrayList<Contact> findAllById(String userId);
    Page<Contact> findByUser(User user, Pageable pageable);
}
