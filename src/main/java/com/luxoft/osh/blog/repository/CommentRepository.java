package com.luxoft.osh.blog.repository;

import com.luxoft.osh.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_Id(Long id);

    Comment getByIdAndPost_Id(Long id, Long postId);

}
