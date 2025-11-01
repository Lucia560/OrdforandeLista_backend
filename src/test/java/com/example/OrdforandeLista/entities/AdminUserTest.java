package com.example.OrdforandeLista.entities;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.*;

class AdminUserTest {

    @Test
    void shouldReturnCorrectAuthorities() {
        AdminUser user = AdminUser.builder()
                .email("admin@example.com")
                .password("password123")
                .role("ADMIN")
                .build();

        assertTrue(
                user.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        );
    }


    @Test
    void shouldReturnEmailAsUsername() {
        AdminUser user = AdminUser.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        assertEquals("test@example.com", user.getUsername());
    }

    @Test
    void accountFlagsShouldBeTrue() {
        AdminUser user = new AdminUser();

        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}

