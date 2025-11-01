/*package com.example.OrdforandeLista.config;

import com.example.OrdforandeLista.service.AuthFilterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({SecurityConfig.class, SecurityConfigTest.TestBeans.class})
class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @TestConfiguration
    static class TestBeans {
        @Bean
        AuthFilterService authFilterService() {
            return Mockito.mock(AuthFilterService.class);
        }
    }

    @Test
    void publicAuthEndpoints() throws Exception {
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void secureEndpointUnauthorized() throws Exception {
        mockMvc.perform(get("/protected"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void secureEndpointAuthorized() throws Exception {
        mockMvc.perform(get("/protected"))
                .andExpect(status().isNotFound());
    }
}*/
