package ru.uskov.dmitry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.dao.DeviceDao;
import ru.uskov.dmitry.entity.Device;

import java.util.List;

/**
 * Created by Dmitry on 23.04.2017.
 */
@Service
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceDao deviceDao;

    @TransactionalSupport
    public List<Device> getAll() {
        return deviceDao.getAll();
    }

    @TransactionalSupport
    public Device get(Long deviceId) {
        return deviceDao.get(deviceId);
    }

    @TransactionalService
    public void setActive(Long deviceId, Boolean active) {
        deviceDao.setActive(deviceId, active);
    }

    @TransactionalService
    public void update(Long deviceId, String name, String comment) {
        Device device = deviceDao.get(deviceId);
        device.setName(name);
        device.setComment(comment);
        deviceDao.update(device);
    }
}