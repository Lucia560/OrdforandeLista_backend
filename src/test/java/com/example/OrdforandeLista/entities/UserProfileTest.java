package com.example.OrdforandeLista.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

class UserProfileTest {

    @Test
    void shouldBuildUserProfileCorrectly() {
        Tag tag1 = Tag.builder().tagId(1L).name("Leadership").build();
        Tag tag2 = Tag.builder().tagId(2L).name("Finance").build();

        UserProfile profile = UserProfile.builder()
                .userId(100L)
                .firstName("Anna")
                .lastName("Svensson")
                .email("anna@example.com")
                .linkedInUrl("https://linkedin.com/anna")
                .profilePictureUrl("https://img.com/anna.png")
                .profilePitch("Experienced leader in finance.")
                .hasBoardEducation(true)
                .boardExperienceYears(5)
                .executiveExperienceYears(3)
                .leadershipPosition("CEO")
                .boardRoles(List.of("Chair", "Member"))
                .companyTypes(List.of("Tech", "Finance"))
                .phoneNumber("123456789")
                .company("BigTech AB")
                .currentPosition("CEO")
                .hasLeadershipEducation(true)
                .leadershipEducationDescription("MBA & Executive Program")
                .leadershipExperienceYears(10)
                .agreedToTerms(true)
                .keyCompetencies(Set.of(tag1, tag2))
                .build();

        assertEquals("Anna", profile.getFirstName());
        assertEquals("Svensson", profile.getLastName());
        assertEquals("anna@example.com", profile.getEmail());
        assertEquals("Experienced leader in finance.", profile.getProfilePitch());
        assertTrue(profile.getHasBoardEducation());
        assertEquals(2, profile.getKeyCompetencies().size());
        assertTrue(profile.getKeyCompetencies().contains(tag1));
    }

    @Test
    void shouldInitializeKeyCompetenciesAsEmptySet() {
        UserProfile profile = UserProfile.builder().build();

        assertNotNull(profile.getKeyCompetencies());
        assertTrue(profile.getKeyCompetencies().isEmpty());
    }

    @Test
    void shouldAddKeyCompetency() {
        UserProfile profile = UserProfile.builder().build();
        Tag tag = Tag.builder().tagId(1L).name("Leadership").build();

        profile.getKeyCompetencies().add(tag);

        assertEquals(1, profile.getKeyCompetencies().size());
        assertTrue(profile.getKeyCompetencies().contains(tag));
    }

    @Test
    void shouldAllowNullOptionalFields() {
        UserProfile profile = UserProfile.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .linkedInUrl("https://linkedin.com")
                .profilePitch("Pitch")
                .hasBoardEducation(false)
                .hasLeadershipEducation(false)
                .agreedToTerms(true)
                .build();

        assertNull(profile.getProfilePictureUrl());
        assertNull(profile.getCompany());
        assertNull(profile.getLeadershipPosition());
        assertNull(profile.getBoardExperienceYears());
    }

    @Test
    void shouldModifyRoleAndCompanyTypeLists() {
        UserProfile profile = UserProfile.builder()
                .firstName("Test")
                .lastName("User")
                .email("mail@mail.com")
                .linkedInUrl("linkedin")
                .profilePitch("pitch")
                .hasBoardEducation(true)
                .hasLeadershipEducation(true)
                .agreedToTerms(true)
                .boardRoles(new java.util.ArrayList<>())
                .companyTypes(new java.util.ArrayList<>())
                .build();

        profile.getBoardRoles().add("Chair");
        profile.getCompanyTypes().add("Tech");

        assertEquals(1, profile.getBoardRoles().size());
        assertEquals(1, profile.getCompanyTypes().size());
        assertTrue(profile.getBoardRoles().contains("Chair"));
    }
}
