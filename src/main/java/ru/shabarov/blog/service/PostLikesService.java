package ru.shabarov.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.entity.PostLike;
import ru.shabarov.blog.entity.User;

import java.util.List;

@Service
public class PostLikesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AbstractDao postLikeDao;

    @Autowired
    private UserService userService;

    public List<PostLike> getLikesForPost(Post post) {
        return (List<PostLike>) postLikeDao.getByAttributeId(post.getId(), null, "postId_fk");
    }

    public int getLikesCountForPost(Post post) {
        return getLikesForPost(post).size();
    }

    public boolean isLikedByCurrentUser(List<PostLike> likes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        logger.info("Post likes = {}", likes);
        // TODO: Spring 3 does not compatible with java 8 (lambda)
        //return likes.stream().anyMatch(l -> l.getUser().getUserId().equals(currentUser.getUserId()));
        for (PostLike pl : likes) {
            if (pl.getUser().getUserId().equals(currentUser.getUserId())) return true;
        }
        return false;
    }

    public void deleteLikesForPost(Post post) {
        PostLike like = new PostLike();
        like.setPost(post);
        postLikeDao.delete(like);
    }

    public void deleteLike(Post post) {
        User user = userService.getCurrentUser();
        if (user == null) {
            logger.error("To unlike the post please authorize");
            return;
        }
        postLikeDao.delete(new PostLike(post, user));
    }

    public void createLike(Post post) {
        User user = userService.getCurrentUser();
        if (user == null) {
            logger.error("To like the post please authorize");
            return;
        }
        postLikeDao.create(new PostLike(post, user));
    }
}
