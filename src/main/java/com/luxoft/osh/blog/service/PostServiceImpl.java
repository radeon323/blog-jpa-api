package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final PostRepository postRepository;

    @Transactional
    @Override
    public List<Post> findAll() {
        logger.info("In PostServiceImpl getAll");
        return postRepository.findAll();
    }

    @Transactional
    @Override
    public Post getById(Long id) {
        logger.info("In PostServiceImpl getById {}", id);
        return postRepository.getById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        logger.info("In PostServiceImpl delete {}", id);
        postRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void save(Post post) {
        logger.info("In PostServiceImpl save {}", post);
        postRepository.save(post);
    }

    @Transactional
    @Override
    public List<Post> getPostsWithStar() {
        logger.info("In PostServiceImpl getPostsWithStar");
        return postRepository.findAllByStar(true);
    }

    @Transactional
    @Override
    public List<Post> findByTitle(String title) {
        logger.info("In PostServiceImpl findByTitle {}", title);
        return postRepository.findByTitle(title);
    }

    @Transactional
    @Override
    public List<Post> sortByTitle() {
        logger.info("In PostServiceImpl sortByTitle");
        return postRepository.findByOrderByTitleAsc(PageRequest.of(0, 10));
    }


}
