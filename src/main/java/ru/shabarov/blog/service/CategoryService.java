package ru.shabarov.blog.service;

import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    @Qualifier(value = "categoryDao")
    private AbstractDao categoryDao;

    @Transactional
    public void create(Category category) {
        categoryDao.create(category);
        System.out.println("Category created");
    }

    @Transactional
    public void delete(Category category) {
        categoryDao.delete(category);
        System.out.println("Category deleted");
    }

    @Transactional
    public void edit(Category category) {
        categoryDao.edit(category);
        System.out.println("Category updated (edited)");
    }

    @Transactional
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Transactional
    public Category getById(Long categoryId) {
        return (Category) categoryDao.getById(categoryId);
    }

    @Transactional
    public List<Category> getByAttributeId(Long id, String attribute, String attributeIdName) {
        return categoryDao.getByAttributeId(id, attribute, attributeIdName);
    }

    @Transactional
    public Long getQuantity(){
        return categoryDao.getQuantity();
    }

    @Transactional
    public List<Category> getByName(String name){
        List<Category> list = (List<Category>) categoryDao.getByName(name);
        System.out.println("Category list = " + list);
        return list;
    }
}
