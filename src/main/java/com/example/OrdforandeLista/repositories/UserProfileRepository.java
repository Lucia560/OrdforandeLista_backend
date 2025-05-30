package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.UserProfile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends ListCrudRepository<UserProfile,Long> {

    Optional<UserProfile> findByFirstName(String firstName);
    Optional<UserProfile> findByLastName(String lastName);
    Optional<UserProfile> findAllByTag(String tag_name);

    boolean existsByEmail(String email);
}
