package ru.uskov.dmitry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Created by Dmitry on 09.03.2017.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getLoginPage(Model model, Locale locale) {
        model.addAttribute("login", messageSource.getMessage("login", null, locale));
        return "login";
    }
}
