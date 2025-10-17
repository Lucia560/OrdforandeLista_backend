package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.dto.AdminUserDTO;
import com.example.OrdforandeLista.dto.CreateAdminUserRequest;
import com.example.OrdforandeLista.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AdminUserMutation {

    private final AdminUserService adminUserService;

    @MutationMapping
    public AdminUserDTO createAdminUser(@Argument @Valid CreateAdminUserRequest input) {
        return adminUserService.createAdminUser(input);
    }

    @MutationMapping
    public AdminUserDTO updateAdminUser(
            @Argument Long id,
            @Argument @Valid CreateAdminUserRequest input) {
        return adminUserService.updateAdminUser(id, input);
    }

    @MutationMapping
    public Boolean deleteAdminUser(@Argument Long id) {
        adminUserService.deleteAdminUser(id);
        return true;
    }
}
