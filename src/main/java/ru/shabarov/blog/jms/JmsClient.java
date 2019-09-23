package ru.shabarov.blog.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class JmsClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    private ScheduledExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> jmsClientHandler = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("JMS Client running");
                jmsTemplate.send(new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        JmsMessage message = new JmsMessage("Blog Jms message example, stamp=" + new Date().getTime());
                        return session.createObjectMessage(message);
                    }
                });
            }
        }, 0, 5, TimeUnit.SECONDS);

//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                jmsClientHandler.cancel(true);
//            }
//        }, 60, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void clean() {
        executorService.shutdown();
    }

}
