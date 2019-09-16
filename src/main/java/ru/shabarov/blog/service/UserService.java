package ru.shabarov.blog.service;

import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AbstractDao userDao;

    @Transactional
    public Long create(User user) {
        user.setEnabled(Boolean.TRUE);
        Long id = userDao.create(user);
        userDao.executeQuery(String.format("INSERT INTO user_roles (USER_ID, AUTHORITY) VALUES (%d, 'ROLE_USER');", id));
        System.out.println("User created");
        return id;
    }

    @Transactional
    public void delete(User user) {
        userDao.delete(user);
        System.out.println("User deleted");
    }

    @Transactional
    public void edit(User user) {
        user.setEnabled(Boolean.TRUE);
        userDao.edit(user);
        System.out.println("User updated (edited)");
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
        if (userList != null && userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }
}
