package com.example.OrdforandeLista.input;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdminUserInputTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        AdminUserInput input = new AdminUserInput("test@example.com", "password123", "ADMIN");

        Set<ConstraintViolation<AdminUserInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertEquals("test@example.com", input.email());
        assertEquals("password123", input.password());
        assertEquals("ADMIN", input.role());
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        AdminUserInput input = new AdminUserInput("", "password123", "ADMIN");

        Set<ConstraintViolation<AdminUserInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        AdminUserInput input = new AdminUserInput("test@example.com", "", "ADMIN");

        Set<ConstraintViolation<AdminUserInput>> violations = validator.validate(input);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    @Test
    void shouldAllowNullRole() {
        AdminUserInput input = new AdminUserInput("test@example.com", "password123");

        Set<ConstraintViolation<AdminUserInput>> violations = validator.validate(input);

        assertTrue(violations.isEmpty());
        assertNull(input.role());
    }

    @Test
    void twoArgConstructorShouldSetRoleToNull() {
        AdminUserInput input = new AdminUserInput("admin@test.com", "pass123");

        assertEquals("admin@test.com", input.email());
        assertEquals("pass123", input.password());
        assertNull(input.role());
    }
}

