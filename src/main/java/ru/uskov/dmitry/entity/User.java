package ru.uskov.dmitry.entity;

import com.querydsl.core.annotations.QueryProjection;
import ru.uskov.dmitry.enums.UserRole;
import ru.uskov.dmitry.validator.annotataions.NewUser;

import java.util.Set;
import java.util.stream.Collectors;

@NewUser
public class User extends AbstractEntity {

    public static String DEVICE_COUNT = "DEVICE_COUNT";
    private Integer id;
    private String login;
    private String password;
    private String email;
    private String comment;
    private String name;
    private Boolean active;
    private Set<Device> devices;
    private Set<UserRole> roles;


    @QueryProjection
    public User(Integer id, String login, String password, String email, String comment, String name, Boolean active) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.comment = comment;
        this.name = name;
        this.active = active;
    }

    public User(Integer id) {
        this.id = id;
    }

    @QueryProjection
    public User(Integer id, String login, String password, String email, String comment, String name, Boolean active, Set<String> roles) {
        this(id, login, password, email, comment, name, active);
        this.roles = roles.stream().map(r -> UserRole.valueOf(r)).collect(Collectors.toSet());
    }

    @QueryProjection
    public User(Integer id, String login, String password, String email, String comment, String name, Boolean active, Set<String> roles, Long deviceCount) {
        this(id, login, password, email, comment, name, active, roles);
        addField(DEVICE_COUNT, deviceCount);
    }

    @QueryProjection
    public User(Integer id, String login, String password, String email, String comment, String name, Boolean active, Set<String> roles, Set<Integer> deviceIds) {
        this(id, login, password, email, comment, name, active, roles);
        this.devices = deviceIds.stream().map(dId -> new Device(dId)).collect(Collectors.toSet());
    }

    public User() {
    }

    public User(Integer id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Boolean getActive() {
        if (active == null) {
            return false;
        }
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
