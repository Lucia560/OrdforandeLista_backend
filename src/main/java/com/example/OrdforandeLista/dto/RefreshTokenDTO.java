package com.example.OrdforandeLista.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDTO {
    private Long id;

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    @NotNull(message = "Expiration time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Instant expirationTime;

    private Long adminUserId;

    public static RefreshTokenDTO fromEntity(com.example.OrdforandeLista.entities.RefreshToken entity) {
        if (entity == null) {
            return null;
        }
        return RefreshTokenDTO.builder()
                .id(entity.getId())
                .refreshToken(entity.getRefreshToken())
                .expirationTime(entity.getExpirationTime())
                .adminUserId(entity.getAdminUser() != null ? entity.getAdminUser().getId() : null)
                .build();
    }
}
