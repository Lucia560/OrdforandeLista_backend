package com.example.OrdforandeLista.service;

import com.example.OrdforandeLista.dto.AdminUserDTO;
import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.input.AdminUserInput;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    // Convert Entity to DTO
    private AdminUserDTO convertToDto(AdminUser adminUser) {
        return AdminUserDTO.builder()
                .id(adminUser.getId())
                .email(adminUser.getEmail())
                .role(adminUser.getRole())
                .createdAt(adminUser.getCreatedAt())
                .updatedAt(adminUser.getUpdatedAt())
                .accountNonExpired(adminUser.isAccountNonExpired())
                .accountNonLocked(adminUser.isAccountNonLocked())
                .credentialsNonExpired(adminUser.isCredentialsNonExpired())
                .enabled(adminUser.isEnabled())
                .build();
    }

    // Convert DTO to Entity
    private AdminUser convertToEntity(AdminUserDTO dto) {
        return AdminUser.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public List<AdminUserDTO> getAllAdminUsers() {
        return adminUserRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AdminUserDTO getAdminUserById(Long id) {
        return adminUserRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Admin user not found with id: " + id));
    }

    public void deleteAdminUser(Long id) {
        if (!adminUserRepository.existsById(id)) {
            throw new IllegalArgumentException("Admin with ID " + id + " does not exist");
        }
        adminUserRepository.deleteById(id);
    }

    public AdminUserDTO createAdminUser(AdminUserInput input) {
        AdminUser adminUser = AdminUser.builder()
                .email(input.email())
                .password(passwordEncoder.encode(input.password())) // Encode password
                .role(input.role() != null ? input.role() : "ADMIN")
                .build();
        
        AdminUser savedUser = adminUserRepository.save(adminUser);
        return convertToDto(savedUser);
    }

    public AdminUserDTO updateAdminUser(Long id, AdminUserInput input) {
        return adminUserRepository.findById(id).map(adminUser -> {
            if (input.email() != null) {
                adminUser.setEmail(input.email());
            }
            if (input.password() != null) {
                adminUser.setPassword(passwordEncoder.encode(input.password())); // Encode password
            }
            if (input.role() != null) {
                adminUser.setRole(input.role());
            }
            AdminUser updatedUser = adminUserRepository.save(adminUser);
            return convertToDto(updatedUser);
        }).orElseThrow(() -> new RuntimeException("Admin user not found with id: " + id));
    }
}

