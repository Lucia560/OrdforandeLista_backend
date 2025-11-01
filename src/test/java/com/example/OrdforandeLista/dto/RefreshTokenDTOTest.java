package com.example.OrdforandeLista.dto;
import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.entities.RefreshToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenRefreshTokenIsBlank() {
        RefreshTokenDTO dto = new RefreshTokenDTO();
        dto.setRefreshToken("");
        dto.setExpirationTime(Instant.now());

        Set<ConstraintViolation<RefreshTokenDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Refresh token is required")));
    }

    @Test
    void shouldFailWhenExpirationTimeIsNull() {
        RefreshTokenDTO dto = new RefreshTokenDTO();
        dto.setRefreshToken("abcd1234");
        dto.setExpirationTime(null);

        Set<ConstraintViolation<RefreshTokenDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Expiration time is required")));
    }

    @Test
    void shouldPassWithValidData() {
        RefreshTokenDTO dto = new RefreshTokenDTO(1L, "token123", Instant.now(), 10L);

        Set<ConstraintViolation<RefreshTokenDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldSerializeInstantCorrectly() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        RefreshTokenDTO dto =
                new RefreshTokenDTO(1L, "token123",
                        Instant.parse("2025-01-01T10:15:30.000Z"), 5L);

        String json = mapper.writeValueAsString(dto);

        assertTrue(json.contains("2025-01-01T10:15:30Z") || json.contains("2025-01-01T10:15:30.000Z")
        );

    }

    @Test
    void shouldMapFromEntity() {
        AdminUser user = new AdminUser();
        user.setId(42L);

        RefreshToken entity = new RefreshToken();
        entity.setId(1L);
        entity.setRefreshToken("abc123");
        entity.setExpirationTime(Instant.parse("2025-01-01T10:00:00Z"));
        entity.setAdminUser(user);

        RefreshTokenDTO dto = RefreshTokenDTO.fromEntity(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("abc123", dto.getRefreshToken());
        assertEquals(Instant.parse("2025-01-01T10:00:00Z"), dto.getExpirationTime());
        assertEquals(42L, dto.getAdminUserId());
    }


    @Test
    void fromEntityShouldReturnNullWhenEntityIsNull() {
        assertNull(RefreshTokenDTO.fromEntity(null));
    }
}

