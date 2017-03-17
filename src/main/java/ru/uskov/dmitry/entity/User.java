package ru.uskov.dmitry.entity;

import ru.uskov.dmitry.enums.ServiceRole;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Entity
@Table(name = "WebUser")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String login;

    private String password;

    private String email;

    private String name;

    @ManyToOne
    @JoinColumn(name = "contragentId")
    private Contragent contragent;

    private Boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<ServiceRole> serviceRoleSet;

    public Set<ServiceRole> getServiceRoleSet() {
        return serviceRoleSet;
    }

    public void setServiceRoleSet(Set<ServiceRole> serviceRoleSet) {
        this.serviceRoleSet = serviceRoleSet;
    }

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

    public Contragent getContragent() {
        return contragent;
    }

    public void setContragent(Contragent contragent) {
        this.contragent = contragent;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
