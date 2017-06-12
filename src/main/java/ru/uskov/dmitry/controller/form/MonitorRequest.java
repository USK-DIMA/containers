package ru.uskov.dmitry.controller.form;

/**
 * Created by Dmitry on 28.05.2017.
 */
public class MonitorRequest {

    private Long timestamp;
    private Integer userId;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}