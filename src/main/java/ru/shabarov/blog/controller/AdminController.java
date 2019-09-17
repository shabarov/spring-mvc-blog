package ru.shabarov.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${blog.test.message}")
    private String message;

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void init() {
        logger.info(message);
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    public String showAdminHomePage(ModelMap modelMap, Principal principal) {
        String name = principal.getName();
        modelMap.put("username", name);
        return "/admin/admin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "/admin/login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(Model model) {
        model.addAttribute("error", "true");
        return "/admin/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return "/admin/login";
    }
}
