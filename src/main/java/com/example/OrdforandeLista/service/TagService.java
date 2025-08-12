package com.example.OrdforandeLista.service;


import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class TagService {

    public final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag) {
        if (tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag with ID '" + id + "' does not exist");
        }
        tagRepository.deleteById(id);
    }


    public Tag updateTag(Long id, Tag tag) {
        return tagRepository.findById(id).map(dbTag -> {
            dbTag.setName(tag.getName());
            return tagRepository.save(dbTag);
        }).orElseThrow(()-> new IllegalArgumentException("Kunde inte hitta taggen"));

    }

//    public List<Tag> getTagsByIds(List<Long> ids) {
//        return tagRepository.findAllById(ids);
//    }
public List<Tag> getTagsByIds(List<Long> ids) {
    if (ids == null || ids.isEmpty()) return List.of();
    return  tagRepository.findAllById(ids);
}

}
