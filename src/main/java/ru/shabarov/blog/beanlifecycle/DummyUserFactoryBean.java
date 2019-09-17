package ru.shabarov.blog.beanlifecycle;

import org.springframework.beans.factory.FactoryBean;
import ru.shabarov.blog.entity.User;

public class DummyUserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        User user = new User("Dummy User", "", "dummy.user@mail.com", true);
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
