package ru.uskov.dmitry.controller.webEntity;

import ru.uskov.dmitry.entity.Device;

import java.text.SimpleDateFormat;

/**
 * Created by Dmitry on 23.04.2017.
 */
public class DeviceWebEntity {
    private Long id;
    private String createData;
    private String name;
    private Integer userCount;
    private String modifyData;
    private String comment;
    private Boolean active;


    public DeviceWebEntity(Device device) {
        this.id = device.getId();
        this.createData = new SimpleDateFormat("yyyy-MM-dd").format(device.getCreateData());
        this.name = device.getName();
        this.userCount = device.getUsers().size();
        this.modifyData = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(device.getModifyData());
        this.active = device.getActive();
        this.comment = device.getComment();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DeviceWebEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getModifyData() {
        return modifyData;
    }

    public void setModifyData(String modifyData) {
        this.modifyData = modifyData;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}