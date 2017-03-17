package ru.uskov.dmitry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uskov.dmitry.annotation.TransactionalService;
import ru.uskov.dmitry.dao.ContragentDao;
import ru.uskov.dmitry.entity.Contragent;

import java.util.List;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Service
public class ContragentService {

    @Autowired
    private ContragentDao contragentDao;

    @TransactionalService
    public List<Contragent> getAll() {
        return contragentDao.getAll();
    }

    @TransactionalService
    public void createTest() {
        Contragent contragent = new Contragent();
        contragent.setName("ЖЭК");
        contragent.setAccessKey("123-456-789");
        contragent.setActive(true);
        contragent.setDescription("просто ЖЭК");
        contragentDao.insertContragent(contragent);
    }
}
