package com.example.OrdforandeLista.service;
import com.example.OrdforandeLista.dto.AdminUserDTO;
import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.input.AdminUserInput;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminUserServiceTest {

    private AdminUserRepository adminUserRepository;
    private PasswordEncoder passwordEncoder;
    private AdminUserService adminUserService;

    @BeforeEach
    void setUp() {
        adminUserRepository = mock(AdminUserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        adminUserService = new AdminUserService(adminUserRepository, passwordEncoder);
    }

    @Test
    void createAdminUser_ShouldSaveAndReturnDTO() {
        AdminUserInput input = new AdminUserInput("test@test.com", "pass123", "ADMIN");

        when(passwordEncoder.encode("pass123")).thenReturn("ENCODED_PASS");

        AdminUser saved = AdminUser.builder()
                .id(1L)
                .email("test@test.com")
                .password("ENCODED_PASS")
                .role("ADMIN")
                .build();

        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(saved);

        AdminUserDTO result = adminUserService.createAdminUser(input);

        assertEquals(1L, result.getId());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("ADMIN", result.getRole());
        verify(adminUserRepository).save(any(AdminUser.class));
        verify(passwordEncoder).encode("pass123");
    }

    @Test
    void getAdminUserById_ShouldReturnDTO() {
        AdminUser user = AdminUser.builder()
                .id(2L)
                .email("admin@test.com")
                .role("ADMIN")
                .build();

        when(adminUserRepository.findById(2L)).thenReturn(Optional.of(user));

        AdminUserDTO dto = adminUserService.getAdminUserById(2L);

        assertEquals(2L, dto.getId());
        assertEquals("admin@test.com", dto.getEmail());
    }

    @Test
    void getAdminUserById_ShouldThrow_WhenNotFound() {
        when(adminUserRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adminUserService.getAdminUserById(99L));

        assertEquals("Admin user not found with id: 99", ex.getMessage());
    }

    @Test
    void getAllAdminUsers_ShouldReturnList() {
        AdminUser user = AdminUser.builder().id(1L).email("a@test.com").role("ADMIN").build();
        when(adminUserRepository.findAll()).thenReturn(List.of(user));

        List<AdminUserDTO> result = adminUserService.getAllAdminUsers();

        assertEquals(1, result.size());
        assertEquals("a@test.com", result.get(0).getEmail());
        verify(adminUserRepository).findAll();
    }

    @Test
    void deleteAdminUser_ShouldCallDelete() {
        when(adminUserRepository.existsById(3L)).thenReturn(true);

        adminUserService.deleteAdminUser(3L);

        verify(adminUserRepository).deleteById(3L);
    }

    @Test
    void deleteAdminUser_ShouldThrow_WhenNotExists() {
        when(adminUserRepository.existsById(10L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> adminUserService.deleteAdminUser(10L));

        verify(adminUserRepository, never()).deleteById(any());
    }

    @Test
    void updateAdminUser_ShouldUpdateFields() {
        AdminUser existing = AdminUser.builder()
                .id(5L)
                .email("old@test.com")
                .password("OLD_PASS")
                .role("ADMIN")
                .build();

        when(adminUserRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newpass")).thenReturn("ENC_NEW");
        when(adminUserRepository.save(any(AdminUser.class))).thenAnswer(i -> i.getArgument(0));

        AdminUserInput input = new AdminUserInput("new@test.com", "newpass", "SUPERADMIN");

        AdminUserDTO result = adminUserService.updateAdminUser(5L, input);

        assertEquals("new@test.com", result.getEmail());
        assertEquals("SUPERADMIN", result.getRole());
        verify(passwordEncoder).encode("newpass");
    }
}

