package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.PostRepository;
import com.luxoft.osh.blog.rest.PostRestControllerV1;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Test
    void testGetAll() {
        List<Post> posts = prepareForTestsAndReturnListOfPosts();
        Mockito.when(postRepository.findAll()).thenReturn(posts);
        assertEquals(3, postRepository.findAll().size());
        assertSame(posts, postRepository.findAll());
    }

    @Test
    void testGetById() {
        Post post = prepareForTestsAndReturnListOfPosts().get(0);
        Optional<Post> optionalPost = Optional.of(post);
        Mockito.when(postRepository.findById(1L)).thenReturn(optionalPost);
        assertEquals("First post", post.getTitle());
    }

    @Test
    void testDelete() {

    }

    @Test
    void testSave() {
        Post post = prepareForTestsAndReturnListOfPosts().get(0);
        Mockito.when(postRepository.save(post)).thenReturn(post);
        assertEquals("First post", post.getTitle());
    }

    @Test
    void testGetPostsWithStar() {
    }

    @Test
    void testFindByTitle() {
    }

    @Test
    void testSortByTitle() {
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

}