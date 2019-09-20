package ru.shabarov.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.service.PostLikesService;

@Controller
@RequestMapping("/like")
public class PostLikeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostLikesService postLikesService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST, value = "/create/post/{postId}")
    public String createLike(@PathVariable("postId") String postId) {
        logger.info("Create like");
        postLikesService.createLike(new Post(Long.parseLong(postId)));
        return "redirect:/post/" + postId;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST, value = "/delete/post/{postId}")
    public String deleteLike(@PathVariable("postId") String postId) {
        logger.info("Delete like");
        postLikesService.deleteLike(new Post(Long.parseLong(postId)));
        return "redirect:/post/" + postId;
    }
}
