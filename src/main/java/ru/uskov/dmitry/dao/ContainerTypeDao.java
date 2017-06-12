package ru.uskov.dmitry.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.uskov.dmitry.annotation.TransactionalMandatory;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.entity.ContainerType;

import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */
@Repository
public class ContainerTypeDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(ContainerTypeDao.class);

    @TransactionalMandatory
    public void save(ContainerType containerType) {
        //todo
    }

    @TransactionalSupport
    public List<ContainerType> loadAll() {
        //todo
        return null;
    }
}