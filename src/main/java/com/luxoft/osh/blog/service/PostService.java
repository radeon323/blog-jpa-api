package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import java.util.List;

public interface PostService {

    List<Post> getAll();

    Post getById(Long id);

    void delete(Long id);

    void save(Post post);

    Post getPostWithComments(Long id);

    List<Post> getPostsWithStar();

    void addStar(Long id);

    void removeStar(Long id);

}
