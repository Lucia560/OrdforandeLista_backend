package com.example.OrdforandeLista.mapper;

import com.example.OrdforandeLista.dto.TagDTO;
import com.example.OrdforandeLista.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class TagMapper {


    public TagDTO toDto(Tag entity) {
        if (entity == null) {
            return null;
        }
        return TagDTO.builder()
                .tagId(entity.getTagId())
                .name(entity.getName())
                .build();
    }

    public Tag toEntity(TagDTO dto) {
        if (dto == null) {
            return null;
        }
        return Tag.builder()
                .tagId(dto.getTagId())
                .name(dto.getName())
                .build();
    }


    public Set<TagDTO> toDtoSet(Set<Tag> entities) {
        if (entities == null) {
            return Set.of();
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }


    public Set<Tag> toEntitySet(Set<TagDTO> dtos) {
        if (dtos == null) {
            return Set.of();
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }


    public Tag updateEntityFromDto(Tag entity, TagDTO dto) {
        if (entity == null || dto == null) {
            throw new IllegalArgumentException("Neither entity nor dto can be null");
        }
        
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        
        return entity;
    }
}
