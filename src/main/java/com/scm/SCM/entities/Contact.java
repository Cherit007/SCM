package com.scm.SCM.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String about;

    private String phoneNumber;
    private boolean favourite=false;
    private String websiteLink;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();
}
