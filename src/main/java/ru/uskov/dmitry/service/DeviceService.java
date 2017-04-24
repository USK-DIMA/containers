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

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void update(Long deviceId, String name, String comment, Set<Long> usersId) {
        Device device = deviceDao.get(deviceId);
        device.setName(name);
        List<User> users = getUsers(usersId);
        device.setUsers(users.stream().collect(Collectors.toSet()));
        device.setComment(comment);
        deviceDao.update(device);
    }

    private List<User> getUsers(Set<Long> usersId) {
        if (usersId == null || usersId.size() == 0) {
            return new LinkedList<>();
        }
        return userDao.getUsers(usersId);
    }

    public Set<Device> getAllActiveForCurrentUser() {
        User user = Common.getCurrentUser();
        if (user == null) {
            return new LinkedHashSet<>();
        }
        return userDao.getUser(user.getId()).getDevices();
    }
}