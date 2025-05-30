package com.example.OrdforandeLista.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;


    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;


    @Column(nullable = false,length = 50)
    private String email;

    @Column(nullable = false,length = 200)
    private String linkedInUrl;

    @Column(nullable = false,length = 200)
    private String profilePictureUrl;

    @Column(nullable = false,length = 500)
    private String profilePitch;

    @Column( nullable = false)
    private Boolean hasBoardEducation;

    @Column
    private Integer boardExperienceYears;

    @Column
    private Integer executiveExperienceYears;

    @Column( length = 100)
    private String leadershipPosition;

    @ElementCollection
    private List<String> boardRoles;

    @ElementCollection
    private List<String> companyTypes;

    @ElementCollection
    private List<String> keyCompetencies;

    @ManyToMany
     @JoinTable(
             name = "users_tags",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "tag_id")
     )
     private Set<Tag> tags = new HashSet<>();

}
