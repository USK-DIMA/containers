package ru.uskov.dmitry.controller.form;

import ru.uskov.dmitry.entity.User;

/**
 * Created by Dmitry on 24.04.2017.
 */
public class UserProfileForm {

    private String name;
    private String login;
    private String email;

    public UserProfileForm(User user) {
        this.name = user.getName();
        this.login = user.getLogin();
        this.email = user.getEmail();
    }

    public UserProfileForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}