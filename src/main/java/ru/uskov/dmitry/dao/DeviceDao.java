package ru.uskov.dmitry.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.dml.SQLInsertClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.CollectionUtils;
import ru.uskov.dmitry.domain.QContainer;
import ru.uskov.dmitry.domain.QContainerType;
import ru.uskov.dmitry.domain.QDevice;
import ru.uskov.dmitry.domain.QDeviceClientJunction;
import ru.uskov.dmitry.entity.ContainerType;
import ru.uskov.dmitry.entity.Device;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dmitry on 23.04.2017.
 */
@Repository
public class DeviceDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDao.class);

    QDevice qDevice = QDevice.device;
    QContainer qContainer = QContainer.container;
    QContainerType qContainerType = QContainerType.containerType;
    QDeviceClientJunction qDeviceClientJunction = QDeviceClientJunction.deviceClientJunction;

    @TransactionalSupport
    public List<Device> getAllWithUserCount() {
        return queryFactory.select(getDeviceProjectionWithUserCount()).from(qDevice)
                .leftJoin(qDevice._device_containe_4D94879BFK, qContainer)
                .leftJoin(qDevice.__deviceCl_devic_5070F446FK, qDeviceClientJunction)
                .groupBy(qDevice.all())
                .groupBy(qContainer.all())
                .fetch();
    }

    @TransactionalSupport
    public List<Device> getForUserLastModify(Integer userId) {
        SQLQuery query = queryFactory.select(getDeviceProjection()).from(qDevice)
                .leftJoin(qDevice._device_containe_4D94879BFK, qContainer)
                .leftJoin(qDevice.__deviceCl_devic_5070F446FK, qDeviceClientJunction)
                .where(qDeviceClientJunction.clientId.eq(userId));
        return query.fetch();
    }

    private ConstructorExpression<Device> getDeviceProjection() {
        return Projections.constructor(Device.class
                , qDevice.deviceId
                , qDevice.creationDate
                , qDevice.name
                , qDevice.lastModified
                , qDevice.fullnessPercent
                , qDevice.activeStatus
                , qDevice.newDevice
                , qContainer.latitude
                , qContainer.longitude
                , qDevice.comment
        );
    }


    @TransactionalSupport
    public Device getWithUserIds(Integer deviceId) {
        Map<Integer, Device> map = queryFactory.select(qDevice.all()).from(qDevice)
                .leftJoin(qDevice._device_containe_4D94879BFK, qContainer)
                .leftJoin(qDevice.__deviceCl_devic_5070F446FK, qDeviceClientJunction)
                .where(qDevice.deviceId.eq(deviceId)).transform(GroupBy.groupBy(qDevice.deviceId).as(getDeviceProjectionWithUserIds()));
        return map.keySet().stream().map(k -> map.get(k)).findAny().orElseGet(() -> null);
    }

    @TransactionalSupport
    public Device getActive(Integer userId, Integer deviceId) {
        List<Device> devices = queryFactory.select(getDeviceProjection()).from(qDevice)
                .leftJoin(qDevice._device_containe_4D94879BFK, qContainer)
                .leftJoin(qDevice.__deviceCl_devic_5070F446FK, qDeviceClientJunction)
                .where(qDevice.deviceId.eq(deviceId).and(qDeviceClientJunction.clientId.eq(userId)).and(qDevice.activeStatus.isTrue())).fetch();

        if (CollectionUtils.isEmpty(devices)) {
            return null;
        } else {
            return devices.get(0);
        }
    }

    private ConstructorExpression<Device> getDeviceProjectionWithUserIds() {
        return Projections.constructor(Device.class
                , qDevice.deviceId
                , qDevice.creationDate
                , qDevice.name
                , qDevice.lastModified
                , qDevice.fullnessPercent
                , qDevice.activeStatus
                , qDevice.newDevice
                , qContainer.latitude
                , qContainer.longitude
                , qDevice.comment
                , GroupBy.set(qDeviceClientJunction.clientId)
        );
    }

    private ConstructorExpression<Device> getDeviceProjectionWithUserCount() {
        return Projections.constructor(Device.class
                , qDevice.deviceId
                , qDevice.creationDate
                , qDevice.name
                , qDevice.lastModified
                , qDevice.fullnessPercent
                , qDevice.activeStatus
                , qDevice.newDevice
                , qContainer.latitude
                , qContainer.longitude
                , qDevice.comment
                , qDeviceClientJunction.clientId.count()
        );
    }

    @TransactionalMandatory
    public void setActive(Integer deviceId, Boolean active) {
        queryFactory.update(qDevice).set(qDevice.activeStatus, active).where(qDevice.deviceId.eq(deviceId)).execute();
    }

    @TransactionalMandatory
    public void update(Integer deviceId, String name, String comment) {
        queryFactory.update(qDevice).set(qDevice.name, name).set(qDevice.comment, comment).where(qDevice.deviceId.eq(deviceId)).execute();
    }

    @TransactionalMandatory
    public void deleteUsers(Integer deviceId) {
        queryFactory.delete(qDeviceClientJunction).where(qDeviceClientJunction.deviceId.eq(deviceId)).execute();
    }

    @TransactionalMandatory
    public void insertUsers(Integer deviceId, Set<Integer> usersId) {
        if (CollectionUtils.isEmpty(usersId)) {
            return;
        }
        SQLInsertClause clause = queryFactory.insert(qDeviceClientJunction);
        usersId.stream().forEach(id -> clause.set(qDeviceClientJunction.clientId, id).set(qDeviceClientJunction.deviceId, deviceId).addBatch());
        clause.execute();
    }

    @TransactionalSupport
    public List<Device> getDeviceWithContainerType(Integer userId, Date date, boolean onlyActive) {
        SQLQuery query = queryFactory.select(getDeviceWithContainerTypeProjection()).from(qDevice)
                .leftJoin(qDevice._device_containe_4D94879BFK, qContainer)
                .leftJoin(qContainer._container_type_52593CB8FK, qContainerType)
                .leftJoin(qDevice.__deviceCl_devic_5070F446FK, qDeviceClientJunction)
                .where(qDeviceClientJunction.clientId.eq(userId));
        if (date != null) {
            query.where(qDevice.lastModified.after(new Timestamp(date.getTime())));
        }
        if (onlyActive) {
            query.where(qDevice.activeStatus.isTrue());
        }
        return query.fetch();
    }

    private ConstructorExpression<Device> getDeviceWithContainerTypeProjection() {
        return Projections.constructor(Device.class
                , qDevice.deviceId
                , qDevice.creationDate
                , qDevice.name
                , qDevice.lastModified
                , qDevice.fullnessPercent
                , qDevice.activeStatus
                , qDevice.newDevice
                , qContainer.latitude
                , qContainer.longitude
                , qDevice.comment
                , getLiteContainerTypeProjection()
        );
    }


    //todo тут присутсвует поле из container, а не containerType
    private ConstructorExpression<ContainerType> getLiteContainerTypeProjection() {
        return Projections.constructor(ContainerType.class
                , qContainer.name
                , qContainerType.height
                , qContainerType.width
                , qContainerType.length
        );
    }
}