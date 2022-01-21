package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final PostRepository postRepository;

    @Override
    public List<Post> getAll() {
        logger.info("In PostServiceImpl getAll");
        return postRepository.findAll();
    }

    @Override
    public Post getById(Long id) {
        logger.info("In PostServiceImpl getById {}", id);
        return postRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        logger.info("In PostServiceImpl delete {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public void save(Post post) {
        logger.info("In PostServiceImpl save {}", post);
        postRepository.save(post);
    }

    @Override
    public List<Post> getPostsWithStar() {
        logger.info("In PostServiceImpl getPostsWithStar");
        return postRepository.findAllByStar(true);
    }

    @Override
    public List<Post> findByTitle(String title) {
        logger.info("In PostServiceImpl findByTitle {}", title);
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> sortByTitle() {
        logger.info("In PostServiceImpl sortByTitle");
        return postRepository.findByOrderByTitleAsc(PageRequest.of(0, 4));
    }


}
