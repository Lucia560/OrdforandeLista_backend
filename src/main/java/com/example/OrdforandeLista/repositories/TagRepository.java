package com.example.OrdforandeLista.repositories;

import com.example.OrdforandeLista.entities.Tag;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TagRepository extends ListCrudRepository<Tag, Long> {

    List<Tag> findAllByName(String name);
    boolean existsByName(String name);


}

