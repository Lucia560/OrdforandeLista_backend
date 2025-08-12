package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.List;

public class UserProfileQuery {

    private UserProfileRepository userProfileRepository;

    @QueryMapping
    public List<UserProfile> findAllUsers() {
        return userProfileRepository.findAll();
    }

    @QueryMapping
    public UserProfile findUserById(@Argument Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @QueryMapping
    public List<UserProfile> findUsersByTag(@Argument String tag) {
        return userProfileRepository.findAllByKeyCompetencies_TagName(tag);
    }

    @QueryMapping
    public UserProfile findUserByName(@Argument String name) {
        return userProfileRepository.findByFirstName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
