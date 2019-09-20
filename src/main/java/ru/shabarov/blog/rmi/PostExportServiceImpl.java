package ru.shabarov.blog.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.service.PostService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service("postExportServiceImpl")
public class PostExportServiceImpl implements PostExportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postService.getAll();
        logger.info("Fetched posts = {}", posts);
        return posts;
//        return Collections.singletonList(new Post(0L,
//                "RMI Test Post",
//                "RMI Test Post",
//                "RMI Test Post",
//                new Date().toString(),
//                null,
//                null));
    }
}
