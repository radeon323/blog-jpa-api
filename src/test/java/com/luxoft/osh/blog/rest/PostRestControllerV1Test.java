package com.luxoft.osh.blog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.entity.Tag;
import com.luxoft.osh.blog.service.PostService;
import com.luxoft.osh.blog.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Oleksandr Shevchenko
 */
@WebMvcTest(PostRestControllerV1.class)
class PostRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private TagService tagService;

    @Test
    void testGetAllPosts() throws Exception {
        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(firstPost);

        Post secondPost = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(secondPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(thirdPost);

        when(postService.findAll()).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].content").value("Content of the first post"))
                .andExpect(jsonPath("$[1].title").value("Second post"))
                .andExpect(jsonPath("$[1].name").doesNotExist())
                .andExpect(jsonPath("$[2].star").isBoolean());
        verify(postService, times(1)).findAll();
    }

    @Test
    void testGetAllPostsIfPostsIsEmpty() throws Exception {
        List<Post> posts = new ArrayList<>();

        when(postService.findAll()).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(postService, times(1)).findAll();
    }

    @Test
    void testSortByTitle() throws Exception {
        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(firstPost);

        Post secondPost = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(secondPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(thirdPost);

        when(postService.sortByTitle()).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/?sort=title")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("First post"))
                .andExpect(jsonPath("$[1].title").value("Second post"))
                .andExpect(jsonPath("$[2].title").value("Third post"));
        verify(postService, times(1)).sortByTitle();
    }

    @Test
    void testSortByTitleIfPostsIsEmpty() throws Exception {
        List<Post> posts = new ArrayList<>();

        when(postService.sortByTitle()).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/?sort=title")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(postService, times(1)).sortByTitle();
    }

    @Test
    void testFindByTitle() throws Exception {
        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(firstPost);

        when(postService.findByTitle("First post")).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/?title={title}", "First post")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("First post"));
        verify(postService, times(1)).findByTitle("First post");
    }

    @Test
    void testFindByTitleIfPostsIsEmpty() throws Exception {
        List<Post> posts = new ArrayList<>();

        when(postService.findByTitle("First post")).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/?title={title}", "First post")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(postService, times(1)).findByTitle("First post");
    }

    @Test
    void testGetPostByIdShort() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        when(postService.getById(1L)).thenReturn(post);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/{id}", 1)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("First post"))
                .andExpect(jsonPath("$.star").value(true));
        verify(postService, times(1)).getById(1L);
    }

    @Test
    void testGetPostByIdShortIfPostNull() throws Exception {
        when(postService.getById(1L)).thenReturn(null);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(postService, times(1)).getById(1L);
    }

    @Test
    void testGetPostByIdFull() throws Exception {
        List<Comment> comments = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        Post post = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(comments)
                .tags(new HashSet<>())
                .build();

        Comment firstComment = Comment.builder()
                .id(1L)
                .text("First comment text")
                .creationDate(now)
                .post(post)
                .build();
        comments.add(firstComment);

        Comment secondComment = Comment.builder()
                .id(2L)
                .text("Second comment text")
                .creationDate(now)
                .post(post)
                .build();
        comments.add(secondComment);

        when(postService.getById(1L)).thenReturn(post);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{id}/full", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("First post"))
                .andExpect(jsonPath("$.star").value(true))
                .andExpect(jsonPath("$.comments[0].text").value("First comment text"))
                .andExpect(jsonPath("$.comments[0].post").value(post.getId()))
                .andExpect(jsonPath("$.comments[1].text").value("Second comment text"))
                .andExpect(jsonPath("$.comments[1].post").value(post.getId()));
        verify(postService, times(1)).getById(1L);
    }

    @Test
    void testGetPostByIdFullIfPostNull() throws Exception {
        when(postService.getById(1L)).thenReturn(null);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{id}/full", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(postService, times(1)).getById(1L);
    }

    @Test
    void testSavePost() throws Exception {
        Post post = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                    .post("/api/v1/posts/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Second post"));
        verify(postService).save(any(Post.class));
    }

    @Test
    void testSavePostIfPostIsNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePost() throws Exception {
        Post post = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                    .put("/api/v1/posts/{id}", 2)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.star").value(false))
                .andExpect(jsonPath("$.title").value("Second post"));
        verify(postService).save(any(Post.class));
    }

    @Test
    void testUpdatePostIfPostIsNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/v1/posts/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeletePost() throws Exception {
        Post post = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                    .delete("/api/v1/posts/{id}", 3)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(status().isNoContent());
        verify(postService, times(1)).delete(3L);
    }

    @Test
    void testGetPostsWithStar() throws Exception {
        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(firstPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();
        posts.add(thirdPost);

        when(postService.getPostsWithStar()).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/star")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].star").value(true))
                .andExpect(jsonPath("$[1].star").value(true));
        verify(postService, times(1)).getPostsWithStar();
    }

    @Test
    void testGetPostsWithStarIfPostsIsEmpty() throws Exception {
        List<Post> posts = new ArrayList<>();

        when(postService.getPostsWithStar()).thenReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/star")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(postService, times(1)).getPostsWithStar();
    }

    @Test
    void testAddStarToPost() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(false)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        when(postService.getById(1L)).thenReturn(post);
        mockMvc.perform( MockMvcRequestBuilders
                    .put("/api/v1/posts/{id}/star", 1)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.star").value(true))
                .andExpect(status().isOk());
        verify(postService, times(1)).getById(1L);
        verify(postService, times(1)).save(any(Post.class));
        verify(postService).save(any(Post.class));
    }

    @Test
    void testAddStarToPostIfPostIsNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/v1/posts/{id}/star", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRemoveStarFromPost() throws Exception {
        Post post = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        when(postService.getById(2L)).thenReturn(post);
        mockMvc.perform( MockMvcRequestBuilders
                    .delete("/api/v1/posts/{id}/star", 2)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.star").value(false))
                .andExpect(status().isOk());
        verify(postService, times(1)).getById(2L);
        verify(postService, times(1)).save(any(Post.class));
        verify(postService).save(any(Post.class));
    }

    @Test
    void testRemoveStarFromPostIfPostIsNull() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/api/v1/posts/{id}/star", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddTagsToPost() throws Exception {

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

        Post post = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(Set.of(tag1, tag2))
                .build();

        List<String> tagNames = new ArrayList<>();
        tagNames.add(tag1.getName());
        tagNames.add(tag2.getName());

        when(postService.getById(1L)).thenReturn(post);
        when(tagService.existsByName(tag1.getName())).thenReturn(true);
        when(tagService.findByName(tag1.getName())).thenReturn(tag1);
        when(tagService.existsByName(tag2.getName())).thenReturn(true);
        when(tagService.findByName(tag2.getName())).thenReturn(tag2);
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/v1/posts/{id}/tags?tag=", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Tag1", "Tag1")
                        .param("Tag2", "Tag2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("First post"))
                .andExpect(jsonPath("$.tags").value(tagNames))
                .andExpect(jsonPath("$.tags[0]").value("Tag1"))
                .andExpect(jsonPath("$.tags[1]").value("Tag2"));
        verify(postService, times(1)).getById(1L);
        verify(postService, times(1)).save(any(Post.class));
        verify(postService).save(any(Post.class));
    }

    @Test
    void testRemoveTagsFromPost() throws Exception {

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

        Post post = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .comments(new ArrayList<>())
                .tags(new HashSet<>())
                .build();

        when(postService.getById(1L)).thenReturn(post);
        when(tagService.findByName(tag1.getName())).thenReturn(tag1);
        when(tagService.findByName(tag2.getName())).thenReturn(tag2);
        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/api/v1/posts/{id}/tags?tag=", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("Tag1", "Tag1")
                        .param("Tag2", "Tag2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("First post"))
                .andExpect(jsonPath("$.tags").isEmpty());
        verify(postService, times(1)).getById(1L);
        verify(postService, times(1)).save(any(Post.class));
        verify(postService).save(any(Post.class));
    }

}