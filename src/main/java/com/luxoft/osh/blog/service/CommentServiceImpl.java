package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.repository.CommentRepository;
import com.luxoft.osh.blog.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    Logger logger = LoggerFactory.getLogger(getClass());

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public List<Comment> findAllByPostId(Long postId) {
        logger.info("In CommentServiceImpl getAllByPostId");
        return commentRepository.findAllByPost_Id(postId);
    }

    @Transactional
    @Override
    public Comment getByIdAndPost_Id(Long id, Long postId) {
        logger.info("In CommentServiceImpl getByIdAndPost_Id");
        return commentRepository.getByIdAndPost_Id(id, postId);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        logger.info("In CommentServiceImpl deleteById");
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void save(Comment comment, Long postId) {
        logger.info("In CommentServiceImpl save");
        LocalDateTime now = LocalDateTime.now();
        comment.setCreationDate(now);
        comment.setPost(postRepository.getById(postId));
        commentRepository.save(comment);
    }



}
