package com.example.OrdforandeLista.dto;

import com.example.OrdforandeLista.entities.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {
    private Long userId;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotBlank(message = "LinkedIn URL is required")
    @Size(max = 200, message = "LinkedIn URL cannot exceed 200 characters")
    private String linkedInUrl;

    @Size(max = 200, message = "Profile picture URL cannot exceed 200 characters")
    private String profilePictureUrl;

    @NotBlank(message = "Profile pitch is required")
    @Size(max = 500, message = "Profile pitch cannot exceed 500 characters")
    private String profilePitch;

    @NotNull(message = "Please specify if you have board education")
    private Boolean hasBoardEducation;

    @Min(value = 0, message = "Board experience years cannot be negative")
    private Integer boardExperienceYears;

    @Min(value = 0, message = "Executive experience years cannot be negative")
    private Integer executiveExperienceYears;

    @Size(max = 100, message = "Leadership position cannot exceed 100 characters")
    private String leadershipPosition;

    private List<String> boardRoles;
    private List<String> companyTypes;

    @Size(max = 50, message = "Phone number cannot exceed 50 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String company;

    @Size(max = 100, message = "Current position cannot exceed 100 characters")
    private String currentPosition;

    @NotNull(message = "Please specify if you have leadership education")
    private Boolean hasLeadershipEducation;

    @Size(max = 500, message = "Leadership education description cannot exceed 500 characters")
    private String leadershipEducationDescription;

    @Min(value = 0, message = "Leadership experience years cannot be negative")
    private Integer leadershipExperienceYears;

    @AssertTrue(message = "You must agree to the terms and conditions")
    private Boolean agreedToTerms;

    private Set<Tag> keyCompetencies = new HashSet<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Helper method to convert entity to DTO
    public static UserProfileDTO fromEntity(com.example.OrdforandeLista.entities.UserProfile entity) {
        if (entity == null) {
            return null;
        }

        return UserProfileDTO.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .linkedInUrl(entity.getLinkedInUrl())
                .profilePictureUrl(entity.getProfilePictureUrl())
                .profilePitch(entity.getProfilePitch())
                .hasBoardEducation(entity.getHasBoardEducation())
                .boardExperienceYears(entity.getBoardExperienceYears())
                .executiveExperienceYears(entity.getExecutiveExperienceYears())
                .leadershipPosition(entity.getLeadershipPosition())
                .boardRoles(entity.getBoardRoles())
                .companyTypes(entity.getCompanyTypes())
                .phoneNumber(entity.getPhoneNumber())
                .company(entity.getCompany())
                .currentPosition(entity.getCurrentPosition())
                .hasLeadershipEducation(entity.getHasLeadershipEducation())
                .leadershipEducationDescription(entity.getLeadershipEducationDescription())
                .leadershipExperienceYears(entity.getLeadershipExperienceYears())
                .agreedToTerms(entity.getAgreedToTerms())
                .keyCompetencies(entity.getKeyCompetencies())
                .build();
    }
}
