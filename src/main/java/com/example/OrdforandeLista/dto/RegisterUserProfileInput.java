package com.example.OrdforandeLista.dto;

import java.util.Set;

public record RegisterUserProfileInput(
    String firstName,
    String lastName,
    String email,
    Set<Long> keyCompetencyIds
) {}
