package com.example.OrdforandeLista.service;
import com.example.OrdforandeLista.dto.TagDTO;
import com.example.OrdforandeLista.dto.UserProfileDTO;
import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.input.RegisterUserProfileInput;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileServiceTest {

    private UserProfileRepository userRepo;
    private TagService tagService;
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserProfileRepository.class);
        tagService = mock(TagService.class);
        userProfileService = new UserProfileService(userRepo, tagService);
    }

    private RegisterUserProfileInput buildInput() {
        return new RegisterUserProfileInput(
                "Jane",
                "Doe",
                "jane@test.com",
                "123456789",
                "Company",
                "CEO",
                "https://linkedin.com",
                "Great leader",
                true,               // hasBoardEducation
                false,              // hasLeadershipEducation
                "Leadership course",
                List.of("Chairman"), // boardRoles
                List.of("Tech"),     // companyTypes
                List.of(1L, 2L),     // ✅ keyCompetencyIds — goes HERE
                5,                  // boardExperienceYears
                3,                  // executiveExperienceYears
                2,                  // leadershipExperienceYears
                true                // agreedToTerms — LAST
        );
    }




    @Test
    void deleteUser_ShouldDelete_WhenUserExists() {
        when(userRepo.existsById(1L)).thenReturn(true);

        userProfileService.deleteUser(1L);

        verify(userRepo).deleteById(1L);
    }


    @Test
    void deleteUser_ShouldThrow_WhenUserDoesNotExist() {
        when(userRepo.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> userProfileService.deleteUser(1L));
        verify(userRepo, never()).deleteById(any());
    }


    @Test
    void registerUser_ShouldSaveUserWithTags() {
        RegisterUserProfileInput input = buildInput();

        List<TagDTO> tagDTOs = List.of(
                new TagDTO(1L, "Leadership"),
                new TagDTO(2L, "Finance")
        );

        when(tagService.getTagsByIds(input.keyCompetencyIds())).thenReturn(tagDTOs);

        UserProfile savedUser = UserProfile.builder()
                .userId(10L)
                .firstName("Jane")
                .keyCompetencies(Set.of(
                        Tag.builder().tagId(1L).name("Leadership").build(),
                        Tag.builder().tagId(2L).name("Finance").build()
                ))
                .build();

        when(userRepo.save(any(UserProfile.class))).thenReturn(savedUser);

        UserProfileDTO result = userProfileService.registerUser(input);

        assertNotNull(result);
        assertEquals(10L, result.getUserId());
        assertEquals("Jane", result.getFirstName());
        assertEquals(2, result.getKeyCompetencies().size());

        verify(tagService).getTagsByIds(input.keyCompetencyIds());
        verify(userRepo).save(any(UserProfile.class));
    }


    @Test
    void updateUser_ShouldUpdateUserFields() {
        RegisterUserProfileInput input = buildInput();

        when(tagService.getTagsByIds(input.keyCompetencyIds()))
                .thenReturn(List.of(new TagDTO(1L, "Leadership")));

        UserProfile existingUser = UserProfile.builder()
                .userId(5L)
                .firstName("OldName")
                .build();

        when(userRepo.findById(5L)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(any(UserProfile.class))).thenAnswer(i -> i.getArgument(0));

        UserProfileDTO result = userProfileService.updateUser(5L, input);

        assertEquals("Jane", result.getFirstName());
        assertEquals(1, result.getKeyCompetencies().size());

        verify(tagService).getTagsByIds(input.keyCompetencyIds());
        verify(userRepo).save(existingUser);
    }


    @Test
    void updateUser_ShouldThrow_WhenUserNotFound() {
        RegisterUserProfileInput input = buildInput();

        when(userRepo.findById(100L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> userProfileService.updateUser(100L, input));

        verify(userRepo, never()).save(any());
    }
}

