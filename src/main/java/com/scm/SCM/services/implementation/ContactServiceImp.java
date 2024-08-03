package com.scm.SCM.services.implementation;

import com.scm.SCM.entities.Contact;
import com.scm.SCM.entities.User;
import com.scm.SCM.repositories.ContactRepo;
import com.scm.SCM.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private ContactRepo contactRepo;

    @Override
    public void saveContact(Contact contact) {
        String contactId= UUID.randomUUID().toString();
        contact.setId(contactId);
        contactRepo.save(contact);
    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {
        Sort sort= direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page,size,sort);
        return contactRepo.findByUser(user,pageable);
    }
}
