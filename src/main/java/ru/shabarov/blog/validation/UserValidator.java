package ru.shabarov.blog.validation;

import ru.shabarov.blog.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "User name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "E-mail is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Password is required");
    }
}
