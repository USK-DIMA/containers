package ru.uskov.dmitry.controller.form;

import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */
public class MonitorTableContainer {

    private Long timestamp;
    private List<MonitorTableForm> monitorTableForm;

    public MonitorTableContainer() {
    }

    public MonitorTableContainer(Long timestamp, List<MonitorTableForm> monitorTableForm) {
        this.timestamp = timestamp;
        this.monitorTableForm = monitorTableForm;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<MonitorTableForm> getMonitorTableForm() {
        return monitorTableForm;
    }

    public void setMonitorTableForm(List<MonitorTableForm> monitorTableForm) {
        this.monitorTableForm = monitorTableForm;
    }
}