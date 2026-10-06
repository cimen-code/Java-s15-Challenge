package com.workintech.library.model;

public abstract class User {

    private final int id;
    private String name;
    private String email;

    protected User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void updateContact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
