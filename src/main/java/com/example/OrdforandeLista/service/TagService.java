package com.example.OrdforandeLista.service;

import com.example.OrdforandeLista.dto.TagDTO;
import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.mapper.TagMapper;
import com.example.OrdforandeLista.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    // ✅ CREATE
    public TagDTO createTag(TagDTO tagDto) {
        if (tagRepository.existsByName(tagDto.getName())) {
            throw new IllegalArgumentException("Tag with name '" + tagDto.getName() + "' already exists");
        }

        Tag tag = tagMapper.toEntity(tagDto);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toDto(savedTag);
    }

    // ✅ DELETE
    public void deleteTag(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new IllegalArgumentException("Tag with ID '" + tagId + "' does not exist");
        }
        tagRepository.deleteById(tagId);
    }

    // ✅ UPDATE
    public TagDTO updateTag(Long tagId, TagDTO tagDto) {
        return tagRepository.findById(tagId).map(existingTag -> {
            Tag updatedTag = tagMapper.updateEntityFromDto(existingTag, tagDto);
            return tagMapper.toDto(tagRepository.save(updatedTag));
        }).orElseThrow(() -> new IllegalArgumentException("Could not find tag with ID: " + tagId));
    }

    // ✅ GET BY IDS
    public List<TagDTO> getTagsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        return tagRepository.findAllById(ids)
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }
}
