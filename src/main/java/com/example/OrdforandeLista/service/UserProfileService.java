package com.example.OrdforandeLista.service;

import com.example.OrdforandeLista.dto.TagDTO;
import com.example.OrdforandeLista.dto.UserProfileDTO;
import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.input.RegisterUserProfileInput;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final TagService tagService;

    public UserProfileService(UserProfileRepository userProfileRepository, TagService tagService) {
        this.userProfileRepository = userProfileRepository;
        this.tagService = tagService;
    }


    public void deleteUser(Long userId) {
        if (!userProfileRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
        userProfileRepository.deleteById(userId);
    }

    public UserProfileDTO registerUser(RegisterUserProfileInput input) {
        // Get TagDTOs from service
        List<TagDTO> tagDTOList = tagService.getTagsByIds(input.keyCompetencyIds());

        // Convert TagDTOs to Tag entities
        Set<Tag> competencies = tagDTOList.stream()
                .map(dto -> Tag.builder()
                        .tagId(dto.getTagId())
                        .name(dto.getName())
                        .build())
                .collect(Collectors.toSet());

        UserProfile user = UserProfile
                .builder()
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
                .boardExperienceYears(input.boardExperienceYears())
                .executiveExperienceYears(input.executiveExperienceYears())
                .leadershipExperienceYears(input.leadershipExperienceYears())
                .agreedToTerms(input.agreedToTerms())
                .keyCompetencies(competencies)
                .build();

        UserProfile savedUser = userProfileRepository.save(user);
        return UserProfileDTO.fromEntity(savedUser); // <- Convert entity to DTO here
    }

    public UserProfileDTO updateUser(Long userId, RegisterUserProfileInput input) {
        UserProfile updatedUser = userProfileRepository.findById(userId).map(user -> {
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

           Set<TagDTO> competencies = new HashSet<>(tagService.getTagsByIds(input.keyCompetencyIds()));

            user.setKeyCompetencies(competencies.stream().map(TagDTO::toEntity).collect(Collectors.toSet()));

            return userProfileRepository.save(user);
        }).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " does not exist"));

        return UserProfileDTO.fromEntity(updatedUser);
    }
}
