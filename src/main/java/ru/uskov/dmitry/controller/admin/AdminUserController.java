package ru.uskov.dmitry.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.uskov.dmitry.service.ContragentService;
import ru.uskov.dmitry.service.UserService;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Controller
@RequestMapping("/admin/service/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContragentService contragentService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model, @RequestParam(name = "test", required = false) Boolean test) {
/*        List<User> userList = userService.loadAllUsers();
        System.out.println("UserListSize: " + userList.size());

        if (userList != null && userList.size() > 0) {
            System.out.println(userList.get(0).getRoles());
        }


        if (test != null && test) {
            List<Contragent> contragents = contragentService.getAll();
            if (contragents != null && contragents.size() > 0) {
                userService.saveTestUser(contragents.get(0));
            }
        }
        contragentService.createTest();*/
        model.addAttribute("layoutContent", "fragments/admin/users");
        return "admin";
    }

}


