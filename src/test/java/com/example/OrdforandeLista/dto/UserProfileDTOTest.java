package com.example.OrdforandeLista.dto;
import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenRequiredFieldsMissing() {
        UserProfileDTO dto = new UserProfileDTO();

        Set<ConstraintViolation<UserProfileDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("First name is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Last name is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("LinkedIn URL is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Profile pitch is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Please specify if you have board education")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Please specify if you have leadership education")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("You must agree to the terms and conditions")));
    }

    @Test
    void shouldFailIfAgreementIsFalse() {
        UserProfileDTO dto = validDto();
        dto.setAgreedToTerms(false);

        Set<ConstraintViolation<UserProfileDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("You must agree to the terms and conditions")));
    }

    @Test
    void shouldPassWithValidData() {
        UserProfileDTO dto = validDto();
        Set<ConstraintViolation<UserProfileDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldMapFromEntity() {
        Tag t1 = new Tag(1L, "Leadership");
        UserProfile entity = new UserProfile();
        entity.setUserId(42L);
        entity.setFirstName("Anna");
        entity.setLastName("Svensson");
        entity.setEmail("anna@example.com");
        entity.setLinkedInUrl("https://linkedin.com/in/anna");
        entity.setProfilePitch("Experienced leader");
        entity.setHasBoardEducation(true);
        entity.setHasLeadershipEducation(true);
        entity.setAgreedToTerms(true);
        entity.setKeyCompetencies(Set.of(t1));

        UserProfileDTO dto = UserProfileDTO.fromEntity(entity);

        assertNotNull(dto);
        assertEquals(42L, dto.getUserId());
        assertEquals("Anna", dto.getFirstName());
        assertEquals("Svensson", dto.getLastName());
        assertEquals("anna@example.com", dto.getEmail());
        assertEquals(1, dto.getKeyCompetencies().size());
        assertTrue(dto.getKeyCompetencies().stream().anyMatch(t -> t.getName().equals("Leadership")));
    }

    private UserProfileDTO validDto() {
        return UserProfileDTO.builder()
                .userId(1L)
                .firstName("Anna")
                .lastName("Svensson")
                .email("anna@example.com")
                .linkedInUrl("https://linkedin.com/in/anna")
                .profilePitch("Executive leader")
                .hasBoardEducation(true)
                .hasLeadershipEducation(true)
                .agreedToTerms(true)
                .boardExperienceYears(2)
                .executiveExperienceYears(5)
                .leadershipExperienceYears(3)
                .boardRoles(List.of("Chair"))
                .companyTypes(List.of("Tech"))
                .keyCompetencies(Set.of(new Tag(1L, "Leadership")))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

