package ru.shabarov.blog.validation;

import ru.shabarov.blog.entity.Category;
import ru.shabarov.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

public class CategoryValidator implements Validator {

    @Autowired
    private CategoryService categoryService;

    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Category category = (Category) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "Category name is required");
        List<Category> categoryList = categoryService.getByName(category.getName());
        if (categoryList != null && !categoryList.isEmpty() && errors.getErrorCount() < 1) {
            errors.rejectValue("name", "", "Category with that name already exists");
        }
    }
}
