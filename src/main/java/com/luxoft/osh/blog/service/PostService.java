package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import java.util.List;

public interface PostService {

    public List<Post> getAll();

    public Post getById(Long id);

    public void delete(Long id);

    public void save(Post post);

    public void edit(Post post, Long id);

    public Post getPostWithComments(Long id);

    public List<Post> getPostsWithStar();

    public void addStar(Long id);

    public void removeStar(Long id);

}
