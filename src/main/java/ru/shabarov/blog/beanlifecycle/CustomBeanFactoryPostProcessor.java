package ru.shabarov.blog.beanlifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        logger.info("Bean definitions: {}", configurableListableBeanFactory.getBeanDefinitionNames());
        logger.info("Bean definitions count: {}", configurableListableBeanFactory.getBeanDefinitionCount());
    }
}
