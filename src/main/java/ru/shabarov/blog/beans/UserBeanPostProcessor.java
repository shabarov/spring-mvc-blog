package ru.shabarov.blog.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.shabarov.blog.entity.User;

@Component
public class UserBeanPostProcessor implements BeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long DEFAULT_USER_ID = -1;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof User) {
            logger.info("Before initialization, bean = {}", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof User) {
            User user = (User) bean;
            user.setUserId(DEFAULT_USER_ID);
            logger.info("Set default user id for bean = {}", beanName);
        }
        return bean;
    }
}
