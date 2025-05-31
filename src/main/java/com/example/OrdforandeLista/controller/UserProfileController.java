package com.example.OrdforandeLista.controller;


import com.example.OrdforandeLista.repositories.TagRepository;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import com.example.OrdforandeLista.service.TagService;
import com.example.OrdforandeLista.service.UserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usersprofiles")
public class UserProfileController {

      public final UserProfileRepository userProfileRepository;
      public final UserProfileService userProfileService;
      public final TagService tagService;
      public final TagRepository tagRepository;

    public UserProfileController(UserProfileRepository userProfileRepository, UserProfileService userProfileService, TagService tagService, TagRepository tagRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileService = userProfileService;
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }






}
