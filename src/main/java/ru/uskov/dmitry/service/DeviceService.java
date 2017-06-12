package ru.uskov.dmitry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.Common;
import ru.uskov.dmitry.dao.DeviceDao;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.entity.Device;
import ru.uskov.dmitry.entity.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry on 23.04.2017.
 */
@Service
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;

    @TransactionalSupport
    public List<Device> getAllWithUserCount() {
        return deviceDao.getAllWithUserCount();
    }

    @TransactionalSupport
    public Device getWithUserIds(Integer deviceId) {
        return deviceDao.getWithUserIds(deviceId);
    }

    @TransactionalSupport
    public Device getActive(Integer userId, Integer deviceId) {
        return deviceDao.getActive(userId, deviceId);
    }

    @TransactionalService
    public void setActive(Integer deviceId, Boolean active) {
        deviceDao.setActive(deviceId, active);
    }

    @TransactionalService
    public void update(Integer deviceId, String name, String comment, Set<Integer> usersId) {
        deviceDao.update(deviceId, name, comment);
        deviceDao.deleteUsers(deviceId);
        deviceDao.insertUsers(deviceId, usersId);
    }

    public List<Device> getAllActiveForCurrentUser() {
        User user = Common.getCurrentUser();
        if (user == null) {
            return new LinkedList<>();
        }
        return getAllActiveForUser(user.getId());
    }

    public List<Device> getAllActiveForUser(Integer userId) {
        return deviceDao.getForUserLastModify(userId);
    }

    @TransactionalSupport
    public List<Device> getDeviceWithContainerType(Integer userId, Date date, boolean onlyActive) {
        return deviceDao.getDeviceWithContainerType(userId, date, onlyActive);
    }
}