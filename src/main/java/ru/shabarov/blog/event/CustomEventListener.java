package ru.shabarov.blog.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.shabarov.blog.config.MyBean;

import javax.annotation.PostConstruct;

@Component
public class CustomEventListener implements ApplicationListener<CustomApplicationEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MyBean myBean;

    @PostConstruct
    public void init() {
        logger.info("Bean property: " + myBean.getProperty());
    }

    @Override
    public void onApplicationEvent(CustomApplicationEvent customApplicationEvent) {
        logger.info("Message: " + customApplicationEvent.getMessage());
    }
}
