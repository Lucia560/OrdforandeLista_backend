package com.example.OrdforandeLista.entities;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private RefreshToken createValidToken() {
        AdminUser user = AdminUser.builder()
                .id(1L)
                .email("admin@example.com")
                .password("password123")
                .build();

        return RefreshToken.builder()
                .id(10L)
                .refreshToken("valid-refresh-token")
                .expirationTime(Instant.now().plusSeconds(3600))
                .adminUser(user)
                .build();
    }

    @Test
    void shouldPassValidationWhenValid() {
        RefreshToken token = createValidToken();
        Set<ConstraintViolation<RefreshToken>> violations = validator.validate(token);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenRefreshTokenBlank() {
        RefreshToken token = createValidToken();
        token.setRefreshToken("");

        Set<ConstraintViolation<RefreshToken>> violations = validator.validate(token);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Skriv in din nya refresh token")));
    }

    @Test
    void shouldFailWhenExpirationIsNull() {
        RefreshToken token = createValidToken();
        token.setExpirationTime(null);

        Set<ConstraintViolation<RefreshToken>> violations = validator.validate(token);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("expirationTime"))
        );
    }

    @Test
    void shouldHoldReferenceToAdminUser() {
        RefreshToken token = createValidToken();

        assertNotNull(token.getAdminUser());
        assertEquals(1L, token.getAdminUser().getId());
        assertEquals("admin@example.com", token.getAdminUser().getEmail());
    }

    @Test
    void shouldAllowSettingFields() {
        RefreshToken token = new RefreshToken();
        Instant exp = Instant.now().plusSeconds(300);

        token.setId(55L);
        token.setRefreshToken("newToken");
        token.setExpirationTime(exp);

        assertEquals(55L, token.getId());
        assertEquals("newToken", token.getRefreshToken());
        assertEquals(exp, token.getExpirationTime());
    }
}

