package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.input.AdminUserInput;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import com.example.OrdforandeLista.service.AdminUserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


@Controller
public class AdminUserMutation {

    private final AdminUserService adminUserService;
    private final AdminUserRepository adminUserRepository;

    public AdminUserMutation(AdminUserService adminUserService, AdminUserRepository adminUserRepository) {
        this.adminUserService = adminUserService;
        this.adminUserRepository = adminUserRepository;
    }
    @MutationMapping
    public AdminUser createAdminUser(@Argument AdminUserInput input) {
        return adminUserService.createAdminUser(input);
    }


//    @MutationMapping
//    public AdminUser createAdminUser(@Argument AdminUserInput input) {
//        AdminUser admin = AdminUser.builder()
//                .email(input.email())
//                .password(input.password()) // add encoder!!!
//                .role("ADMIN")
//                .build();
//
//        return adminUserRepository.save(admin);  // ← Make sure this returns the saved object!
//    }

    @MutationMapping
    public AdminUser updateAdminUser(@Argument Long id, @Argument AdminUserInput input) {
        return adminUserService.updateAdminUser(id,input);
    }

    @MutationMapping
    public Boolean deleteAdminUser(@Argument Long id) {
        adminUserService.deleteAdminUser(id);
        return true;
    }
}
