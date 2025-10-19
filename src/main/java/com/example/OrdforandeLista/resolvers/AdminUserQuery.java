package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.entities.AdminUser;
import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.repositories.AdminUserRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class AdminUserQuery {

    private final AdminUserRepository adminUserRepository;


    public AdminUserQuery(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @QueryMapping
    public List<AdminUser> findAllAdminUsers() {
        return adminUserRepository.findAll();
    }

}


