package com.example.OrdforandeLista.input;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserProfileInputTest {

    @Test
    void shouldCreateRecordProperly() {
        RegisterUserProfileInput input = new RegisterUserProfileInput(
                "Lucia",
                "Andersson",
                "lucia@example.com",
                "0701234567",
                "TechCorp",
                "CTO",
                "https://linkedin.com/in/lucia",
                "Experienced board leader",
                true,
                true,
                "Executive leadership training",
                List.of("Chairman", "Board Member"),
                List.of("Tech", "Finance"),
                List.of(1L, 2L),
                5,
                10,
                7,
                true
        );

        assertEquals("Lucia", input.firstName());
        assertEquals("Andersson", input.lastName());
        assertEquals("lucia@example.com", input.email());
        assertEquals("0701234567", input.phoneNumber());
        assertEquals("TechCorp", input.company());
        assertEquals("CTO", input.currentPosition());
        assertEquals("https://linkedin.com/in/lucia", input.linkedInUrl());
        assertEquals("Experienced board leader", input.profilePitch());
        assertTrue(input.hasBoardEducation());
        assertTrue(input.hasLeadershipEducation());
        assertEquals("Executive leadership training", input.leadershipEducationDescription());
        assertEquals(List.of("Chairman", "Board Member"), input.boardRoles());
        assertEquals(List.of("Tech", "Finance"), input.companyTypes());
        assertEquals(List.of(1L, 2L), input.keyCompetencyIds());
        assertEquals(5, input.boardExperienceYears());
        assertEquals(10, input.executiveExperienceYears());
        assertEquals(7, input.leadershipExperienceYears());
        assertTrue(input.agreedToTerms());
    }

    @Test
    void shouldAllowNullValues() {
        RegisterUserProfileInput input = new RegisterUserProfileInput(
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null, null
        );

        assertNull(input.firstName());
        assertNull(input.lastName());
        assertNull(input.email());
        assertNull(input.agreedToTerms());
        assertNull(input.keyCompetencyIds());
    }
}


