package com.luxoft.osh.blog.rest;

import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.service.CommentService;
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
public class CommentRestControllerV1 {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final CommentService commentService;

    @GetMapping(value = "{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable("id") Long postId) {
        logger.info("CommentRestControllerV1 getAllComments");

        List<Comment> comments = commentService.findAllByPostId(postId);

        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<List<Comment>> responseEntity = new ResponseEntity<>(comments, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }


    @PostMapping(value = "{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> saveComment(
            @RequestBody @Valid Comment comment, @PathVariable("id") Long postId) {
        logger.info("CommentRestControllerV1 saveComment {}", comment);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        commentService.save(comment, postId);

        ResponseEntity<Comment> responseEntity = new ResponseEntity<>(comment, HttpStatus.CREATED);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }


    @GetMapping(value = "{postId}/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getCommentByIdByPostId(
            @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        logger.info("CommentRestControllerV1 getCommentByIdByPostId");

        Comment comment = commentService.getByIdAndPost_Id(commentId, postId);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<Comment> responseEntity = new ResponseEntity<>(comment, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping(value = "{postId}/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> deleteCommentByIdByPostId(
            @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        logger.info("CommentRestControllerV1 deleteCommentByIdByPostId");

        commentService.deleteById(commentId);

        ResponseEntity<Comment> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

}
