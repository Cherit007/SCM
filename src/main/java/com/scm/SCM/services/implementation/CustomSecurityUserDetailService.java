package com.scm.SCM.services.implementation;

import com.scm.SCM.entities.User;
import com.scm.SCM.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomSecurityUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user= userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found for username "+username));
        System.out.println(username + "| " + user.toString() + " username");
        return user;
    }
}
