/*package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.dto.AdminUserDTO;
import com.example.OrdforandeLista.input.AdminUserInput;
import com.example.OrdforandeLista.service.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@GraphQlTest(AdminUserMutation.class)
@AutoConfigureGraphQlTester
@Import(AdminUserMutation.class)
class AdminUserMutationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AdminUserService adminUserService;

    @Test
    void createAdminUser_ShouldReturnUser() {
        AdminUserDTO mockUser = AdminUserDTO.builder()
                .id(1L)
                .email("admin@test.com")
                .role("ADMIN")
                .build();

        when(adminUserService.createAdminUser(any())).thenReturn(mockUser);

        String mutation = """
        mutation {
          createAdminUser(input: {
            email: "admin@test.com",
            password: "strongpass123",
            role: "ADMIN"
          }) {
            id
            email
            role
          }
        }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("createAdminUser.id").entity(Long.class).isEqualTo(1L)
                .path("createAdminUser.email").entity(String.class).isEqualTo("admin@test.com")
                .path("createAdminUser.role").entity(String.class).isEqualTo("ADMIN");

        verify(adminUserService).createAdminUser(any(AdminUserInput.class));
    }

    @Test
    void updateAdminUser_ShouldReturnUpdatedUser() {
        AdminUserDTO updated = AdminUserDTO.builder()
                .id(1L)
                .email("updated@test.com")
                .role("ADMIN")
                .build();

        when(adminUserService.updateAdminUser(eq(1L), any())).thenReturn(updated);

        String mutation = """
        mutation {
          updateAdminUser(id: 1, input: {
            email: "updated@test.com",
            password: "newpass123",
            role: "ADMIN"
          }) {
            id
            email
            role
          }
        }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("updateAdminUser.email").entity(String.class).isEqualTo("updated@test.com");

        verify(adminUserService).updateAdminUser(eq(1L), any(AdminUserInput.class));
    }

    @Test
    void deleteAdminUser_ShouldReturnTrue() {
        doNothing().when(adminUserService).deleteAdminUser(1L);

        String mutation = """
        mutation {
          deleteAdminUser(id: 1)
        }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("deleteAdminUser").entity(Boolean.class).isEqualTo(true);

        verify(adminUserService).deleteAdminUser(1L);
    }
}*/
