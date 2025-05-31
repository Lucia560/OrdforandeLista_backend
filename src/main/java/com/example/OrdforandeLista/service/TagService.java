package com.example.OrdforandeLista.service;


import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TagService {

    public final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag) {
        if (tagRepository.existsByTagName(tag.getTagName())) {
            throw new IllegalArgumentException("Tag with name '" + tag.getTagName() + "' already exists");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
        ;
    }

    public Tag updateTag(Long id, Tag tag) {
        return tagRepository.findById(id).map(dbTag -> {
            dbTag.setTagName(tag.getTagName());
            return tagRepository.save(dbTag);
        }).orElseThrow(()-> new IllegalArgumentException("Kunde inte hitta taggen"));

    }
}
