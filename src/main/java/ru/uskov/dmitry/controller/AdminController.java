package ru.uskov.dmitry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Dmitry on 09.03.2017.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getAdminPage() {
        return "admin";
    }
}
