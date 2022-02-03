package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.entity.Tag;
import com.luxoft.osh.blog.repository.PostRepository;
import com.luxoft.osh.blog.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Oleksandr Shevchenko
 */
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    void testFindAll() {
        TagServiceImpl tagService = new TagServiceImpl(tagRepository);

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

        List<Tag> tags = List.of(tag1,tag2);

        Mockito.when(tagRepository.findAll()).thenReturn(tags);
        List<Tag> actualTags= tagService.findAll();
        assertNotNull(actualTags);
        assertEquals(2, actualTags.size());
        assertEquals("Tag1", actualTags.get(0).getName());
        assertEquals(tag2, actualTags.get(1));
    }

    @Test
    void testFindAllByPostId() {
        TagServiceImpl tagService = new TagServiceImpl(tagRepository);

        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Tag1")
                .posts(List.of(new Post(1L,"","",true,null,null)))
                .build();

        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Tag2")
                .posts(List.of(new Post(1L,"","",true,null,null)))
                .build();

        List<Tag> tags = List.of(tag1,tag2);

        Mockito.when(tagRepository.findAll()).thenReturn(tags);
        Set<Tag> actualTags = tagService.findAllByPostId(1L);
        assertNotNull(actualTags);
        assertEquals(2, actualTags.size());
        assertTrue(actualTags.contains(tag1));
        assertTrue(actualTags.contains(tag2));
    }

    @Test
    void testExistsByName() {
        TagServiceImpl tagService = new TagServiceImpl(tagRepository);
        Mockito.when(tagRepository.existsByName("Tag Name")).thenReturn(true);
        boolean isTag = tagService.existsByName("Tag Name");
        assertTrue(isTag);
    }

    @Test
    void testFindByName() {
        TagServiceImpl tagService = new TagServiceImpl(tagRepository);

        Tag tag = Tag.builder()
                .id(1L)
                .name("Tag Name")
                .posts(List.of(new Post()))
                .build();

        Mockito.when(tagRepository.findByName("Tag Name")).thenReturn(tag);
        Tag actualTag = tagService.findByName("Tag Name");
        assertEquals(tag, actualTag);
    }
}