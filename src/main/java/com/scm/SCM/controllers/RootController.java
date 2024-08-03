package com.scm.SCM.controllers;

import com.scm.SCM.entities.User;
import com.scm.SCM.helpers.Helper;
import com.scm.SCM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication){
        if(authentication==null) return;
        String username= Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);

        model.addAttribute("loggedInUser", user);
    }
}
