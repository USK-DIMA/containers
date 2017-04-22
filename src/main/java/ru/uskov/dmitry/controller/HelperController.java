package ru.uskov.dmitry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.uskov.dmitry.service.UserService;

/**
 * Created by Dmitry on 22.04.2017.
 */
@Controller
@RequestMapping(value = "/helper")
public class HelperController {

    private static final Logger logger = LoggerFactory.getLogger(HelperController.class);

    @Autowired
    UserService userService;

/*    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser() {
        userService.saveTestUser();
        return "redirect:admin";
    }*/

}