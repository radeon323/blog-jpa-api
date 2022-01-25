package com.luxoft.osh.blog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.service.CommentService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(CommentRestControllerV1.class)
class CommentRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;


    @Test
    void testGetAllComments() throws Exception {
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

        given(commentService.getAllByPostId(1L)).willReturn(comments);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/v1/posts/{id}/comments", 1L)
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
        Comment comment = Comment.builder()
                .id(1L)
                .text("First comment text")
                .creationDate(LocalDateTime.now())
                .post(new Post(1L,"","",true,null))
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                .post("/api/v1/posts/{id}/comments", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("First comment text"));
        verify(commentService).save(comment, 1L);
    }




    @Test
    void testGetCommentByIdByPostId() {
    }

    @Test
    void testDeleteCommentByIdByPostId() {
    }








}