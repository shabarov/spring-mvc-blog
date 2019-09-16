package ru.shabarov.blog.service;

import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    @Qualifier(value = "commentDao")
    AbstractDao commentDao;

    @Transactional
    public void create(Comment comment) {
        comment.setCurrentDate();
        commentDao.create(comment);

        System.out.println("Comment created");
    }

    @Transactional
    public void delete(Comment comment) {
        commentDao.delete(comment);
        System.out.println("Comment deleted");
    }

    @Transactional
    public void edit(Comment comment) {
        comment.setCurrentDate();
        commentDao.edit(comment);
        System.out.println("Comment updated (edited)");
    }

    @Transactional
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Transactional
    public Comment getById(Long commentId) {
        return (Comment) commentDao.getById(commentId);
    }

    @Transactional
    public List<Comment> getByAttributeId(Long id, String attribute, String attributeIdName) {
        return commentDao.getByAttributeId(id, attribute, attributeIdName);
    }

    @Transactional
    public Long getQuantity() {
        return commentDao.getQuantity();
    }
}
