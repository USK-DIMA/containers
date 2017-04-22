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
        if (Common.isCurrentUserAdmin()) {
            return "redirect: admin/users";
        }
        if (Common.isCurrentUserManager()) {
            return "redirect: admin/devices";
        }
        return "admin";
    }
}
