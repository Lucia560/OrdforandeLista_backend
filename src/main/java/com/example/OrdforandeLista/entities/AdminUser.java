package com.example.OrdforandeLista.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;



@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_user")
@Builder
public class AdminUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Denna fält kan inte vara tomt")
    @Email(message = "Skriv in en giltig e-post")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Denna fält kan inte vara tomt")
    @Size(min = 8, message = "Lösenordet ska vara minst 8 tecken")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "ADMIN";

    @OneToOne(mappedBy = "adminUser", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    // Spring Security methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ADMIN");
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername()
    { return email;
    }

    //inkapsulering, is final corect?
    private final boolean isAccountNonExpired = true;
    private final  boolean isAccountNonLocked = true;
    private final  boolean isCredentialsNonExpired = true;
    private final  boolean isEnabled = true;



    @Override public boolean isAccountNonExpired() { return isAccountNonExpired; }
    @Override public boolean isAccountNonLocked() { return isAccountNonLocked; }
    @Override public boolean isCredentialsNonExpired() { return isCredentialsNonExpired; }
    @Override public boolean isEnabled() { return isEnabled; }

}
