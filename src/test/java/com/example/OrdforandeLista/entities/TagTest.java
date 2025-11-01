package com.example.OrdforandeLista.entities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void shouldBuildTagCorrectly() {
        Tag tag = Tag.builder()
                .tagId(1L)
                .name("Leadership")
                .build();

        assertEquals(1L, tag.getTagId());
        assertEquals("Leadership", tag.getName());
    }

    @Test
    void shouldAllowSettingFields() {
        Tag tag = Tag.builder().build();

        tag.setTagId(2L);
        tag.setName("Finance");

        assertEquals(2L, tag.getTagId());
        assertEquals("Finance", tag.getName());
    }

    @Test
    void defaultIdShouldBeNullBeforePersist() {
        Tag tag = Tag.builder()
                .name("Strategy")
                .build();

        assertNull(tag.getTagId());
    }

    @Test
    void shouldNotBreakWhenNameIsNull() {
        Tag tag = Tag.builder().build();
        tag.setName(null);
        assertNull(tag.getName());
    }
}

