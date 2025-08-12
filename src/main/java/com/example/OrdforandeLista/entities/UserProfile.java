package com.example.OrdforandeLista.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // matches join table column
    private Long user_id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 200)
    private String linkedInUrl;

    @Column(nullable = false, length = 200)
    private String profilePictureUrl;

    @Column(nullable = false, length = 500)
    private String profilePitch;

    @Column(nullable = false)
    private Boolean hasBoardEducation;

    @Column
    private Integer boardExperienceYears;

    @Column
    private Integer executiveExperienceYears;

    @Column(length = 100)
    private String leadershipPosition;

    @ElementCollection
    @CollectionTable(name = "user_profile_board_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> boardRoles;

    @ElementCollection
    @CollectionTable(name = "user_profile_company_types", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> companyTypes;

    @Column(length = 50)
    private String phoneNumber;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String currentPosition;

    @Column(nullable = false)
    private Boolean hasLeadershipEducation;

    @Column(length = 500)
    private String leadershipEducationDescription;

    @Column
    private Integer leadershipExperienceYears;

    @Column(nullable = false)
    private Boolean agreedToTerms;

    // ✅ Key competencies are the tags
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_profile_key_competencies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> keyCompetencies = new HashSet<>();
}

