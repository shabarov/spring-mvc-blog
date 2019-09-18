package ru.shabarov.blog.controller;

import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.entity.Comment;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.service.CategoryService;
import ru.shabarov.blog.service.CommentService;
import ru.shabarov.blog.service.PostService;
import ru.shabarov.blog.validation.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    @Qualifier(value = "commentValidator")
    private CommentValidator commentValidator;

    @ModelAttribute("categories")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @ModelAttribute("username")
    public String getUserName(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/newcomment/{postId}")
    public String addNewComment(@ModelAttribute("comment") Comment comment,
                                @PathVariable("postId") Long postId,
                                BindingResult result,
                                Model model) {
        Post post = postService.getById(postId);
        model.addAttribute("post", post);
        commentValidator.validate(comment, result);
        Boolean hasErrors = result.hasErrors();

        if (hasErrors) {
            return "post";
        }

        comment.setPost(post);
        commentService.create(comment);
        model.addAttribute("comment", new Comment());
        return "redirect:/post/" + postId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comments")
    public String showCommentsAdminPage(Model model) {
        model.addAttribute("comments", commentService.getAll());
        return "admin/comments";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Model model) {
        Comment comment = commentService.getById(commentId);
        comment.getPost().getComments().remove(comment);
        commentService.delete(comment);
        model.addAttribute("comments", commentService.getAll());
        return "redirect:/admin/comments";
    }
}
