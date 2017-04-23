package ru.uskov.dmitry.dao;

import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionaMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.Device;

import java.util.List;

/**
 * Created by Dmitry on 23.04.2017.
 */
@Repository
public class DeviceDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDao.class);

    @TransactionalSupport
    public List<Device> getAll() {
        return getAll(Device.class);
    }

    @TransactionalSupport
    public Device get(Long deviceId) {
        return (Device) getCurrentSession().get(Device.class, deviceId);
    }

    @TransactionaMandatory
    public void setActive(Long deviceId, Boolean active) {
        Session session = getCurrentSession();
        Device device = (Device) session.get(Device.class, deviceId);
        device.setActive(active);
        session.saveOrUpdate(device);
    }

    @TransactionaMandatory
    public void update(Device device) {
        getCurrentSession().update(device);
    }
}