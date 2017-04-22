package ru.uskov.dmitry.dao;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionaMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Repository
public class UserDao extends AbstractDao {

    @TransactionalSupport
    public List<User> getAll() {
        return getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @TransactionaMandatory
    public void insertUsers(List<User> users) {
        Session session = getCurrentSession();
        users.stream().forEach(u -> session.persist(u));
    }

    @TransactionalSupport
    public List<User> getByFieldEq(Map<String, Object> map) {
        return fillCriteria(map).list();
    }

    @TransactionalSupport
    public Long getByFieldEqCount(Map<String, Object> map) {
        return (Long) fillCriteria(map).setProjection(Projections.rowCount()).uniqueResult();
    }

    @TransactionaMandatory
    public void delete(Long userId) {
        delete(User.class, userId);
    }

    @TransactionaMandatory
    public void setActive(Long userId, Boolean active) {
        Session session = getCurrentSession();
        User user = (User) session.get(User.class, userId);
        user.setActive(active);
        session.saveOrUpdate(user);
    }

    @TransactionaMandatory
    public void update(User user) {
        getCurrentSession().update(user);
    }

    private Criteria fillCriteria(Map<String, Object> map) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        map.keySet().stream().forEach(k -> criteria.add(Restrictions.eq(k, map.get(k))));
        return criteria;
    }

    @TransactionalSupport
    public User getUser(Long userId) {
        return (User) getCurrentSession().get(User.class, userId);
    }
}
