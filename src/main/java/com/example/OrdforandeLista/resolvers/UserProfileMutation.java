package com.example.OrdforandeLista.resolvers;

import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.input.RegisterUserProfileInput;
import com.example.OrdforandeLista.service.TagService;
import com.example.OrdforandeLista.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMutation {

    private final UserProfileService userProfileService;
    private final TagService tagService;

    @Autowired
    public UserProfileMutation(UserProfileService userProfileService, TagService tagservice) {
        this.userProfileService = userProfileService;
        this.tagService = tagservice;
    }

    @MutationMapping
    public UserProfile createUser(@Argument RegisterUserProfileInput input) {
        return userProfileService.registerUser(input);
    }

    @MutationMapping
    public UserProfile updateUser(@Argument Long id, @Argument RegisterUserProfileInput input) {
        return userProfileService.updateUser(id, input);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        userProfileService.deleteUser(id);
        return true;
    }
}
