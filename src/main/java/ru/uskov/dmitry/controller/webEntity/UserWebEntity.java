package ru.uskov.dmitry.controller.webEntity;

import ru.uskov.dmitry.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 22.04.2017.
 */
public class UserWebEntity {
    protected Integer id;
    protected String fio;
    protected String login;
    protected String email;
    protected Set<String> roles;
    protected Long deviceCount;
    protected Boolean active;

    public UserWebEntity() {
    }

    public UserWebEntity(Integer id, String fio, String login, String email, Set<String> roles, Long deviceCount, Boolean active) {
        this.id = id;
        this.fio = fio;
        this.login = login;
        this.email = email;
        this.roles = roles;
        this.deviceCount = deviceCount;
        this.active = active;
    }

    public UserWebEntity(User user) {
        this.id = user.getId();
        this.fio = user.getName();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(r -> r.name()).collect(Collectors.toSet());
        this.deviceCount = (Long) user.getValue(User.DEVICE_COUNT);
        this.active = user.getActive();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Long deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static class Builder extends UserWebEntity {

        public UserWebEntity build() {
            return new UserWebEntity(id, fio, login, email, roles, deviceCount, active);
        }

        public UserWebEntity.Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserWebEntity.Builder fio(String fio) {
            this.fio = fio;
            return this;
        }


        public UserWebEntity.Builder login(String login) {
            this.login = login;
            return this;
        }


        public UserWebEntity.Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserWebEntity.Builder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserWebEntity.Builder deviceCount(Long deviceCount) {
            this.deviceCount = deviceCount;
            return this;
        }

        public UserWebEntity.Builder active(Boolean active) {
            this.active = active;
            return this;
        }

    }
}