package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post getById(Long id);

    void delete(Long id);

    void save(Post post);

    List<Post> getPostsWithStar();

    List<Post> findByTitle(String title);

    List<Post> sortByTitle();

    List<Post> findByTagName(String name);


}
