/*package com.example.OrdforandeLista.resolvers;
import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

import static org.mockito.Mockito.*;

@GraphQlTest(AdminUserQuery.class)
class AdminUserQueryTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AdminUserRepository adminUserRepository;

    @Test
    void findAllAdminUsers_ShouldReturnList() {

        AdminUser user = AdminUser.builder()
                .id(1L)
                .email("admin@test.com")
                .password("dummy") // won't return anyway
                .role("ADMIN")
                .build();

        when(adminUserRepository.findAll()).thenReturn(List.of(user));

        String query = """
        query {
          findAllAdminUsers {
            id
            email
            role
          }
        }
        """;

        graphQlTester.document(query)
                .execute()
                .path("findAllAdminUsers[0].id").entity(Long.class).isEqualTo(1L)
                .path("findAllAdminUsers[0].email").entity(String.class).isEqualTo("admin@test.com")
                .path("findAllAdminUsers[0].role").entity(String.class).isEqualTo("ADMIN");

        verify(adminUserRepository, times(1)).findAll();
    }
}*/

