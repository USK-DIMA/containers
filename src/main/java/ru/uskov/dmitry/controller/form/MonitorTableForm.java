package ru.uskov.dmitry.controller.form;

import ru.uskov.dmitry.entity.Device;

import java.text.SimpleDateFormat;

/**
 * Created by Dmitry on 27.05.2017.
 */
public class MonitorTableForm {

    private Long id;
    private String name;
    private Integer fullness;
    private String lastModify;
    private String containerName;
    private Integer volume;
    private String dimensions;

    public MonitorTableForm(Device d) {
        id = d.getId();
        name = d.getName();
        fullness = d.getFilling();
        lastModify = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d.getModifyData());
        containerName = d.getContainerType().getName();
        Integer w = d.getContainerType().getWidth();
        Integer h = d.getContainerType().getHeight();
        Integer l = d.getContainerType().getLenght();
        volume = w * h * l;
        dimensions = h + "*" + w + "*" + l;
    }

    public MonitorTableForm() {
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

    public Integer getFullness() {
        return fullness;
    }

    public void setFullness(Integer fullness) {
        this.fullness = fullness;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}