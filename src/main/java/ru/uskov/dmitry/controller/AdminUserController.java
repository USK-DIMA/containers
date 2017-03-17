package ru.uskov.dmitry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.uskov.dmitry.entity.Contragent;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.service.ContragentService;
import ru.uskov.dmitry.service.UserService;

import java.util.List;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContragentService contragentService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getPage(@RequestParam(name = "test", required = false) Boolean test) {
        List<User> userList = userService.loadAllUsers();
        System.out.println("UserListSize: " + userList.size());

        if (userList != null && userList.size() > 0) {
            System.out.println(userList.get(0).getServiceRoleSet());
        }


        if (test != null && test) {
            List<Contragent> contragents = contragentService.getAll();
            if (contragents != null && contragents.size() > 0) {
                userService.saveTestUser(contragents.get(0));
            }
        }
        contragentService.createTest();

        return "admin/users";
    }

}


