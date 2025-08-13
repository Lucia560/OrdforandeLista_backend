package com.example.OrdforandeLista.service;

import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.input.RegisterUserProfileInput;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final TagService tagService;

    public UserProfileService(UserProfileRepository userProfileRepository, TagService tagService) {
        this.userProfileRepository = userProfileRepository;
        this.tagService = tagService;
    }

    public UserProfile registerUser(RegisterUserProfileInput input) {
        // Map keyCompetencyIds -> Set<Tag>
        Set<Tag> competencies = new HashSet<>(tagService.getTagsByIds(input.keyCompetencyIds()));

        UserProfile user = UserProfile.builder()
                .firstName(input.firstName())
                .lastName(input.lastName())
                .email(input.email())
                .phoneNumber(input.phoneNumber())
                .company(input.company())
                .currentPosition(input.currentPosition())
                .linkedInUrl(input.linkedInUrl())
                .profilePitch(input.profilePitch())
                .hasBoardEducation(input.hasBoardEducation())
                .hasLeadershipEducation(input.hasLeadershipEducation())
                .leadershipEducationDescription(input.leadershipEducationDescription())
                .boardRoles(input.boardRoles())
                .companyTypes(input.companyTypes())
                .keyCompetencies(competencies)      // <— mapped Set<Tag>
                .boardExperienceYears(input.boardExperienceYears())
                .executiveExperienceYears(input.executiveExperienceYears())
                .leadershipExperienceYears(input.leadershipExperienceYears())
                .agreedToTerms(input.agreedToTerms())
                .build();

        return userProfileRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userProfileRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
        userProfileRepository.deleteById(userId);
    }

    public UserProfile updateUser(Long userId, RegisterUserProfileInput input) {
        return userProfileRepository.findById(userId).map(user -> {
            user.setFirstName(input.firstName());
            user.setLastName(input.lastName());
            user.setEmail(input.email());
            user.setPhoneNumber(input.phoneNumber());
            user.setCompany(input.company());
            user.setCurrentPosition(input.currentPosition());
            user.setLinkedInUrl(input.linkedInUrl());
            user.setProfilePitch(input.profilePitch());
            user.setHasBoardEducation(input.hasBoardEducation());
            user.setHasLeadershipEducation(input.hasLeadershipEducation());
            user.setLeadershipEducationDescription(input.leadershipEducationDescription());
            user.setBoardRoles(input.boardRoles());
            user.setCompanyTypes(input.companyTypes());
            user.setBoardExperienceYears(input.boardExperienceYears());
            user.setExecutiveExperienceYears(input.executiveExperienceYears());
            user.setLeadershipExperienceYears(input.leadershipExperienceYears());
            user.setAgreedToTerms(input.agreedToTerms());

            // Map keyCompetencyIds -> Set<Tag>
            Set<Tag> competencies = new HashSet<>(tagService.getTagsByIds(input.keyCompetencyIds()));
            user.setKeyCompetencies(competencies);

            return userProfileRepository.save(user);
        }).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " does not exist"));
    }
}









