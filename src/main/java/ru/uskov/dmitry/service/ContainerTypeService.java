package ru.uskov.dmitry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.annotation.TransactionalSupport;
import ru.uskov.dmitry.dao.ContainerTypeDao;
import ru.uskov.dmitry.entity.ContainerType;

import java.util.Date;
import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */
@Service
public class ContainerTypeService {
    private static final Logger logger = LoggerFactory.getLogger(ContainerTypeService.class);

    @Autowired
    ContainerTypeDao containerTypeDao;

    @TransactionalService
    public ContainerType save(ContainerType containerType) {
        containerTypeDao.save(containerType);
        return containerType;
    }

    @TransactionalService
    public ContainerType create(ContainerType containerType) {
        containerType.setCreateDate(new Date());
        containerTypeDao.save(containerType);
        return containerType;
    }


    @TransactionalSupport
    public List<ContainerType> getAll() {
        return containerTypeDao.loadAll();
    }

}