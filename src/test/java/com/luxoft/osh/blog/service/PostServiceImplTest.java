package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Test
    void testFindAll() {
        PostServiceImpl postService = new PostServiceImpl(postRepository);

        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .build();
        posts.add(firstPost);

        Post secondPost = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .build();
        posts.add(secondPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .build();
        posts.add(thirdPost);

        Mockito.when(postRepository.findAll()).thenReturn(posts);

        List<Post> actualPosts = postService.findAll();
        assertNotNull(actualPosts);
        assertEquals(3, actualPosts.size());
        assertEquals("Content of the second post", actualPosts.get(1).getContent());
        assertEquals(thirdPost, actualPosts.get(2));
    }

    @Test
    void testGetById() {
        PostServiceImpl postService = new PostServiceImpl(postRepository);
        Post post = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .build();

        Mockito.when(postRepository.getById(1L)).thenReturn(post);
        Post actualPost = postService.getById(1L);

        assertEquals("First post", actualPost.getTitle());
        assertEquals("Content of the first post", actualPost.getContent());
        assertTrue(actualPost.isStar());
    }


    @Test
    void testGetPostsWithStar() {
        PostServiceImpl postService = new PostServiceImpl(postRepository);

        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .build();
        posts.add(firstPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .build();
        posts.add(thirdPost);

        Mockito.when(postRepository.findAllByStar(true)).thenReturn(posts);

        List<Post> actualPosts = postService.getPostsWithStar();
        assertEquals(2, actualPosts.size());
        assertEquals("First post", actualPosts.get(0).getTitle());
        assertEquals("Third post", actualPosts.get(1).getTitle());
        assertSame(thirdPost, actualPosts.get(1));
    }

    @Test
    void testFindByTitle() {
        PostServiceImpl postService = new PostServiceImpl(postRepository);

        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .build();
        posts.add(firstPost);

        Post secondPost = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .build();
        posts.add(secondPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .build();
        posts.add(thirdPost);

        Mockito.when(postRepository.findByTitle("Second post")).thenReturn(List.of(secondPost));

        List<Post> actualPosts = postService.findByTitle("Second post");
        assertEquals(1, actualPosts.size());
        assertSame(secondPost, actualPosts.get(0));
    }

    @Test
    void testSortByTitle() {
        PostServiceImpl postService = new PostServiceImpl(postRepository);

        List<Post> posts = new ArrayList<>();

        Post firstPost = Post.builder()
                .id(1L)
                .title("First post")
                .content("Content of the first post")
                .star(true)
                .build();
        posts.add(firstPost);

        Post secondPost = Post.builder()
                .id(2L)
                .title("Second post")
                .content("Content of the second post")
                .star(false)
                .build();
        posts.add(secondPost);

        Post thirdPost = Post.builder()
                .id(3L)
                .title("Third post")
                .content("Content of the third post")
                .star(true)
                .build();
        posts.add(thirdPost);

        Mockito.when(postRepository.findByOrderByTitleAsc(PageRequest.of(0, 10))).thenReturn(posts);

        List<String> sortedTitles = new ArrayList<>(List.of(thirdPost.getTitle(), firstPost.getTitle(), secondPost.getTitle()));
        Collections.sort(sortedTitles);

        List<Post> actualPosts = postService.sortByTitle();

        assertEquals(sortedTitles.get(0), actualPosts.get(0).getTitle());
        assertEquals(sortedTitles.get(1), actualPosts.get(1).getTitle());
        assertEquals(sortedTitles.get(2), actualPosts.get(2).getTitle());
    }




}