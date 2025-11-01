package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AdminUserRepositoryTest {

    @Autowired
    private AdminUserRepository repository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void shouldSaveAndFindByEmail() {
        AdminUser user = AdminUser.builder()
                .email("test@example.com")
                .password(encoder.encode("password123"))
                .role("ADMIN")
                .build();

        repository.save(user);

        Optional<AdminUser> found = repository.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        assertTrue(repository.findByEmail("missing@example.com").isEmpty());
    }

    @Test
    void shouldReturnTrueIfEmailExists() {
        AdminUser user = AdminUser.builder()
                .email("exists@example.com")
                .password(encoder.encode("password123"))
                .role("ADMIN")
                .build();

        repository.save(user);

        assertTrue(repository.existsByEmail("exists@example.com"));
    }

    @Test
    void shouldReturnFalseIfEmailDoesNotExist() {
        assertFalse(repository.existsByEmail("nope@example.com"));
    }
}
