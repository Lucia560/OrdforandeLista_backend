package com.example.OrdforandeLista.repositories;
import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setup() {
        userProfileRepository.deleteAll();
        tagRepository.deleteAll();
    }

    private UserProfile createUser(String first, String last, String email, Set<Tag> tags) {
        return UserProfile.builder()
                .firstName(first)
                .lastName(last)
                .email(email)
                .linkedInUrl("https://linkedin.com/in/" + first.toLowerCase())
                .profilePitch("Experienced leader.")
                .hasBoardEducation(true)
                .agreedToTerms(true)
                .hasLeadershipEducation(true)
                .keyCompetencies(tags)
                .build();
    }

    @Test
    void shouldFindByFirstName() {
        UserProfile user = createUser("Anna", "Karlsson", "anna@test.com", Set.of());
        userProfileRepository.save(user);

        var found = userProfileRepository.findByFirstName("Anna");

        assertTrue(found.isPresent());
        assertEquals("Anna", found.get().getFirstName());
    }

    @Test
    void shouldFindByLastName() {
        UserProfile user = createUser("Lisa", "Svensson", "lisa@test.com", Set.of());
        userProfileRepository.save(user);

        var found = userProfileRepository.findByLastName("Svensson");

        assertTrue(found.isPresent());
        assertEquals("Svensson", found.get().getLastName());
    }

    @Test
    void existsByEmail_ShouldReturnTrue() {
        UserProfile user = createUser("John", "Doe", "john@test.com", Set.of());
        userProfileRepository.save(user);

        assertTrue(userProfileRepository.existsByEmail("john@test.com"));
    }

    @Test
    void existsByEmail_ShouldReturnFalse() {
        assertFalse(userProfileRepository.existsByEmail("missing@test.com"));
    }

    @Test
    void shouldFindUsersByTagName() {
        Tag finance = tagRepository.save(Tag.builder().name("Finance").build());
        Tag tech = tagRepository.save(Tag.builder().name("Tech").build());

        UserProfile u1 = createUser("Sara", "Lund", "sara@test.com", Set.of(finance));
        UserProfile u2 = createUser("Mia", "Berg", "mia@test.com", Set.of(finance, tech));

        userProfileRepository.save(u1);
        userProfileRepository.save(u2);

        List<UserProfile> financeUsers =
                userProfileRepository.findAllByKeyCompetencies_Name("Finance");

        assertEquals(2, financeUsers.size());
    }

    @Test
    void findAll_ShouldReturnAllProfiles() {
        userProfileRepository.save(createUser("A", "A", "a@test.com", Set.of()));
        userProfileRepository.save(createUser("B", "B", "b@test.com", Set.of()));

        List<UserProfile> users = userProfileRepository.findAll();

        assertEquals(2, users.size());
    }
}

