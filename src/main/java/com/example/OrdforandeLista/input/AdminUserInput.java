package com.example.OrdforandeLista.input;

import jakarta.validation.constraints.NotBlank;

public record AdminUserInput(
        @NotBlank String email,
        @NotBlank String password,
        String role
) {
    public AdminUserInput(String email, String password) {
        this(email, password, null);
    }
}