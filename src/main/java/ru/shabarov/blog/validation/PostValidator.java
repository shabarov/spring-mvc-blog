package ru.shabarov.blog.validation;

import ru.shabarov.blog.entity.Post;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PostValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return Post.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "", "Post title is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "summary", "", "Post summary is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "", "Post body is required");
    }
}
