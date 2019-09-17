package ru.shabarov.blog.event;

import org.springframework.context.ApplicationEvent;

public class CustomApplicationEvent extends ApplicationEvent {
    private String message;

    public CustomApplicationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
