package ru.shabarov.blog.dao;

import java.util.List;

public interface AbstractDao<T> {

    Long create(T entity);

    void delete(T entity);

    T edit(T entity);

    T getById(Long id);

    List<T> getAll();

    Long getQuantity();

    List<T> getByAttributeId(Long id, String attribute, String attributeIdName);

    List<T> getByName(String name);

    void executeQuery(String query);
}
