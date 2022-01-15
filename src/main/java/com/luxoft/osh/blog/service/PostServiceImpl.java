package com.luxoft.osh.blog.service;

import com.luxoft.osh.blog.entity.Post;
import com.luxoft.osh.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

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
    public void edit(Post post, Long id) {
        logger.info("In PostServiceImpl edit {}", post);
        Post editedPost = postRepository.getById(id);

        if(Objects.nonNull(post.getTitle()) && !"".equalsIgnoreCase(post.getTitle())){
            editedPost.setTitle(post.getTitle());
        }
        if(Objects.nonNull(post.getContent()) && !"".equalsIgnoreCase(post.getContent())) {
            editedPost.setContent(post.getContent());
        }
        if(editedPost.isStar()) {
            editedPost.setStar(!post.isStar());
        } else {
            editedPost.setStar(post.isStar());
        }

        postRepository.save(editedPost);
    }

    @Override
    public Post getPostWithComments(Long id) {
        logger.info("In PostServiceImpl getPostWithComments {}", id);
        return null;
    }

    @Override
    public List<Post> getPostsWithStar() {
        logger.info("In PostServiceImpl getPostsWithStar");
        return postRepository.findAllByStar(true);
    }

    @Override
    public void addStar(Long id) {
        logger.info("In PostServiceImpl markPostWithStar {}", id);
        Post post = postRepository.getById(id);
        post.setStar(true);
        postRepository.save(post);
    }

    @Override
    public void removeStar(Long id) {
        logger.info("In PostServiceImpl removeStar {}", id);
        Post post = postRepository.getById(id);
        post.setStar(false);
        postRepository.save(post);
    }
}
