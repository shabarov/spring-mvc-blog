package ru.shabarov.blog.controller;

import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.service.CategoryService;
import ru.shabarov.blog.validation.CategoryValidator;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    @Qualifier(value = "categoryValidator")
    private CategoryValidator categoryValidator;

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

    @RequestMapping(method = RequestMethod.GET, value = "/admin/categories")
    public String showCategoryPage(Model model) {
        model.addAttribute("isCreate", Boolean.TRUE);
        model.addAttribute("category", new Category());
        return "admin/categories";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/category/{action}/{categoryId}")
    public String updateCategory(@PathVariable Long categoryId, @PathVariable String action, Model model) {
        Category category = (Category) categoryService.getById(categoryId);
        if (action.equalsIgnoreCase("edit")) {
            model.addAttribute("category", category);
            model.addAttribute("isCreate", Boolean.FALSE);
            return "admin/categories";
        } else if (action.equalsIgnoreCase("delete")) {
            categoryService.delete(category);
        }
        return "redirect:/admin/categories";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/category/{action}")
    public String updatePost(@ModelAttribute("category") Category category,
                             @PathVariable String action,
                             BindingResult result,
                             Model model) {
        categoryValidator.validate(category, result);
        boolean hasErrors = result.hasErrors();
        Boolean isCreate = action.equalsIgnoreCase("create");

        if (hasErrors) {
            //Check case when user enters empty category name editing existing category
            if (!isCreate) {
                String editedCategoryName = categoryService.getById(category.getId()).getName();
                category.setName(editedCategoryName);
                model.addAttribute("category", category);
            }
            model.addAttribute("isCreate", isCreate);
            return "/admin/categories";
        }

        if (isCreate) {
            categoryService.create(category);
        } else {
            categoryService.edit(category);
        }
        return "redirect:/admin/categories";
    }
}
