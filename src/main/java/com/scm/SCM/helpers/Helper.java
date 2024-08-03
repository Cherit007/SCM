package com.scm.SCM.helpers;

import com.scm.SCM.entities.Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        if(authentication instanceof OAuth2AuthenticationToken) {

            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oAuth2User=(OAuth2User) authentication.getPrincipal();
            String username="";
            if (authorizedClientRegistrationId.equals(Provider.GOOGLE.toString().toLowerCase())) {
                System.out.println("Getting data from google");
                username=oAuth2User.getAttribute("email").toString();
            } else if (authorizedClientRegistrationId.equals(Provider.GITHUB.toString().toLowerCase())) {
                System.out.println("Getting data from github");
                username=oAuth2User.getAttribute("email")!=null ? oAuth2User.getAttribute("email") : oAuth2User.getAttribute("login")+"@gmail.com";
            }
            return username;
        }
        System.out.println("Getting data from normal sign-in");
        return authentication.getName();
    }
}
