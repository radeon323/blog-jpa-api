package com.luxoft.osh.blog.repository;

import com.luxoft.osh.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Oleksandr Shevchenko
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
