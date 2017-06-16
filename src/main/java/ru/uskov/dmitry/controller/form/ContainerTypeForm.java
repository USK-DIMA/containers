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
    private Double width;
    private Double height;
    private Double lenght;
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

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }
}