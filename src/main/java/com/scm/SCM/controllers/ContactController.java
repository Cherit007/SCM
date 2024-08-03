package com.scm.SCM.controllers;

import com.scm.SCM.entities.Contact;
import com.scm.SCM.entities.User;
import com.scm.SCM.forms.ContactForm;
import com.scm.SCM.helpers.AppConstants;
import com.scm.SCM.helpers.Helper;
import com.scm.SCM.helpers.Message;
import com.scm.SCM.helpers.MessageType;
import com.scm.SCM.services.ContactService;
import com.scm.SCM.services.ImageService;
import com.scm.SCM.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm",contactForm);
        return "user/add_contacts";
    }

    @RequestMapping("")
    public String index(@RequestParam(value = "page",defaultValue = "0") int page,
                        @RequestParam(value = "size",defaultValue = "2") int size,
                        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
                        @RequestParam(value = "direction",defaultValue = "asc") String direction,
                        Model model, Authentication authentication){

        String username= Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);
        Page<Contact> contacts= contactService.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contacts",contacts);
        return "user/contacts";
    }

    @RequestMapping(value = "/add-contact",method = RequestMethod.POST)
    public String addContactForUser(@Valid @ModelAttribute ContactForm contactForm, BindingResult rBindingResult, Authentication authentication,HttpSession session){
        System.out.println(contactForm + "hello ebr" );
        if(rBindingResult.hasErrors()) {
            Message message = Message.builder()
                    .content("Please correct the errors")
                    .type(MessageType.red)
                    .build();
            System.out.println(message);
            session.setAttribute("message",message);
            return "user/add_contacts";
        }
        String username= Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);
        String fileUrl=imageService.imageUpload(contactForm.getContactImage());
        Contact contact = Contact.builder()
                .name(contactForm.getName())
                .email(contactForm.getEmail())
                .phoneNumber(contactForm.getPhoneNumber())
                .description(contactForm.getDescription())
                .favourite(contactForm.isFavourite())
                .picture(fileUrl)
                .about(contactForm.getAbout())
                .user(user)
                .build();
        contactService.saveContact(contact);
        Message message = Message.builder()
                .content("Contact saved successfully")
                .type(MessageType.green)
                .build();
        System.out.println(message);
        session.setAttribute("message",message);
        return "redirect:/user/contacts/add";
    }
}
