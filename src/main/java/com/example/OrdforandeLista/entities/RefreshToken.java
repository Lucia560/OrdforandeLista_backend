package com.example.OrdforandeLista.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Skriv in din nya refresh token")
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", referencedColumnName = "id", nullable = false, unique = true)
    @ToString.Exclude
    private AdminUser adminUser;
}
