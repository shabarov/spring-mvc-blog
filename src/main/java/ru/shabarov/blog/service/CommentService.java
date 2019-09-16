package ru.shabarov.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(value = "commentDao")
    AbstractDao commentDao;

    @Transactional
    public void create(Comment comment) {
        comment.setCurrentDate();
        commentDao.create(comment);
        logger.info("Comment created");
    }

    @Transactional
    public void delete(Comment comment) {
        commentDao.delete(comment);
        logger.info("Comment deleted");
    }

    @Transactional
    public void edit(Comment comment) {
        comment.setCurrentDate();
        commentDao.edit(comment);
        logger.info("Comment updated (edited)");
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
