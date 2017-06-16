package ru.uskov.dmitry.entity;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.List;

/**
 * Created by Dmitry on 27.05.2017.
 */


public class ContainerType extends AbstractEntity {
    private Long id;
    private String name;
    private String description;
    private Double height;
    private Double width;
    private Double lenght;
    private Date createDate;
    private List<Device> devices;

    public ContainerType() {
    }

    @QueryProjection
    public ContainerType(String name, Double height, Double width, Double lenght) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.lenght = lenght;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}