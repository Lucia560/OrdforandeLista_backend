package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.Tag;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends ListCrudRepository<Tag, Long> {

    Optional<Tag> findByTag(String tag_name);
}

