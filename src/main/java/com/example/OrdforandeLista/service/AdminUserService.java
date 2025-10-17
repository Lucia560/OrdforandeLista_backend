package com.example.OrdforandeLista.service;

import com.example.OrdforandeLista.dto.AdminUserDTO;
import com.example.OrdforandeLista.dto.CreateAdminUserRequest;
import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.mapper.AdminUserMapper;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminUserDTO createAdminUser(CreateAdminUserRequest request) {
        if (adminUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        AdminUser adminUser = adminUserMapper.toEntity(request);
        adminUser.setPassword(passwordEncoder.encode(request.getPassword()));
        adminUser.setRole("ADMIN");
        
        return adminUserMapper.toDto(adminUserRepository.save(adminUser));
    }

    public void deleteAdminUser(Long id) {
        AdminUser adminUser = adminUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin with ID " + id + " not found"));
        adminUserRepository.delete(adminUser);
    }

    public AdminUserDTO updateAdminUser(Long id, CreateAdminUserRequest request) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUserMapper.updateAdminUserFromDto(request, adminUser);
                    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                        adminUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    }
                    return adminUserMapper.toDto(adminUserRepository.save(adminUser));
                })
                .orElseThrow(() -> new IllegalArgumentException("Admin user not found with id " + id));
    }

    public List<AdminUserDTO> getAllAdminUsers() {
        return adminUserRepository.findAll().stream()
                .map(adminUserMapper::toDto)
                .collect(Collectors.toList());
    }

    public AdminUserDTO getAdminUserById(Long id) {
        return adminUserRepository.findById(id)
                .map(adminUserMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Admin user not found with id " + id));
    }



}
