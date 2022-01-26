package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.CommentRepository;
import com.luxoft.osh.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    void testFindAllByPostId() {
        CommentServiceImpl commentService = new CommentServiceImpl(commentRepository, postRepository);

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

        Mockito.when(commentRepository.findAllByPost_Id(1L)).thenReturn(comments);

        List<Comment> actualComments = commentService.findAllByPostId(1L);
        assertNotNull(actualComments);
        assertEquals(3, actualComments.size());
        assertEquals("Second comment text", actualComments.get(1).getText());
        assertEquals(thirdComment, actualComments.get(2));
    }

    @Test
    void testGetByIdAndPost_Id() {
        CommentServiceImpl commentService = new CommentServiceImpl(commentRepository, postRepository);

        Comment comment = Comment.builder()
                .id(1L)
                .text("First comment text")
                .creationDate(LocalDateTime.now())
                .post(new Post(1L,"","",true,null))
                .build();

        Mockito.when(commentRepository.getByIdAndPost_Id(1L, 1L)).thenReturn(comment);
        Comment actualComment = commentService.getByIdAndPost_Id(1L, 1L);

        assertEquals("First comment text", actualComment.getText());
        assertEquals(1L, actualComment.getPostId());
        assertInstanceOf(Post.class, actualComment.getPost());
    }

    @Test
    void testDeleteById() {

    }

    @Test
    void testSave() {

    }
}