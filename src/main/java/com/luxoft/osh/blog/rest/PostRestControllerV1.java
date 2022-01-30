package com.luxoft.osh.blog.rest;

import com.luxoft.osh.blog.dto.PostFull;
import com.luxoft.osh.blog.dto.PostShort;
import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.entity.Tag;
import com.luxoft.osh.blog.service.PostService;
import com.luxoft.osh.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@RestController
@RequestMapping("/api/v1/posts/")
@RequiredArgsConstructor
public class PostRestControllerV1 {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final PostService postService;

    @Autowired
    private final TagService tagService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostShort>> findAllPosts(@RequestParam(value = "title", required = false) String title,
                                                  @RequestParam(value = "sort", required = false) String sort) {
        logger.info("PostRestControllerV1 findAllPosts");

        List<Post> posts;

        if (sort != null) {
            posts = postService.sortByTitle();
        } else if (title != null) {
            posts = postService.findByTitle(title);
        } else {
            posts = postService.findAll();
        }

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PostShort> postShortList = new ArrayList<>();
        for (Post post : posts) {
            postShortList.add(dtoConvertToShort(post));
        }

        ResponseEntity<List<PostShort>> responseEntity = new ResponseEntity<>(postShortList, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostShort> getPostByIdShort(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 getPostByIdShort {}", postId);

        Post post = postService.getById(postId);


        if (postId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PostShort postShort = dtoConvertToShort(post);

        ResponseEntity<PostShort> responseEntity = new ResponseEntity<>(postShort, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping(value = "{id}/full", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostFull> getPostByIdFull(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 getPostByIdFull {}", postId);

        Post post = postService.getById(postId);

        if (postId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PostFull postFull = dtoConvertToFull(post);

        ResponseEntity<PostFull> responseEntity = new ResponseEntity<>(postFull, HttpStatus.OK);
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

        List<Tag> tags = post.getTags();
        for (Tag tag : tags) {
            tagService.save(tag);
        }

        postService.save(post);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.CREATED);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> updatePost(@RequestBody @Valid Post post, @PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 updatePost {}", post);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        post.setId(postId);
        postService.save(post);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 deletePost {}", postId);

        postService.delete(postId);

        ResponseEntity<Post> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping(value = "star", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostShort>> getPostsWithStar() {
        logger.info("PostRestControllerV1 getPostsWithStar");

        List<Post> posts = postService.getPostsWithStar();

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PostShort> postShortList = new ArrayList<>();
        for (Post post : posts) {
            postShortList.add(dtoConvertToShort(post));
        }

        ResponseEntity<List<PostShort>> responseEntity = new ResponseEntity<>(postShortList, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @PutMapping(value = "{id}/star", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostShort> addStarToPost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 addStarToPost");
        return putDeleteStar(postId);
    }

    @DeleteMapping(value = "{id}/star", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostShort> removeStarFromPost(@PathVariable("id") Long postId) {
        logger.info("PostRestControllerV1 removeStarFromPost");
        return putDeleteStar(postId);
    }




    @Transactional
    private ResponseEntity<PostShort> putDeleteStar(Long postId) {
        Post post = postService.getById(postId);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        post.setStar(!post.isStar());
        postService.save(post);
        PostShort postShort = dtoConvertToShort(post);

        ResponseEntity<PostShort> responseEntity = new ResponseEntity<>(postShort, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    private PostShort dtoConvertToShort(Post post) {
        PostShort postShort = new PostShort();
        postShort.setId(post.getId());
        postShort.setTitle(post.getTitle());
        postShort.setContent(post.getContent());
        postShort.setStar(post.isStar());
        postShort.setTags(post.getTags());
        return postShort;
    }

    private PostFull dtoConvertToFull(Post post) {
        PostFull postFull = new PostFull();
        postFull.setId(post.getId());
        postFull.setTitle(post.getTitle());
        postFull.setContent(post.getContent());
        postFull.setStar(post.isStar());
        postFull.setTags(post.getTags());
        postFull.setComments(post.getComments());
        return postFull;
    }




}
