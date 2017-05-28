package ru.uskov.dmitry.controller.form;

/**
 * Created by Dmitry on 28.05.2017.
 */
public class MonitorRequest {

    private Long timestamp;
    private Long userId;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}