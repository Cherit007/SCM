 package com.scm.SCM.entities;

 import jakarta.persistence.*;
 import lombok.*;
 import org.hibernate.Hibernate;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.userdetails.UserDetails;

 import java.util.*;
 import java.util.stream.Collectors;

 @Table(name = "users")
 @Getter
 @Setter
 @Entity
 @Builder
 @AllArgsConstructor
 @NoArgsConstructor
 @ToString
 public class User implements UserDetails {
     @Id
     private String userId;
     @Column(name = "user_name",nullable = false)
     private String name;
     @Column(unique = true,nullable = false)
     private String email;
     @Getter(AccessLevel.NONE)
     private String password;
     @Column(length =100)
     private String about;
     @Column(length = 500)
     private String profileLink;
     private String phoneNumber;
     @Getter(AccessLevel.NONE)
     private boolean enabled=true;
     private boolean emailVerified=false;
     private boolean phoneVerified=false;

     @Enumerated(value = EnumType.STRING)
    @Builder.Default
     private Provider provider = Provider.SELF;
     private String providerUserId;

     //mappedBy bcoz if not given then two mapping tables would create
     //cascade bcoz if user gets removed then all his contacts are removed
     @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
     private List<Contact> contacts= new ArrayList<>();

     @ElementCollection(fetch = FetchType.EAGER)
     private List<String> roleList=new ArrayList<>();

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
         Collection<SimpleGrantedAuthority> roles=roleList.stream().map(role ->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
     }

     @Override
     public String getPassword() {
         return this.password;
     }

     @Override
     public String getUsername() {
         return this.email;
     }

     @Override
     public boolean isAccountNonExpired() {
         return true;
     }

     @Override
     public boolean isAccountNonLocked() {
         return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
         return true;
     }

     @Override
     public boolean isEnabled() {
         return true;
     }
 }
