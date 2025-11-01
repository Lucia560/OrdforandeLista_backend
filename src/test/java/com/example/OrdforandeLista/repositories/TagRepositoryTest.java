package com.example.OrdforandeLista.repositories;
import com.example.OrdforandeLista.entities.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setup() {
        tagRepository.deleteAll(); // clean H2 before each test
    }

    @Test
    void shouldSaveAndFindById() {
        Tag tag = Tag.builder().name("Leadership").build();
        Tag saved = tagRepository.save(tag);

        assertNotNull(saved.getTagId());
        assertTrue(tagRepository.findById(saved.getTagId()).isPresent());
    }

    @Test
    void shouldFindAllByName() {
        Tag tag1 = Tag.builder().name("Finance").build();
        Tag tag2 = Tag.builder().name("Finance").build();
        Tag tag3 = Tag.builder().name("Tech").build();

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

        List<Tag> financeTags = tagRepository.findAllByName("Finance");

        assertEquals(2, financeTags.size());
    }

    @Test
    void shouldReturnTrueIfNameExists() {
        tagRepository.save(Tag.builder().name("HR").build());

        assertTrue(tagRepository.existsByName("HR"));
    }

    @Test
    void shouldReturnFalseIfNameDoesNotExist() {
        assertFalse(tagRepository.existsByName("NonExistingTag"));
    }
}

