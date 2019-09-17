package ru.shabarov.blog.entity;

public class Contact {

    private User contact;

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contact=" + contact +
                '}';
    }
}
