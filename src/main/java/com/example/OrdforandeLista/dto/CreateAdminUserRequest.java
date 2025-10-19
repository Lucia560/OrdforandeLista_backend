package com.example.OrdforandeLista.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAdminUserRequest {
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotBlank(message = "Email is required")
    private String email;
    
    // Add any other fields that might be needed for admin user creation
    // For example: private String role;
}
