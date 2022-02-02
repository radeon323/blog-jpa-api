package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author Oleksandr Shevchenko
 */
public interface TagService {
    List<Tag> findAll();

    Set<Tag> findAllByPostId(Long postId);

    void deleteById(Long tagId);

    void save(Tag tag);

    boolean existsByName(String name);

    Tag findByName(String name);



}
