package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.entities.UserProfile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagRepository extends ListCrudRepository<Tag, Long> {

    List<UserProfile> findAllByTag(Tag tag);

    boolean existsByName(String tagName);
}

