/*package com.example.OrdforandeLista.service;
import com.example.OrdforandeLista.entities.AdminUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = {
                "spring.jpa.hibernate.ddl-auto=none",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
        }
)
@Import(JWTService.class)
class JWTServiceTest {

    @Autowired
    private JWTService jwtService;



    @Test
    void testGenerateAndValidateToken_ForAdminUser() {
        AdminUser admin = AdminUser.builder()
                .id(1L)
                .email("admin@test.com")
                .password("password")
                .role("ADMIN")
                .build();

        String token = jwtService.generateToken(admin);

        assertNotNull(token);
        assertEquals("admin@test.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, admin));
    }

    @Test
    void testGenerateAndValidateToken_ForSpringUserDetails() {
        User user = new User(
                "user@test.com",
                "pass",
                List.of(() -> "ROLE_USER")
        );

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertEquals("user@test.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void testExpiredTokenReturnsFalse() throws InterruptedException {
        AdminUser admin = AdminUser.builder()
                .id(1L)
                .email("expired@test.com")
                .password("pass")
                .role("ADMIN")
                .build();

        // create token that expires immediately
        String token = jwtService.generateToken(admin);

        // Manually simulate expiration (fast tests don't wait a day)
        Thread.sleep(5);
        jwtService.extractExpiration(token).setTime(System.currentTimeMillis() - 1000);

        assertFalse(jwtService.isTokenValid(token, admin));
    }

    @Test
    void testExtractUsername() {
        User user = new User(
                "hello@test.com",
                "pass",
                List.of(() -> "ROLE_USER")
        );

        String token = jwtService.generateToken(user);

        assertEquals("hello@test.com", jwtService.extractUsername(token));
    }
}*/

