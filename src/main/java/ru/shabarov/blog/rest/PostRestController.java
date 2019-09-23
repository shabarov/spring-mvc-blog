package ru.shabarov.blog.rest;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.service.CategoryService;
import ru.shabarov.blog.service.PostService;

import java.io.IOException;
import java.util.Collections;

@Controller
@RequestMapping("/rest")
public class PostRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/post/{id}", produces = "application/json")
    public @ResponseBody Post getPostById(@PathVariable long id) {
        logger.info("Received rest controller request (GET), id = {}", id);
        return postService.getById(id);
    }

    // curl -X PUT -H "Content-Type: application/json"
    // -d '{"title":"Mountains Updated","summary":"Mountains","body":"Mountains","postDate":"2019-09-20 10:34:37",
    // "imagePath":"/images/image-1568964877279.png","id":18}' http://localhost:8080/rest/post/18
    @RequestMapping(method = RequestMethod.PUT, value = "/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePostById(@PathVariable long id, @RequestBody Post post) {
        logger.info("Received rest controller request (PUT), id = {}", id);
        logger.info("Post title to be updated = {}", post.getTitle());
        Post fetchedPost = postService.getById(id);
        post.setCategory(fetchedPost.getCategory());
        post.setComments(fetchedPost.getComments());
        postService.edit(post);
    }

    // curl -X DELETE http://localhost:8080/rest/post/20
    @RequestMapping(method = RequestMethod.DELETE, value = "/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable long id) {
        logger.info("Received rest controller request (DELETE), id = {}", id);
        postService.delete(postService.getById(id));
    }

    // curl -X POST -H "Content-Type: application/json" -d
    // '{"title":"Mountains","summary":"Mountains","body":"Mountains Edited","postDate":"2019-09-20 10:34:37",
    // "imagePath":"/images/image-1568964877279.png"}' http://localhost:8080/rest/post?categoryId=1
    @RequestMapping(method = RequestMethod.POST, value = "/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody Post post, @RequestParam long categoryId) throws IOException {
        logger.info("Received rest controller request (POST)");
        Preconditions.checkArgument(post != null);
        Category category = categoryService.getById(categoryId);
        post.setCategory(category);
        post.setComments(Collections.emptySet());
        postService.create(post, null);
    }
}
