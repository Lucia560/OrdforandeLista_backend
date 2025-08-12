package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.Tag;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TagRepository extends ListCrudRepository<Tag, Long> {

    List<Tag> findAllByTagName(String tagName);
    boolean existsByTagName(String tagName);


}

