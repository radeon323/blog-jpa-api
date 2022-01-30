package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Tag;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public interface TagService {
    List<Tag> findAll();

    List<Tag> findAllByPostId(Long postId);

    void deleteById(Long tagId);

    void save(Tag tag);
}
