package com.luxoft.osh.blog.repository;

import com.luxoft.osh.blog.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByStar(boolean star);

    List<Post> findByTitle(String title);

    List<Post> findByOrderByTitleAsc(Pageable page);

    @Query("select p from Post p left join p.tags tags where tags.name = ?1")
    List<Post> findByTagName(String name);


}
