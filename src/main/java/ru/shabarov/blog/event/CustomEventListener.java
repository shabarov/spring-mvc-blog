package ru.shabarov.blog.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener implements ApplicationListener<CustomApplicationEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(CustomApplicationEvent customApplicationEvent) {
        logger.info("Message: " + customApplicationEvent.getMessage());
    }
}
