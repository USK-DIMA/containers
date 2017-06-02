package ru.uskov.dmitry.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.uskov.dmitry.controller.form.GeneralSettings;
import ru.uskov.dmitry.service.SettingsService;

/**
 * Created by Dmitry on 02.06.2017.
 */
@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/settings")
public class AdminSettingsController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSettingsController.class);

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("layoutContent", "fragments/admin/settings");
        model.addAttribute("settingsForm", settingsService.loadGenerateSettings());
        return "admin";
    }

    @RequestMapping(value = {"/save", "/save/"}, method = RequestMethod.POST)
    public String getPage(GeneralSettings settings) {
        settingsService.saveSettings(settings);
        return "redirect:/admin/settings";
    }


    @RequestMapping(value = "/startPoint/save", method = RequestMethod.POST)
    @ResponseBody
    public Double[] saveStartPointCoordinate(@RequestBody Double[] coordinates) {
        settingsService.saveStartPointCoordinate(coordinates);
        return coordinates;
    }


}