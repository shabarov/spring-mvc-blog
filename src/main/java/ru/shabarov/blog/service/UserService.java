package ru.shabarov.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Contact;
import ru.shabarov.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AbstractDao userDao;

    @Autowired
    private User dummyUser;

    @Autowired
    private Contact userContact;

    @PostConstruct
    public void init() {
        logger.info("Dummy user = {}", dummyUser);
        logger.info("User contact = {}", userContact);
    }

    @Transactional
    public Long create(User user) {
        user.setEnabled(Boolean.TRUE);
        Long id = userDao.create(user);
        userDao.executeQuery(String.format("INSERT INTO user_roles (USER_ID, AUTHORITY) VALUES (%d, 'ROLE_USER');", id));
        logger.info("User created");
        return id;
    }

    @Transactional
    public void delete(User user) {
        userDao.delete(user);
        logger.info("User deleted");
    }

    @Transactional
    public void edit(User user) {
        user.setEnabled(Boolean.TRUE);
        userDao.edit(user);
        logger.info("User updated (edited)");
    }

    @Transactional
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    public User getById(Long userId) {
        return (User) userDao.getById(userId);
    }

    @Transactional
    public List<User> getByAttributeId(Long id, String attribute, String attributeIdName) {
        return userDao.getByAttributeId(id, attribute, attributeIdName);
    }

    @Transactional
    public Long getQuantity() {
        return userDao.getQuantity();
    }

    @Transactional
    public User getByName(String name) {
        List<User> userList = userDao.getByName(name);
        logger.info("User list = {}", userList);
        if (userList != null && userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    public User getCurrentUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (!(principal instanceof AnonymousAuthenticationToken)) {
            return this.getByName(principal.getName());
        }
        return null;
    }
}
