package ru.shabarov.blog.validation;

import ru.shabarov.blog.entity.Comment;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class CommentValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return Comment.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {

        Comment comment = (Comment) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "", "Comment body is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "", "Author name is required");

        if (!isValidEmail(comment.getEmail()) && errors.getErrorCount() < 1) {
            errors.rejectValue("email", "", "Invalid email address");
        }
    }

    private boolean isValidEmail(String email) {
        String email_pattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-.]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email_pattern);
        return pattern.matcher(email).matches();
    }
}
