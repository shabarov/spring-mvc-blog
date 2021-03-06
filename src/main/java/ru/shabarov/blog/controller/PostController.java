package ru.shabarov.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shabarov.blog.dao.AbstractDao;
import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.entity.Comment;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.entity.PostLike;
import ru.shabarov.blog.event.CustomApplicationEvent;
import ru.shabarov.blog.service.ImageService;
import ru.shabarov.blog.service.PostLikesService;
import ru.shabarov.blog.service.PostService;
import ru.shabarov.blog.service.UserService;
import ru.shabarov.blog.validation.PostValidator;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    @Qualifier(value = "categoryDao")
    private AbstractDao categoryDao;

    @Autowired
    private PostLikesService postLikesService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "postValidator")
    private PostValidator postValidator;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/")
    public String initialLoad() {
        return "redirect:/index";
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }

    @ModelAttribute("username")
    public String getUserName(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public String showHomePage(Model model) {
        logger.info("Home page showed");
        List<Post> posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{categoryId}")
    public String showPostsByCategoryPage(@PathVariable("categoryId") String categoryId, ModelMap modelMap) {
        //Getting Post collection by category id
        List<Post> posts = postService.getByAttributeId(Long.parseLong(categoryId), "category", "categoryId");
        modelMap.put("posts", posts);
        logger.info("Posts by category = {}", posts);
        //Getting all Category collection
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/post/{postId}")
    public String showPostByIdPage(@PathVariable("postId") Long postId, ModelMap modelMap, Principal principal) {
        //Getting post by id
        Post post = postService.getById(postId);
        List<PostLike> likesForPost = postLikesService.getLikesForPost(post);
        Boolean likedByCurrentUser = postLikesService.isLikedByCurrentUser(likesForPost);
        modelMap.put("post", post);
        modelMap.put("comment", new Comment());
        modelMap.put("isLiked", likedByCurrentUser);
        modelMap.put("likeCount", likesForPost.size());
        return "post";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/newpost")
    public String showNewPostPage(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("isCreate", Boolean.TRUE);
        return "createOrEditPost";
    }

/*    @RequestMapping(method = RequestMethod.GET, value = "/admin/{action}/{postId}")
    public String showEditPostPage(@PathVariable Long postId, @PathVariable String action, Model model){
        Post post = (Post) postService.getById(postId);
        if (action.equalsIgnoreCase("edit")) {
            model.addAttribute("post", post);
            model.addAttribute("isCreate", Boolean.FALSE);
            return "createOrEditPost";
        } else if (action.equalsIgnoreCase("delete")) {
            postService.delete(post);
        }
        return "redirect:/index";
    }*/

    @RequestMapping(method = RequestMethod.POST, value = {"/updatepost/create", "/updatepost/edit"})
    public String createPost(@Valid @ModelAttribute("post") Post post,
                             BindingResult result,
                             @RequestParam(value = "file") MultipartFile image) throws IOException {
        postValidator.validate(post, result);
        boolean hasErrors = result.hasErrors();

        if (hasErrors) {
            return "createOrEditPost";
        }
        postService.create(post, image);
        applicationEventPublisher.publishEvent(
                new CustomApplicationEvent(this, "Post created, title = " + post.getTitle()));
        return "redirect:/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/posts")
    public String showPostEditPage(Model model) {
        List<Post> posts = postService.getAll();
        model.addAttribute("posts", posts);
        model.addAttribute("isCreate", Boolean.TRUE);
        model.addAttribute("post", new Post());
        return "admin/posts";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/post/{action}/{postId}")
    public String showUpdatePostPage(@PathVariable Long postId, @PathVariable String action, Model model) {
        List<Post> posts = postService.getAll();
        Post post = postService.getById(postId);
        model.addAttribute("posts", posts);
        if (action.equalsIgnoreCase("edit")) {
            model.addAttribute("post", post);
            model.addAttribute("isCreate", Boolean.FALSE);
            return "admin/posts";
        } else if (action.equalsIgnoreCase("delete")) {
            /*This 'remove' method is used to avoid
            'deleted object would be re-saved by cascade (remove deleted object from associations)'
            problem, when entity is deleted, but presents in associations*/
            post.getCategory().getPosts().remove(post);
            postService.delete(post);
        }
        return "redirect:/admin/posts";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/post/{action}")
    public String updatePost(@ModelAttribute("post") Post post,
                             @PathVariable String action,
                             BindingResult result,
                             Model model) throws IOException {
        postValidator.validate(post, result);
        boolean hasErrors = result.hasErrors();
        Boolean isCreate = action.equalsIgnoreCase("create");

        if (hasErrors) {
            //Check case when user enters empty post field (title, summary, etc) editing existing post
            if (!isCreate) {
                String editedPostTitle = postService.getById(post.getId()).getTitle();
                post.setTitle(editedPostTitle);
                model.addAttribute("post", post);
                model.addAttribute("posts", postService.getAll());
            }
            model.addAttribute("isCreate", isCreate);
            return "/admin/posts#editForm";
        }

        Long updatedPostId = null;
        if (isCreate) {
            updatedPostId = postService.create(post, null);
        } else {
            logger.info("Edited post = {}", post);
            postService.edit(post);
            updatedPostId = post.getId();
        }
        return "redirect:/admin/posts#postId" + updatedPostId;
    }

    // Transform category ID referenced from posts.jsp form to Category object and
    // sets it to the appropriate field of Post object
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Category.class, "category", new PropertyEditorSupport() {

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Category category = (Category) value;
                    return category.getName();
                }
                return null;
            }

            public void setAsText(String text) {
                if (text != null) {
                    Long categoryId = Long.parseLong(text);
                    Category category = (Category) categoryDao.getById(categoryId);
                    setValue(category);
                }
            }
        });
    }
}
