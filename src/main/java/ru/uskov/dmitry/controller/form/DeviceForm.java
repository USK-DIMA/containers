package ru.uskov.dmitry.controller.form;

import java.util.Set;

public class DeviceForm {

    private String name;
    private String comment;
    private Set<Integer> usersId;

    public Set<Integer> getUsersId() {
        return usersId;
    }

    public void setUsersId(Set<Integer> usersId) {
        this.usersId = usersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}