package com.example.OrdforandeLista.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdminUserDTOTest {

    private static Validator validator;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    void testEmailValidationFailsWhenEmpty() {
        AdminUserDTO dto = AdminUserDTO.builder()
                .email("")
                .password("12345678")
                .build();

        Set<ConstraintViolation<AdminUserDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Email is required")));
    }

    @Test
    void testInvalidEmailFormat() {
        AdminUserDTO dto = AdminUserDTO.builder()
                .email("invalid-email")
                .password("12345678")
                .build();

        Set<ConstraintViolation<AdminUserDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("valid email")));
    }

    @Test
    void testPasswordMinimumLength() {
        AdminUserDTO dto = AdminUserDTO.builder()
                .email("test@example.com")
                .password("short")
                .build();

        Set<ConstraintViolation<AdminUserDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("at least 8 characters")));
    }

    @Test
    void testPasswordIsIgnoredInJson() throws Exception {
        AdminUserDTO dto = AdminUserDTO.builder()
                .id(1L)
                .email("test@example.com")
                .password("supersecret123")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertFalse(json.contains("password"), "Password should not appear in JSON output");
    }

    @Test
    void testDateFormatting() throws Exception {
        LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 30, 45);

        AdminUserDTO dto = AdminUserDTO.builder()
                .createdAt(now)
                .updatedAt(now)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("2025-01-01 12:30:45"));
    }
}

