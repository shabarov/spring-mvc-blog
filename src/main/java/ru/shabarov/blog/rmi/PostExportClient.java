package ru.shabarov.blog.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shabarov.blog.entity.Post;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PostExportClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("postExportServiceClient")
    private PostExportService postExportService;

    @PostConstruct
    public void init() {
        logger.info("PostExportClient init");
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("PostExportClient running");
                List<Post> allPosts = postExportService.getAllPosts();
                logger.info("Received posts = {}, time = {}", allPosts, new Date());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

}
