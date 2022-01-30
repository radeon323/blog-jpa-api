package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.entity.Tag;
import com.luxoft.osh.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Oleksandr Shevchenko
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        logger.info("In TagServiceImpl findAll");
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> findAllByPostId(Long postId) {
        logger.info("In TagServiceImpl findAllByPostId");
        List<Tag> tags = tagRepository.findAll();
        List<Tag> tagsById = new ArrayList<>();
        for (Tag tag : tags) {
            List<Post> posts = tag.getPosts();
            for (Post post : posts) {
                if (Objects.equals(post.getId(), postId)) {
                    tagsById.add(tag);
                }
            }
        }
        return tagsById;
    }

    @Override
    public void deleteById(Long tagId) {
        logger.info("In TagServiceImpl delete {}", tagId);
        tagRepository.deleteById(tagId);
    }

    @Override
    public void save(Tag tag) {
        logger.info("In TagServiceImpl save {}", tag);
        tagRepository.save(tag);
    }
}
