package com.example.OrdforandeLista.service;


import com.example.OrdforandeLista.entities.UserProfile;
import com.example.OrdforandeLista.repositories.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserProfileService {

    public final UserProfileRepository userProfileRepository;


    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createUserProfile(UserProfile userProfile) {
        if (userProfileRepository.existsByEmail(userProfile.getEmail())) {
            throw new IllegalArgumentException(" Email finns redan i databasen");
        }
        return userProfileRepository.save(userProfile);
    }

    public void deleteUserProfile(Long id) {
       userProfileRepository.deleteById(id);
    }

    public UserProfile updateUserProfile(Long id,UserProfile userProfile) {
        return userProfileRepository.findById((id)).map(dbUserProfile -> {
            dbUserProfile.setFirstName(userProfile.getFirstName());
            dbUserProfile.setLastName(userProfile.getLastName());
            dbUserProfile.setEmail(userProfile.getEmail());
            dbUserProfile.setLinkedInUrl(userProfile.getLinkedInUrl());
            dbUserProfile.setProfilePictureUrl(userProfile.getProfilePictureUrl());
            dbUserProfile.setProfilePitch(userProfile.getProfilePitch());
            dbUserProfile.setHasBoardEducation(userProfile.getHasBoardEducation());
            dbUserProfile.setBoardExperienceYears(userProfile.getBoardExperienceYears());
            dbUserProfile.setExecutiveExperienceYears(userProfile.getExecutiveExperienceYears());
            dbUserProfile.setLeadershipPosition(userProfile.getLeadershipPosition());
            dbUserProfile.setBoardRoles(userProfile.getBoardRoles());
            dbUserProfile.setCompanyTypes(userProfile.getCompanyTypes());
            dbUserProfile.setKeyCompetencies(userProfile.getKeyCompetencies());
            dbUserProfile.setTag(userProfile.getTag());
            return userProfileRepository.save(dbUserProfile);

           }).orElseThrow(()->new IllegalArgumentException("Kunde inte hitta användaren"));
        }

}






