package com.example.OrdforandeLista.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateAdminUserRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenUsernameIsBlank() {
        CreateAdminUserRequest request = new CreateAdminUserRequest();
        request.setUsername("");
        request.setPassword("test12345");
        request.setEmail("test@example.com");

        Set<ConstraintViolation<CreateAdminUserRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Username is required")));
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        CreateAdminUserRequest request = new CreateAdminUserRequest();
        request.setUsername("admin");
        request.setPassword("");
        request.setEmail("test@example.com");

        Set<ConstraintViolation<CreateAdminUserRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Password is required")));
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        CreateAdminUserRequest request = new CreateAdminUserRequest();
        request.setUsername("admin");
        request.setPassword("test12345");
        request.setEmail("");

        Set<ConstraintViolation<CreateAdminUserRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email is required")));
    }

    @Test
    void shouldPassWithValidData() {
        CreateAdminUserRequest request = new CreateAdminUserRequest();
        request.setUsername("admin");
        request.setPassword("test12345");
        request.setEmail("test@example.com");

        Set<ConstraintViolation<CreateAdminUserRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Validation should pass with valid data");
    }
}

