package ru.shabarov.blog.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedNotification;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;
import ru.shabarov.blog.entity.Post;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.Notification;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
@ManagedResource(objectName="blog:name=PostExportClient")
@ManagedNotification(notificationTypes = "PostNotifier.MessageChange", name = "MessageChange")
public class PostExportClient implements NotificationPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("postExportServiceClient")
    private PostExportService postExportService;

    private ScheduledExecutorService executorService;

    private NotificationPublisher notificationPublisher;

    private String message = "Default message";

    @PostConstruct
    public void init() {
        logger.info("PostExportClient init");
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("PostExportClient running");
                List<Post> allPosts = postExportService.getAllPosts();
                logger.info("Received posts = {}, time = {}", allPosts, new Date());
                logger.info(message);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void clean() {
        executorService.shutdown();
    }

    @ManagedAttribute
    public String getMessage() {
        return message;
    }

    @ManagedAttribute
    public void setMessage(String message) {
        // jconsole -> mbean tab -> choose this mbean -> notifications -> click subscribe -> setMessage
        notificationPublisher.sendNotification(buildNotification(message));
        this.message = message;
    }

    private Notification buildNotification(final String message)
    {
        final Notification notification = new Notification("PostNotifier.MessageChange",
                        this,
                        0,
                        System.currentTimeMillis(),
                        "Set a new message = " + message);
        notification.setUserData("Blog user data");
        return notification;
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }
}
