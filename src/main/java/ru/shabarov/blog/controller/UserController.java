package ru.shabarov.blog.controller;

import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.entity.User;
import ru.shabarov.blog.service.CategoryService;
import ru.shabarov.blog.service.UserService;
import ru.shabarov.blog.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserValidator userValidator;

    @ModelAttribute("username")
    public String getUserName(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account")
    public String showAccountPage(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getByName(principal.getName());
            System.out.println("Principal name = " + principal.getName());
            System.out.println("User = " + user);
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("isLogged", Boolean.TRUE);
            }
        } else {
            model.addAttribute("isLogged", Boolean.FALSE);
            model.addAttribute("user", new User());
        }
        return "account";
    }

/*    @RequestMapping(method = RequestMethod.GET, value = "/account/edit/{userId}")
    public String showAccountEditPage(@PathVariable("userId") Long userId, Model model){
        User user = userService.getById(userId);
        model.addAttribute("user", user);
        return "account";
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "account/{action}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable String action,
                             BindingResult result,
                             Model model) {
        userValidator.validate(user, result);
        boolean hasErrors = result.hasErrors();
        Boolean isCreate = action.equalsIgnoreCase("create");

        if (hasErrors) {
            model.addAttribute("isCreate", isCreate);
            return "account";
        }

        if (isCreate) {
            userService.create(user);
            model.addAttribute("username", user.getName());
            return "redirect:/login";
        } else {
            System.out.println("Edited user = " + user);
            userService.edit(user);
        }
        return "redirect:/account";
    }
}
