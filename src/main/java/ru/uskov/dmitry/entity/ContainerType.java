package ru.uskov.dmitry.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Dmitry on 27.05.2017.
 */

@Entity
@Table(name = "ContainerType")
public class ContainerType extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer height;
    private Integer width;
    private Integer lenght;
    private Date createDate;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "containerType")
    private List<Device> devices;

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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
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