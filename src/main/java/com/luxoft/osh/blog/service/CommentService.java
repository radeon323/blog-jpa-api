package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllByPostId(Long postId);

    Comment getByIdAndPost_Id(Long commentId, Long postId);

    void deleteById(Long commentId);

    void save(Comment comment, Long postId);

}
