package ru.uskov.dmitry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.uskov.dmitry.common.Common;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.service.SettingsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Uskov Dmitry on 03.03.2017.
 */
@Controller
@RequestMapping(value = {"/"})
public class HomeController extends AbstractController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getRootPage(Model model, Locale locale) {
        User user = Common.getCurrentUser();
        if (user != null) {
            model.addAttribute("disableLoginHeader", true);
        }
        model.addAttribute("pageType", "home");
        return "home";
    }

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/common/startPoint", method = RequestMethod.GET)
    @ResponseBody
    public Double[] getStartPointCoordinate() {
        return settingsService.getStartPoint();
    }

    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
