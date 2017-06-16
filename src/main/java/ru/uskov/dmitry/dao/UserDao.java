package ru.uskov.dmitry.dao;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.common.CollectionUtils;
import ru.uskov.dmitry.domain.QClient;
import ru.uskov.dmitry.domain.QDeviceClientJunction;
import ru.uskov.dmitry.domain.QRole;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.enums.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Repository
public class UserDao extends AbstractDao {


    private static final QClient qClient = QClient.client;
    private static final QRole qRole = QRole.role;
    private static final QDeviceClientJunction qDeviceClientJunction = QDeviceClientJunction.deviceClientJunction;


    @TransactionalSupport
    public List<User> getAllWithRolesAndDeviceCount() {
        Map<Integer, User> map = queryFactory.select(qClient.all()).from(qClient)
                .leftJoin(qClient.__role_clientId_59063A47FK, qRole)
                .leftJoin(qClient.__deviceCl_clien_4F7CD00DFK, qDeviceClientJunction)
                .groupBy(qClient.all())
                .groupBy(qRole.all())
                .transform(GroupBy.groupBy(qClient.clientId).as(getUserProjectionWithRolesAndDeviceCount()));
        return map.keySet().stream().map(k -> map.get(k)).collect(Collectors.toList());
    }

    /**
     * Возвращает всех пользователей заисключением пользователя с id = userId
     *
     * @param userId
     * @return
     */
    @TransactionalSupport
    public List<User> loadAllWithout(Integer userId) {
        if (userId == null) {
            userId = -1;
        }
        SQLQuery query = queryFactory.select(getUserProjection()).from(qClient).where(qClient.clientId.ne(userId));
        return query.fetch();
    }

    @TransactionalMandatory
    public Integer insertUser(User user) {
        return queryFactory.insert(qClient)
                .set(qClient.login, user.getLogin())
                .set(qClient.password, user.getPassword())
                .set(qClient.email, user.getEmail())
                .set(qClient.comment, user.getComment())
                .set(qClient.name, user.getName())
                .set(qClient.active, user.getActive())
                .executeWithKey(qClient.clientId);
    }

    @TransactionalMandatory
    public void delete(Integer userId) {
        queryFactory.delete(qClient).where(qClient.clientId.eq(userId)).execute();
    }

    @TransactionalMandatory
    public long setActive(Integer userId, Boolean active) {
        return queryFactory.update(qClient).set(qClient.active, active).where(qClient.clientId.eq(userId)).execute();
    }


    @TransactionalMandatory
    public long update(Integer id, String login, String email, String name, String comment) {
        SQLUpdateClause query = queryFactory.update(qClient)
                .set(qClient.login, login)
                .set(qClient.email, email)
                .set(qClient.name, name);

        if (comment != null) {
            query.set(qClient.comment, comment);
        }

        return query.where(qClient.clientId.eq(id)).execute();
    }

    @TransactionalSupport
    public User getUser(Integer userId) {
        SQLQuery query = queryFactory.select(getUserProjection()).from(qClient).where(qClient.clientId.eq(userId));
        return (User) query.fetch().get(0);
    }


    public List<User> getUsersWithRoles(Set<Integer> usersId) {
        Map<Integer, User> map = queryFactory.select(qClient.all()).from(qClient).leftJoin(qClient.__role_clientId_59063A47FK, qRole).where(qClient.clientId.in(usersId)).transform(GroupBy.groupBy(qClient.clientId).as(getUserProjectionWithRoles()));
        return map.keySet().stream().map(k -> map.get(k)).collect(Collectors.toList());
    }

    @TransactionalSupport
    public User getByLoginWithRoles(String login, boolean onlyActive) {
        SQLQuery query = queryFactory.select(qClient.all()).from(qClient).leftJoin(qClient.__role_clientId_59063A47FK, qRole).where(qClient.login.eq(login));
        if (onlyActive) {
            query.where(qClient.active.isTrue());
        }
        Map<Integer, User> map = (Map<Integer, User>) query.transform(GroupBy.groupBy(qClient.clientId).as(getUserProjectionWithRoles()));
        return map.keySet().stream().map(k -> map.get(k)).findAny().orElseGet(() -> null);
    }

