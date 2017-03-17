package ru.uskov.dmitry.dao;

import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionaMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.Contragent;

import java.util.List;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Repository
public class ContragentDao extends AbstractDao {

    @TransactionalSupport
    public List<Contragent> getAll() {
        return getCurrentSession().createCriteria(Contragent.class).list();
    }

    @TransactionaMandatory
    public void insertContragent(Contragent contragent) {
        getCurrentSession().persist(contragent);
    }
}
