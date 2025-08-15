package com.example.OrdforandeLista.service;


import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.input.AdminUserInput;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser createAdminUser(AdminUserInput input) {
        AdminUser adminUser = AdminUser
                .builder()
                .email(input.email())
                .password(input.password())
                .role("Admin")
                .build();
        return adminUserRepository.save(adminUser);
    }

    public void deleteAdminUser(Long Id) {
        if (!adminUserRepository.existsById(Id)) {
            throw new IllegalArgumentException("Admin with ID " + Id + " does not exist");
        }
        adminUserRepository.deleteById(Id);
    }

    public AdminUser updateAdminUser(Long id, AdminUserInput input) {
        return adminUserRepository.findById(id).map(adminUser -> {
            adminUser.setEmail(input.email());
            adminUser.setPassword(input.password());
            return adminUserRepository.save(adminUser);
        }).orElseThrow(() -> new RuntimeException("Admin user not found with id " + id));
    }



}
