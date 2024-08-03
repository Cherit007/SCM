package com.scm.SCM.config;

import com.scm.SCM.entities.Provider;
import com.scm.SCM.entities.User;
import com.scm.SCM.helpers.AppConstants;
import com.scm.SCM.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
        String email="";
        User newUser=new User();
        System.out.println(user + "User" + authorizedClientRegistrationId);
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setPassword("password");
            newUser.setEnabled(true);
            newUser.setEmailVerified(true);
            newUser.setRoleList(List.of(AppConstants.ROLE_USER));
        if(authorizedClientRegistrationId.equals(Provider.GOOGLE.toString().toLowerCase())){
            email=user.getAttribute("email").toString();
            String name=user.getAttribute("name").toString();
            String picture=user.getAttribute("picture").toString();

            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setProfileLink(picture);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setProviderUserId(user.getName());
            newUser.setAbout("This account is created using google");
        }else if(authorizedClientRegistrationId.equals(Provider.GITHUB.toString().toLowerCase())){
            System.out.println(user + "User");
            email=user.getAttribute("email")!=null ? user.getAttribute("email") : user.getAttribute("login")+"@gmail.com";
            String name=user.getAttribute("login").toString();
            String picture=user.getAttribute("avatar_url").toString();
            newUser.setProvider(Provider.GITHUB);
            newUser.setProviderUserId(user.getName());
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProfileLink(picture);
            newUser.setAbout("This account is created using github");
        }

        User user1=userRepo.findByEmail(email).orElse(null);

        if(user1==null){
            userRepo.save(newUser);
        }
        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");
    }
}
