package com.luxoft.osh.blog.rest;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/posts/")
@RequiredArgsConstructor
public class PostRestControllerV1 {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final PostService postService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getAllPosts() {
        logger.info("PostRestControllerV1 getAllPosts");

        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<List<Post>> responseEntity = new ResponseEntity<>(posts, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 getPost {}", postId);

        Post post = postService.getById(postId);

        if (postId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> savePost(@RequestBody @Valid Post post) {
        logger.info("PostRestControllerV1 savePost {}", post);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        postService.save(post);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.CREATED);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @PostMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> updatePost(@RequestBody @Valid Post post, @PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 updatePost {}", post);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        postService.edit(post, postId);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 deletePost {}", postId);

        Post post = postService.getById(postId);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        postService.delete(postId);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping("star")
    public ResponseEntity<List<Post>> getPostsWithStar() {
        logger.info("PostRestControllerV1 getPostsWithStar");

        List<Post> posts = postService.getPostsWithStar();

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("{id}/star")
    public ResponseEntity<Post> addStarToPost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 addStarToPost");

        postService.addStar(postId);
        Post post = postService.getById(postId);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping("{id}/star")
    public ResponseEntity<Post> removeStarFromPost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 removeStarFromPost");

        postService.removeStar(postId);
        Post post = postService.getById(postId);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }


}
