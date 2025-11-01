package com.example.OrdforandeLista.dto;


import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class RegisterUserProfileInputTest {

    @Test
    void shouldCreateRecordCorrectly() {
        Set<Long> competencies = Set.of(1L, 2L, 3L);

        RegisterUserProfileInput dto = new RegisterUserProfileInput(
                "Anna",
                "Svensson",
                "anna@example.com",
                competencies
        );

        assertEquals("Anna", dto.firstName());
        assertEquals("Svensson", dto.lastName());
        assertEquals("anna@example.com", dto.email());
        assertEquals(competencies, dto.keyCompetencyIds());
    }

    @Test
    void shouldSupportEquality() {
        Set<Long> competencies = Set.of(5L, 7L);

        RegisterUserProfileInput a = new RegisterUserProfileInput("John", "Doe", "john@example.com", competencies);
        RegisterUserProfileInput b = new RegisterUserProfileInput("John", "Doe", "john@example.com", competencies);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        Set<Long> competencies = Set.of(10L);

        RegisterUserProfileInput dto = new RegisterUserProfileInput("Lisa", "Karlsson", "lisa@ex.com", competencies);

        String str = dto.toString();
        assertTrue(str.contains("Lisa"));
        assertTrue(str.contains("Karlsson"));
        assertTrue(str.contains("lisa@ex.com"));
        assertTrue(str.contains("10"));
    }
}

