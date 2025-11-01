package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.entities.RefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void shouldSaveAndFindByRefreshToken() {
        AdminUser user = AdminUser.builder()
                .email("admin@test.com")
                .password(encoder.encode("password123"))
                .role("ADMIN")
                .build();
        adminUserRepository.save(user);

        RefreshToken token = RefreshToken.builder()
                .refreshToken("test-token-123")
                .expirationTime(Instant.now().plusSeconds(3600))
                .adminUser(user)
                .build();
        refreshTokenRepository.save(token);

        Optional<RefreshToken> found = refreshTokenRepository.findByRefreshToken("test-token-123");

        assertTrue(found.isPresent());
        assertEquals("test-token-123", found.get().getRefreshToken());
        assertEquals(user.getId(), found.get().getAdminUser().getId());
    }

    @Test
    void shouldReturnEmptyWhenTokenNotFound() {
        Optional<RefreshToken> found = refreshTokenRepository.findByRefreshToken("missing-token");
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindByAdminUserId() {
        AdminUser user = AdminUser.builder()
                .email("other@test.com")
                .password(encoder.encode("password123"))
                .role("ADMIN")
                .build();
        adminUserRepository.save(user);

        RefreshToken token = RefreshToken.builder()
                .refreshToken("xyz")
                .expirationTime(Instant.now().plusSeconds(3600))
                .adminUser(user)
                .build();
        refreshTokenRepository.save(token);

        Optional<RefreshToken> found = refreshTokenRepository.findByAdminUserId(user.getId());

        assertTrue(found.isPresent());
        assertEquals("xyz", found.get().getRefreshToken());
    }

    @Test
    void shouldReturnEmptyWhenAdminHasNoToken() {
        AdminUser user = AdminUser.builder()
                .email("no.token@test.com")
                .password(encoder.encode("password123"))
                .role("ADMIN")
                .build();
        adminUserRepository.save(user);

        Optional<RefreshToken> found = refreshTokenRepository.findByAdminUserId(user.getId());

        assertTrue(found.isEmpty());
    }
}
