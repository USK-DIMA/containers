package ru.uskov.dmitry.dao;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Repository
public class UserDao extends AbstractDao {

    @TransactionalSupport
    public List<User> getAll() {
        return getAll(User.class);
    }

    @TransactionalMandatory
    public void insertUsers(List<User> users) {
        Session session = getCurrentSession();
        users.stream().forEach(u -> session.persist(u));
    }

    @TransactionalSupport
    public List<User> getByFieldEq(Map<String, Object> map) {
        return fullCriteria(map).list();
    }

    @TransactionalSupport
    public Long getByFieldEqCount(Map<String, Object> map) {
        return (Long) fullCriteria(map).setProjection(Projections.rowCount()).uniqueResult();
    }

    @TransactionalMandatory
    public void delete(Long userId) {
        delete(User.class, userId);
    }

    @TransactionalMandatory
    public void setActive(Long userId, Boolean active) {
        Session session = getCurrentSession();
        User user = (User) session.get(User.class, userId);
        user.setActive(active);
        session.saveOrUpdate(user);
    }

    @TransactionalMandatory
    public void update(User user) {
        getCurrentSession().update(user);
    }

    private Criteria fullCriteria(Map<String, Object> map) {
        return fullCriteria(User.class, map);
    }


    @TransactionalSupport
    public User getUser(Long userId) {
        return (User) getCurrentSession().get(User.class, userId);
    }

    public List<User> getUsers(Set<Long> usersId) {
        return listByIds(User.class, usersId);
    }
}
