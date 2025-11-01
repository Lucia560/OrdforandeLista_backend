package com.example.OrdforandeLista.config;

import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class ApplicationConfigTest {

        private AdminUserRepository adminUserRepository;
        private ApplicationConfig applicationConfig;

        @BeforeEach
        void setUp() {
            adminUserRepository = Mockito.mock(AdminUserRepository.class);
            applicationConfig = new ApplicationConfig(adminUserRepository);
        }

        @Test
        void testUserDetailsService_UserExists() {

            String email = "test@example.com";
            AdminUser mockUser = new AdminUser();
            mockUser.setEmail(email);

            when(adminUserRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));


            UserDetails userDetails = applicationConfig.userDetailsService().loadUserByUsername(email);


            assertNotNull(userDetails);
            assertEquals(email, userDetails.getUsername());
            verify(adminUserRepository, times(1)).findByEmail(email);
        }

        @Test
        void testUserDetailsService_UserNotFound() {
            // Given
            String email = "notfound@example.com";
            when(adminUserRepository.findByEmail(email)).thenReturn(Optional.empty());

            // When / Then
            assertThrows(UsernameNotFoundException.class, () ->
                    applicationConfig.userDetailsService().loadUserByUsername(email));

            verify(adminUserRepository, times(1)).findByEmail(email);
        }
    }


