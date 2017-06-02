package ru.uskov.dmotry;

/**
 * Created by Dmitry on 01.06.2017.
 */
public class RequestDevice {

    private Long device_id;
    private Byte fullness;
    private Long container_type_id;
    private String creatin_date;
    private String coordinates;
    private String last_modify;

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public Byte getFullness() {
        return fullness;
    }

    public void setFullness(Byte fullness) {
        this.fullness = fullness;
    }

    public Long getContainer_type_id() {
        return container_type_id;
    }

    public void setContainer_type_id(Long container_type_id) {
        this.container_type_id = container_type_id;
    }

    public String getCreatin_date() {
        return creatin_date;
    }

    public void setCreatin_date(String creatin_date) {
        this.creatin_date = creatin_date;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }
}