package ru.shabarov.blog.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("jmsServiceListener")
public class JmsServiceListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void processMessage(JmsMessage message) {
        logger.info("Jms message: {}", message);
    }
}
