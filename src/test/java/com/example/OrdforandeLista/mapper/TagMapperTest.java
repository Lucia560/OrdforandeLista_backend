package com.example.OrdforandeLista.mapper;
import com.example.OrdforandeLista.dto.TagDTO;
import com.example.OrdforandeLista.entities.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagMapperTest {

    private TagMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TagMapper();
    }

    @Test
    void shouldMapEntityToDto() {
        Tag tag = Tag.builder()
                .tagId(1L)
                .name("Leadership")
                .build();

        TagDTO dto = mapper.toDto(tag);

        assertNotNull(dto);
        assertEquals(1L, dto.getTagId());
        assertEquals("Leadership", dto.getName());
    }

    @Test
    void shouldReturnNullWhenMappingNullEntityToDto() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void shouldMapDtoToEntity() {
        TagDTO dto = TagDTO.builder()
                .tagId(2L)
                .name("Finance")
                .build();

        Tag tag = mapper.toEntity(dto);

        assertNotNull(tag);
        assertEquals(2L, tag.getTagId());
        assertEquals("Finance", tag.getName());
    }

    @Test
    void shouldReturnNullWhenMappingNullDtoToEntity() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void shouldMapEntitySetToDtoSet() {
        Set<Tag> entities = Set.of(
                Tag.builder().tagId(1L).name("Tech").build(),
                Tag.builder().tagId(2L).name("Legal").build()
        );

        Set<TagDTO> dtos = mapper.toDtoSet(entities);

        assertEquals(2, dtos.size());
        assertTrue(dtos.stream().anyMatch(d -> d.getName().equals("Tech")));
        assertTrue(dtos.stream().anyMatch(d -> d.getName().equals("Legal")));
    }

    @Test
    void shouldReturnEmptySetWhenEntitySetIsNull() {
        Set<TagDTO> dtos = mapper.toDtoSet(null);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void shouldMapDtoSetToEntitySet() {
        Set<TagDTO> dtos = Set.of(
                TagDTO.builder().tagId(1L).name("Growth").build(),
                TagDTO.builder().tagId(2L).name("Strategy").build()
        );

        Set<Tag> entities = mapper.toEntitySet(dtos);

        assertEquals(2, entities.size());
        assertTrue(entities.stream().anyMatch(d -> d.getName().equals("Growth")));
        assertTrue(entities.stream().anyMatch(d -> d.getName().equals("Strategy")));
    }

    @Test
    void shouldReturnEmptySetWhenDtoSetIsNull() {
        Set<Tag> entities = mapper.toEntitySet(null);
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }

    @Test
    void shouldUpdateEntityFromDto() {
        Tag entity = Tag.builder().tagId(10L).name("OldName").build();
        TagDTO dto = TagDTO.builder().name("NewName").build();

        mapper.updateEntityFromDto(entity, dto);

        assertEquals("NewName", entity.getName());
        assertEquals(10L, entity.getTagId()); // id must remain unchanged
    }

    @Test
    void shouldNotUpdateNameIfNullInDto() {
        Tag entity = Tag.builder().tagId(5L).name("Original").build();
        TagDTO dto = TagDTO.builder().name(null).build();

        mapper.updateEntityFromDto(entity, dto);

        assertEquals("Original", entity.getName()); // unchanged
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNullArguments() {
        Tag entity = Tag.builder().tagId(1L).name("Tag").build();

        assertThrows(IllegalArgumentException.class, () -> mapper.updateEntityFromDto(null, new TagDTO()));
        assertThrows(IllegalArgumentException.class, () -> mapper.updateEntityFromDto(entity, null));
    }
}

