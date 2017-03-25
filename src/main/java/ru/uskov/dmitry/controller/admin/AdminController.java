package ru.uskov.dmitry.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.uskov.dmitry.common.Common;

/**
 * Created by Dmitry on 09.03.2017.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getAdminPage() {
        if (Common.isCurrentUserAdminService()) {
            return "redirect: admin/service/contragents";
        }
        if (Common.isCurrentUserAdminContragent()) {
            return "redirect: admin/contragent/users";
        }
        return "admin";
    }
}
