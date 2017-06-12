package ru.uskov.dmitry.controller.form;

import ru.uskov.dmitry.entity.User;

import java.util.Set;

/**
 * Created by Dmitry on 23.04.2017.
 */
public class UserForm extends User {

    private Set<Integer> deviceId;

    public Set<Integer> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Set<Integer> deviceId) {
        this.deviceId = deviceId;
    }
}