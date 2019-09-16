package ru.shabarov.blog.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AbstractDaoImpl<T> implements AbstractDao<T> {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    private Class<T> type;

    public AbstractDaoImpl(Class<T> type) {
        this.type = type;
    }

    public Long create(T entity) {
        Long id = (Long) hibernateTemplate.save(entity);
        return id;
    }

    public void delete(T entity) {
        hibernateTemplate.delete(entity);
    }

    public T edit(T entity) {
        hibernateTemplate.getSessionFactory().getCurrentSession().clear();
        hibernateTemplate.saveOrUpdate(entity);
        return entity;
    }

    public T getById(Long id) {
        return hibernateTemplate.get(type, id);
    }

    public List<T> getByName(String name) {
        List<T> result = (List<T>) hibernateTemplate.find(String.format("FROM %s WHERE name = '%s'", this.type.getSimpleName(), name));
/*        if(this.type.equals(User.class)){
            result = (List<T>) hibernateTemplate.find(String.format("FROM User WHERE name = '%s'", name));
        }

        if(this.type.equals(Category.class)){
            result = (List<T>) hibernateTemplate.find(String.format("FROM Category WHERE name = '%s'", name));
        }*/
        return result;
    }

    public List<T> getAll() {
        return hibernateTemplate.loadAll(type);
    }

    public Long getQuantity() {
        String entityType = this.type.getSimpleName();
        System.out.println(entityType);
        Long count = (Long) hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select count(*) from " + entityType).uniqueResult();
        return count;
    }

    public List<T> getByAttributeId(Long id, String attribute, String attributeIdName) {

        Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(this.type);

        //ATTENTION: hibernate ocassionly returns dublicated of the first entry in table
        //           In order to fix it, i use ResultTransformer
        criteria.add(Restrictions.eq(attribute + "." + attributeIdName, id)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> results = null;
        try {
            results = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void executeQuery(String query) {
        SQLQuery sqlQuery = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(query);
        sqlQuery.executeUpdate();
    }
}
