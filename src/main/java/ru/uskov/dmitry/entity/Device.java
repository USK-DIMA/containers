package ru.uskov.dmitry.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Dmitry on 23.04.2017.
 */
@Entity
@Table(name = "device")
public class Device extends AbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "device_id")
    private Long id;

    @Column(name = "creation_date")
    private Date createData;

    @Column(name = "name")
    private String name;

    @Column(name = "last_modified")
    private Date modifyData;

    @Column(nullable = false, columnDefinition = "int default 0 ", name = "fullness")
    private Integer filling;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "device_client_junction",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<User> users;

    @Column(name = "active_status")
    private Boolean active;

    @Column(name = "new_device")
    private boolean newDevice;

    @Column(name = "coordinates")
    private String coordinate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "container_type_id", nullable = false)
    private ContainerType containerType;

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

    public Integer getFilling() {
        return filling;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public void setFilling(Integer filling) {
        this.filling = filling;
    }
}