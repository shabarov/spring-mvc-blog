package ru.shabarov.blog.service;

import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    @Qualifier(value = "postDao")
    private AbstractDao postDao;

    @Transactional
    public Long create(Post post) {
        post.setCurrentDate();
        post.setImagePath("DefaultPath");
        Long id = postDao.create(post);

        System.out.println("Post created");
        return id;
    }

    @Transactional
    public void delete(Post post) {
        postDao.delete(post);
        System.out.println("Post deleted");
    }

    @Transactional
    public void edit(Post post) {
        post.setCurrentDate();
        postDao.edit(post);
        System.out.println("Post updated (edited)");
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