    @TransactionalMandatory
    public long insertUserRoles(Integer userId, Collection<UserRole> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return 0;
        }
        SQLInsertClause insert = queryFactory.insert(qRole);
        roles.stream().forEach(r -> {
            insert.set(qRole.clientId, userId).set(qRole.roleValue, r.name()).addBatch();
        });
        return insert.execute();
    }

    @TransactionalMandatory
    public long updatePassword(Integer userId, String newPassword) {
        return queryFactory.update(qClient).set(qClient.password, newPassword).where(qClient.clientId.eq(userId)).execute();
    }


    private ConstructorExpression<User> getUserProjection() {
        return Projections.constructor(User.class
                , qClient.clientId
                , qClient.login
                , qClient.password
                , qClient.email
                , qClient.comment
                , qClient.name
                , qClient.active
        );
    }

    private ConstructorExpression<User> getUserProjectionWithRoles() {
        return Projections.constructor(User.class
                , qClient.clientId
                , qClient.login
                , qClient.password
                , qClient.email
                , qClient.comment
                , qClient.name
                , qClient.active,
                GroupBy.set(qRole.roleValue)
        );
    }

    private ConstructorExpression<User> getUserProjectionWithRolesAndDeviceCount() {
        return Projections.constructor(User.class
                , qClient.clientId
                , qClient.login
                , qClient.password
                , qClient.email
                , qClient.comment
                , qClient.name
                , qClient.active
                , GroupBy.set(qRole.roleValue)
                , qDeviceClientJunction.deviceId.count()
        );
    }

    private ConstructorExpression<User> getUserProjectionWithRolesAndDeviceIds() {
        return Projections.constructor(User.class
                , qClient.clientId
                , qClient.login
                , qClient.password
                , qClient.email
                , qClient.comment
                , qClient.name
                , qClient.active
                , GroupBy.set(qRole.roleValue)
                , GroupBy.set(qDeviceClientJunction.deviceId)
        );
    }

    @TransactionalSupport
    public User getUserWithDevicesIds(Integer userId) {
        Map<Integer, User> map = queryFactory.select(qClient.all()).from(qClient)
                .leftJoin(qClient.__role_clientId_59063A47FK, qRole)
                .leftJoin(qClient.__deviceCl_clien_4F7CD00DFK, qDeviceClientJunction)
                .where(qClient.clientId.eq(userId))
                .transform(GroupBy.groupBy(qClient.clientId).as(getUserProjectionWithRolesAndDeviceIds()));
        return map.keySet().stream().map(k -> map.get(k)).findAny().orElseGet(() -> null);
    }

    @TransactionalMandatory
    public void deleteUserDeviceMap(Integer userId) {
        queryFactory.delete(qDeviceClientJunction).where(qDeviceClientJunction.clientId.eq(userId)).execute();
    }

    @TransactionalMandatory
    public long insertUserDeviceMap(Integer userId, Collection<Integer> deviceIds) {
        if (CollectionUtils.isEmpty(deviceIds)) {
            return 0L;
        }
        SQLInsertClause insert = queryFactory.insert(qDeviceClientJunction);
        deviceIds.stream().forEach(devId -> {
            insert.set(qDeviceClientJunction.clientId, userId).set(qDeviceClientJunction.deviceId, devId).addBatch();
        });
        return insert.execute();
    }

    @TransactionalMandatory
    public void deleteRoles(Integer userId) {
        queryFactory.delete(qRole).where(qRole.clientId.eq(userId)).execute();
    }

    @TransactionalSupport
    public boolean loginExist(String login) {
        return queryFactory.select(qClient.login).from(qClient).where(qClient.login.eq(login)).fetchCount() != 0;
    }

    @TransactionalSupport
    public boolean emailExist(String email) {
        return queryFactory.select(qClient.email).from(qClient).where(qClient.email.eq(email)).fetchCount() != 0;
    }
}
