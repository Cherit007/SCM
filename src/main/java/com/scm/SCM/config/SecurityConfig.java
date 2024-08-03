package com.scm.SCM.config;

import com.scm.SCM.services.implementation.CustomSecurityUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomSecurityUserDetailService customSecurityUserDetailService;

    @Autowired
    private  OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customSecurityUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/user/**").authenticated();
                    authorize.anyRequest().permitAll();
                });
        httpSecurity.formLogin(loginForm -> {
            loginForm.loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .defaultSuccessUrl("/user/dashboard", true)
                    .failureUrl("/login?error")
                    .failureHandler((request, response, exception) -> {
                        System.err.println("Authentication failed: " + exception.getMessage());
                        exception.printStackTrace();
                        response.sendRedirect("/login?error");
                    })
                    .usernameParameter("email")
                    .passwordParameter("password");
        }).csrf(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(loginForm -> loginForm
                .successHandler((request, response, authentication) -> {
                    System.out.println("User " + authentication.getName() + " logged in successfully");
                    response.sendRedirect("/user/dashboard");
                }));
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
             logoutForm.logoutSuccessUrl("/login?logout=true");
        });
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oAuthAuthenticationSuccessHandler);
        });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
