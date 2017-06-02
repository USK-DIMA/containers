package ru.uskov.dmotry;

import java.util.List;

/**
 * Created by Dmitry on 01.06.2017.
 */
public class Request {

    private List<RequestDevice> devices;
    private List<RequestContentType> containers;

    public List<RequestDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<RequestDevice> devices) {
        this.devices = devices;
    }

    public List<RequestContentType> getContainers() {
        return containers;
    }

    public void setContainers(List<RequestContentType> containers) {
        this.containers = containers;
    }
}



