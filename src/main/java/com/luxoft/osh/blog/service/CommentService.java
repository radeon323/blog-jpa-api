package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllByPostId(Long postId);

    Comment getById(Long id, Long postId);

    void delete(Long id);

    void save(Comment comment, Long postId);

}
