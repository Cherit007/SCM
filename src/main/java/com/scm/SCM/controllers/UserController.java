package com.scm.SCM.controllers;

import com.scm.SCM.helpers.Helper;
import com.scm.SCM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.scm.SCM.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    @RequestMapping("/profile")
    public String userProfile(Model model, Authentication authentication){
        return "user/profile";
    }
}
