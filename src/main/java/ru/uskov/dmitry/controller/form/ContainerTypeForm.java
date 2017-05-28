package ru.uskov.dmitry.controller.form;

import ru.uskov.dmitry.entity.ContainerType;

import java.util.Date;

/**
 * Created by Dmitry on 28.05.2017.
 */
public class ContainerTypeForm {

    private Long id;
    private String name;
    private Date create;
    private Integer width;
    private Integer height;
    private Integer lenght;
    private Integer deviceCount;

    public ContainerTypeForm(ContainerType typeEntity) {
        id = typeEntity.getId();
        name = typeEntity.getName();
        create = typeEntity.getCreateDate();
        width = typeEntity.getWidth();
        height = typeEntity.getHeight();
        lenght = typeEntity.getLenght();
        deviceCount = typeEntity.getDevices().size();
    }

    public ContainerTypeForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }
}