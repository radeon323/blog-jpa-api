package com.luxoft.osh.blog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostRestControllerV1.class)
class PostRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private final String API_URL = "/api/v1/posts/";

    @Test
    void testGetAllPosts() throws Exception {
        List<Post> posts = prepareForTestsAndReturnListOfPosts();
        given(postService.getAll()).willReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                .get(API_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("A baba galamaga"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Second post"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].star").isBoolean());
    }

    @Test
    void testGetPost() throws Exception {
        Post post = prepareForTestsAndReturnListOfPosts().get(0);
        given(postService.getById(1L)).willReturn(post);
        mockMvc.perform( MockMvcRequestBuilders
                .get(API_URL + "{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value(true));
    }

    @Test
    void testSavePost() throws Exception {
        Post post = prepareForTestsAndReturnListOfPosts().get(1);
        String json = asJsonString(post);
        mockMvc.perform( MockMvcRequestBuilders
                .post(API_URL, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Second post"));
    }

    @Test
    void testUpdatePost() throws Exception {
        Post post = prepareForTestsAndReturnListOfPosts().get(2);
        String json = asJsonString(post);
        mockMvc.perform( MockMvcRequestBuilders
                .put(API_URL + "{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Third post"));
    }

    @Test
    void testDeletePost() throws Exception {
        Post post = prepareForTestsAndReturnListOfPosts().get(2);
        String json = asJsonString(post);
        mockMvc.perform( MockMvcRequestBuilders
                .delete(API_URL + "{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetPostsWithStar() throws Exception {
        List<Post> posts = prepareForTestsAndReturnListOfPosts();
        given(postService.getPostsWithStar()).willReturn(posts);
        mockMvc.perform( MockMvcRequestBuilders
                .get(API_URL + "star")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("A baba galamaga"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Second post"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].star").isBoolean());
    }

    @Test
    void testAddStarToPost() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put(API_URL + "{id}/star", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveStarFromPost() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete(API_URL + "{id}/star", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



    private List<Post> prepareForTestsAndReturnListOfPosts() {
        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .title("First post")
                .content("A baba galamaga")
                .star(true)
                .build();
        firstPost.setId(1L);
        posts.add(firstPost);

        Post secondPost = Post.builder()
                .title("Second post")
                .content("Eins, zwei, Polizei")
                .star(false)
                .build();
        secondPost.setId(2L);
        posts.add(secondPost);

        Post thirdPost = Post.builder()
                .title("Third post")
                .content("Padav snih na porih, Kit zlipyv sobi pyrih.")
                .star(true)
                .build();
        thirdPost.setId(3L);
        posts.add(thirdPost);

        return posts;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}