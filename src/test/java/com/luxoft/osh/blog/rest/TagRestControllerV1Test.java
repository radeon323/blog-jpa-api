package com.luxoft.osh.blog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.entity.Tag;
import com.luxoft.osh.blog.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Oleksandr Shevchenko
 */
@WebMvcTest(TagRestControllerV1.class)
class TagRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    @Test
    void testFindAllTags() throws Exception {

        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Tag1")
                .posts(List.of(new Post()))
                .build();

        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Tag2")
                .posts(List.of(new Post()))
                .build();

        List<Tag> tags = List.of(tag1, tag2);

        when(tagService.findAll()).thenReturn(tags);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/tags")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Tag1"))
                .andExpect(jsonPath("$[1].name").value("Tag2"))
                .andExpect(jsonPath("$[1].star").doesNotExist());
        verify(tagService, times(1)).findAll();
    }

    @Test
    void testGetAllTagsIfTagsIsEmpty() throws Exception {
        List<Tag> tags = new ArrayList<>();

        when(tagService.findAll()).thenReturn(tags);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/tags")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(tagService, times(1)).findAll();
    }

    @Test
    void testSaveTag() throws Exception {
        Tag tag = Tag.builder()
                .id(1L)
                .name("Tag1")
                .posts(new ArrayList<>())
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/posts/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tag1"));
        verify(tagService).save(any(Tag.class));
    }

    @Test
    void testSaveTagIfTagIsNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/posts/tags", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTagById() throws Exception {
        Tag tag = Tag.builder()
                .id(1L)
                .name("Tag1")
                .posts(new ArrayList<>())
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/api/v1/posts/tags/{tagId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tag)))
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(status().isNoContent());
        verify(tagService, times(1)).deleteById(1L);
    }

    @Test
    void testFindAllTagsByPostId() throws Exception {

        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Tag1")
                .posts(List.of(new Post()))
                .build();

        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Tag2")
                .posts(List.of(new Post()))
                .build();

        Set<Tag> tags = Set.of(tag1, tag2);

        when(tagService.findAllByPostId(1L)).thenReturn(tags);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{postId}/tags", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Tag1"))
                .andExpect(jsonPath("$[1].name").value("Tag2"))
                .andExpect(jsonPath("$[1].star").doesNotExist());
        verify(tagService, times(1)).findAllByPostId(1L);
    }

    @Test
    void testFindAllTagsByPostIdIfTagsIsEmpty() throws Exception {
        Set<Tag> tags = new HashSet<>();

        when(tagService.findAllByPostId(1L)).thenReturn(tags);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{postId}/tags", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(tagService, times(1)).findAllByPostId(1L);
    }


}