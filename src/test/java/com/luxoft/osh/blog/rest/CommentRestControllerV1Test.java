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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        given(commentService.findAllByPostId(1L)).willReturn(comments);
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
        verify(commentService, times(1)).findAllByPostId(1L);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment text"));
        verify(commentService).save(comment, 1L);
    }

    @Test
    void testGetCommentByIdByPostId() throws Exception {
        Comment comment = Comment.builder()
                .id(1L)
                .text("First comment text")
                .creationDate(LocalDateTime.now())
                .post(new Post(1L,"","",true,null))
                .build();

        when(commentService.getByIdAndPost_Id(1L, 1L)).thenReturn(comment);
        mockMvc.perform( MockMvcRequestBuilders
                    .get("/api/v1/posts/{postId}/comment/{commentId}", 1, 1)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("First comment text"));
        verify(commentService, times(1)).getByIdAndPost_Id(1L,1L);
    }

    @Test
    void testDeleteCommentByIdByPostId() throws Exception {
        Comment comment = Comment.builder()
                .id(1L)
                .text("First comment text")
                .creationDate(LocalDateTime.now())
                .post(new Post(1L,"","",true,null))
                .build();

        mockMvc.perform( MockMvcRequestBuilders
                    .delete("/api/v1/posts/{postId}/comment/{commentId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(comment)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(status().isNoContent());
        verify(commentService, times(1)).deleteById(1L);
    }



}