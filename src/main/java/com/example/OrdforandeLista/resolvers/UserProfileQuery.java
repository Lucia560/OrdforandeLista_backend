package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class UserProfileQuery {

    private final UserProfileRepository userProfileRepository;

    public UserProfileQuery(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;

    }

    @QueryMapping
    public List<UserProfile> findAllUsers() {
        return userProfileRepository.findAll();
    }

    @QueryMapping
    public UserProfile findUserById(@Argument Long userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @QueryMapping
    public List<UserProfile> findUsersByTag(@Argument String tag) {
        return userProfileRepository.findAllByKeyCompetencies_Name(tag);
    }

    @QueryMapping
    public UserProfile findUserByName(@Argument String name) {
        return userProfileRepository.findByFirstName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
