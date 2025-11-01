package com.example.OrdforandeLista.service;

import com.example.OrdforandeLista.dto.TagDTO;
import com.example.OrdforandeLista.entities.Tag;
import com.example.OrdforandeLista.mapper.TagMapper;
import com.example.OrdforandeLista.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {

    private TagRepository tagRepository;
    private TagMapper tagMapper;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagRepository = mock(TagRepository.class);
        tagMapper = mock(TagMapper.class);
        tagService = new TagService(tagRepository, tagMapper);
    }

    // ✅ CREATE TAG
    @Test
    void createTag_ShouldSaveAndReturnDto() {
        TagDTO inputDto = new TagDTO(1L, "Tech");
        Tag entity = new Tag(1L, "Tech");
        Tag savedEntity = new Tag(1L, "Tech");
        TagDTO outputDto = new TagDTO(1L, "Tech");

        when(tagRepository.existsByName("Tech")).thenReturn(false);
        when(tagMapper.toEntity(inputDto)).thenReturn(entity);
        when(tagRepository.save(entity)).thenReturn(savedEntity);
        when(tagMapper.toDto(savedEntity)).thenReturn(outputDto);

        TagDTO result = tagService.createTag(inputDto);

        assertNotNull(result);
        assertEquals("Tech", result.getName());
        verify(tagRepository).existsByName("Tech");
        verify(tagRepository).save(entity);
    }

    @Test
    void createTag_ShouldThrowIfNameExists() {
        TagDTO dto = new TagDTO(1L, "Tech");
        when(tagRepository.existsByName("Tech")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> tagService.createTag(dto));
        verify(tagRepository).existsByName("Tech");
        verify(tagRepository, never()).save(any());
    }

    // ✅ DELETE TAG
    @Test
    void deleteTag_ShouldRemoveTag() {
        when(tagRepository.existsById(1L)).thenReturn(true);

        tagService.deleteTag(1L);

        verify(tagRepository).deleteById(1L);
    }

    @Test
    void deleteTag_ShouldThrowWhenNotExists() {
        when(tagRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> tagService.deleteTag(1L));
        verify(tagRepository, never()).deleteById(1L);
    }

    // ✅ UPDATE TAG
    @Test
    void updateTag_ShouldUpdateAndReturnDto() {
        TagDTO dto = new TagDTO(1L, "Updated");
        Tag entity = new Tag(1L, "Old");
        Tag updatedEntity = new Tag(1L, "Updated");
        TagDTO resultDto = new TagDTO(1L, "Updated");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(tagMapper.updateEntityFromDto(entity, dto)).thenReturn(updatedEntity);
        when(tagRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(tagMapper.toDto(updatedEntity)).thenReturn(resultDto);

        TagDTO result = tagService.updateTag(1L, dto);

        assertEquals("Updated", result.getName());
        verify(tagRepository).findById(1L);
        verify(tagRepository).save(updatedEntity);
    }

    @Test
    void updateTag_ShouldThrowWhenNotFound() {
        TagDTO dto = new TagDTO(1L, "Updated");
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> tagService.updateTag(1L, dto));
    }

    // ✅ GET BY IDS
    @Test
    void getTagsByIds_ShouldReturnMappedList() {
        Tag tag = new Tag(1L, "Java");
        TagDTO dto = new TagDTO(1L, "Java");

        when(tagRepository.findAllById(List.of(1L))).thenReturn(List.of(tag));
        when(tagMapper.toDto(tag)).thenReturn(dto);

        List<TagDTO> result = tagService.getTagsByIds(List.of(1L));

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
    }

    @Test
    void getTagsByIds_ShouldReturnEmptyList_ForEmptyParam() {
        List<TagDTO> result = tagService.getTagsByIds(null);
        assertTrue(result.isEmpty());

        result = tagService.getTagsByIds(List.of());
        assertTrue(result.isEmpty());
    }
}
