package ru.uskov.dmitry.entity;

import ru.uskov.dmitry.enums.UserRole;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "login")
})
public class User extends AbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private Long id;


    @Column(name = "login")
    private String login;


    @Column(name = "password")
    private String password;


    @Column(name = "email")
    private String email;


    @Column(name = "comment")
    private String comment;


    @Column(name = "name")
    private String name;


    @Column(name = "active")
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "device_client_junction",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id"))
    private Set<Device> devices;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
