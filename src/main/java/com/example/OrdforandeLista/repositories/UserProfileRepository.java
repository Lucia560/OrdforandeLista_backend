package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserProfileRepository extends ListCrudRepository<UserProfile,Long> {

    Optional<UserProfile> findByFirstName(String firstName);
    Optional<UserProfile> findByLastName(String lastName);
    List<UserProfile> findAllByKeyCompetencies_TagName(String tagName);
    boolean existsByEmail(String email);
}
