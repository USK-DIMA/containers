package ru.uskov.dmitry.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Dmitry on 25.03.2017.
 */
@Controller
@RequestMapping("/admin/devices")
public class AdminDeviceController {
    private static final Logger logger = LoggerFactory.getLogger(AdminDeviceController.class);

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("layoutContent", "fragments/admin/devices");
        return "admin";
    }

}