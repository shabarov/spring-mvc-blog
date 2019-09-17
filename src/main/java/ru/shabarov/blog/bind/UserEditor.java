package ru.shabarov.blog.bind;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import ru.shabarov.blog.entity.User;

import java.beans.PropertyEditorSupport;

public class UserEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Object value = getValue();
        User user = (User) value;
        return user.getName() + " " + user.getEmail();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!Strings.isNullOrEmpty(text)) {
            String[] parts = text.split("\\-");
            Preconditions.checkState(parts.length == 2);
            String name = parts[0];
            String email = parts[1];
            User user = new User(name, "", email, false);
            setValue(user);
        }
    }
}
