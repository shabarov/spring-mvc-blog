package ru.shabarov.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(value = "postDao")
    private AbstractDao postDao;

    @Autowired
    private PostLikesService postLikesService;

    @Transactional
    public Long create(Post post) {
        post.setCurrentDate();
        post.setImagePath("DefaultPath");
        Long id = postDao.create(post);

        logger.info("Post created");
        return id;
    }

    @Transactional
    public void delete(Post post) {
        postDao.delete(post);
        postLikesService.deleteLikesForPost(post);
        logger.info("Post deleted");
    }

    @Transactional
    public void edit(Post post) {
        post.setCurrentDate();
        postDao.edit(post);
        logger.info("Post updated (edited)");
    }

    @Transactional
    public List<Post> getAll() {
        return postDao.getAll();
    }

    @Transactional
    public Post getById(Long postId) {
        return (Post) postDao.getById(postId);
    }

    @Transactional
    public List<Post> getByAttributeId(Long id, String attribute, String attributeIdName) {
        return postDao.getByAttributeId(id, attribute, attributeIdName);
    }

    @Transactional
    public Long getQuantity() {
        return postDao.getQuantity();
    }
}
