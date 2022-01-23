package com.luxoft.osh.blog.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.CommentRepository;
import com.luxoft.osh.blog.repository.PostRepository;
import com.luxoft.osh.blog.service.CommentService;
import com.luxoft.osh.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(CommentRestControllerV1.class)
class CommentRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;


    private final String API_URL = "/api/v1/posts/";


    @Test
    void testGetAllComments() throws Exception {
        List<Comment> comments = prepareForTestsAndReturnListOfComments();
        given(commentService.getAllByPostId(1L)).willReturn(comments);
        mockMvc.perform( MockMvcRequestBuilders
                .get(API_URL + "{id}/comments", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("First comment text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].text").value("Second comment text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].text").value("Third comment text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").doesNotExist());

    }


    @Test
    void testSaveComment() throws Exception {
        Comment comment = prepareForTestsAndReturnListOfComments().get(0);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);

        String json = asJsonString(comment);

        mockMvc.perform( MockMvcRequestBuilders
                .post(API_URL + "{id}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("First comment text"));
    }

    @Test
    void testGetCommentByIdByPostId() {
    }

    @Test
    void testDeleteCommentByIdByPostId() {
    }




    private List<Comment> prepareForTestsAndReturnListOfComments() {
        List<Comment> comments = new ArrayList<>();
        Post post = new Post(1L,"","",true,null);
        LocalDateTime now = LocalDateTime.now();

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

        Comment thirdComment = Comment.builder()
                .id(3L)
                .text("Third comment text")
                .creationDate(now)
                .post(post)
                .build();
        comments.add(thirdComment);

        return comments;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}