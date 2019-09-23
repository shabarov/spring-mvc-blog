package ru.shabarov.blog.jms;

import java.io.Serializable;

public class JmsMessage implements Serializable {

    private String message;

    public JmsMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JmsMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
