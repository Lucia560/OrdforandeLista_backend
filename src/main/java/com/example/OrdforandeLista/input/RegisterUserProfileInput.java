package com.example.OrdforandeLista.input;


import java.util.List;

public record RegisterUserProfileInput(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String company,
        String currentPosition,
        String linkedInUrl,
        String profilePitch,
        Boolean hasBoardEducation,
        Boolean hasLeadershipEducation,
        String leadershipEducationDescription,
        List<String> boardRoles,
        List<String> companyTypes,
        List<Long> keyCompetencyIds,
        Integer boardExperienceYears,
        Integer executiveExperienceYears,
        Integer leadershipExperienceYears,
        Boolean agreedToTerms
) {}
