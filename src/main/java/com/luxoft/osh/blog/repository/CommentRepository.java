package com.luxoft.osh.blog.repository;

import com.luxoft.osh.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.id = :commentId and c.post.id = :postId")
    Comment findByIdAndPostId(@Param("commentId") Long commentId, @Param("postId") Long postId);


    @Query("select c from Comment c where c.post.id = :postId")
    List<Comment> findAllByPostId(@Param("postId") Long postId);

}
