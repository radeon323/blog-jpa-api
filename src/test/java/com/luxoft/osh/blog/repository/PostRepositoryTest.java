package com.luxoft.osh.blog.repository;

import com.luxoft.osh.blog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void savePost() {
        Post post = Post.builder()
                .title("Second post")
                .content("A baba galamaga 2")
                .star(false)
                .build();
        postRepository.save(post);
    }

    @Test
    public void printAllPosts() {
        List<Post> postList = postRepository.findAll();
        System.out.println("postList = " + postList);
    }



}