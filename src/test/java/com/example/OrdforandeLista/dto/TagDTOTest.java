package com.example.OrdforandeLista.dto;
import com.example.OrdforandeLista.entities.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("");

        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Tag name is required")));
    }

    @Test
    void shouldFailWhenNameIsTooLong() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("A".repeat(101));

        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Tag name cannot exceed 100 characters")));
    }

    @Test
    void shouldPassWithValidData() {
        TagDTO tagDTO = TagDTO.builder()
                .tagId(1L)
                .name("Technology")
                .build();

        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);

        assertTrue(violations.isEmpty());
        assertEquals(1L, tagDTO.getTagId());
        assertEquals("Technology", tagDTO.getName());
    }

    @Test
    void shouldConvertToEntity() {
        TagDTO dto = TagDTO.builder()
                .tagId(5L)
                .name("AI")
                .build();

        Tag entity = dto.toEntity();

        assertNotNull(entity);
        assertEquals(5L, entity.getTagId());
        assertEquals("AI", entity.getName());
    }
}

