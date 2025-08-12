package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.UserProfile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends ListCrudRepository<UserProfile,Long> {

    Optional<UserProfile> findByFirstName(String firstName);
    Optional<UserProfile> findByLastName(String lastName);
    List<UserProfile> findAllByKeyCompetencies_Name(String name);
    boolean existsByEmail(String email);
}
