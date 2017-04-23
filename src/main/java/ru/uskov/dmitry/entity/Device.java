package ru.uskov.dmitry.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Dmitry on 23.04.2017.
 */
@Entity
@Table(name = "Device")
public class Device extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Date createData;

    private String name;

    private Date modifyData;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;

    private Boolean active;

    private boolean newDevice;

    private String coordinate;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateData() {
        return createData;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getModifyData() {
        return modifyData;
    }

    public void setModifyData(Date modifyData) {
        this.modifyData = modifyData;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isNewDevice() {
        return newDevice;
    }

    public void setNewDevice(boolean newDevice) {
        this.newDevice = newDevice;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}