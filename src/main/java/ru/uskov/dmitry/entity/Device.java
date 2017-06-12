package ru.uskov.dmitry.entity;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class Device extends AbstractEntity {

    public static String USER_COUNT = "USER_COUNT";
    private Integer id;
    private Date createData;
    private String name;
    private Date modifyData;
    private Integer filling;
    private Set<User> users;
    private Boolean active;
    private boolean newDevice;
    private String coordinate;
    private String comment;
    private ContainerType containerType;


    @QueryProjection
    public Device(Integer id, Date createData, String name, Date modifyData, Integer filling, Boolean active, boolean newDevice, Double latitude, Double longitude, String comment) {
        this.id = id;
        this.createData = createData;
        this.name = name;
        this.modifyData = modifyData;
        this.filling = filling;
        this.active = active;
        this.newDevice = newDevice;
        this.coordinate = latitude + "," + longitude;
        this.comment = comment;
    }

    @QueryProjection
    public Device(Integer id, Date createData, String name, Date modifyData, Integer filling, Boolean active, boolean newDevice, Double latitude, Double longitude, String comment, Long userCount) {
        this(id, createData, name, modifyData, filling, active, newDevice, latitude, longitude, comment);
        addField(USER_COUNT, userCount);
    }

    @QueryProjection
    public Device(Integer id, Date createData, String name, Date modifyData, Integer filling, Boolean active, boolean newDevice, Double latitude, Double longitude, String comment, ContainerType containerType) {
        this(id, createData, name, modifyData, filling, active, newDevice, latitude, longitude, comment);
        this.containerType = containerType;
    }

    @QueryProjection
    public Device(Integer id, Date createData, String name, Date modifyData, Integer filling, Boolean active, boolean newDevice, Double latitude, Double longitude, String comment, Set<Integer> userIds) {
        this(id, createData, name, modifyData, filling, active, newDevice, latitude, longitude, comment);
        this.users = userIds.stream().map(userId -> new User(userId)).collect(Collectors.toSet());
    }

    public Device(Integer id) {
        this.id = id;
    }

    public Device() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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