package com.example.OrdforandeLista.service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthFilterServiceTest {

    private JWTService jwtService;
    private UserDetailsService userDetailsService;
    private AuthFilterService authFilter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtService = mock(JWTService.class);
        userDetailsService = mock(UserDetailsService.class);
        authFilter = new AuthFilterService(jwtService, userDetailsService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldNotAuthenticate_IfNoAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldAuthenticate_WhenValidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer VALID_TOKEN");
        when(jwtService.extractUsername("VALID_TOKEN")).thenReturn("admin@test.com");

        UserDetails user = new User(
                "admin@test.com",
                "pass",
                List.of(() -> "ROLE_ADMIN")
        );

        when(userDetailsService.loadUserByUsername("admin@test.com")).thenReturn(user);
        when(jwtService.isTokenValid("VALID_TOKEN", user)).thenReturn(true);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertEquals("admin@test.com", auth.getName());
        assertTrue(auth.isAuthenticated());
    }

    @Test
    void shouldNotAuthenticate_WhenInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer BAD_TOKEN");
        when(jwtService.extractUsername("BAD_TOKEN")).thenReturn("user@test.com");

        UserDetails user = new User("user@test.com", "pass", List.of());
        when(userDetailsService.loadUserByUsername("user@test.com")).thenReturn(user);
        when(jwtService.isTokenValid("BAD_TOKEN", user)).thenReturn(false);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}

