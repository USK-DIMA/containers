package ru.uskov.dmitry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.uskov.dmitry.common.Common;
import ru.uskov.dmitry.controller.form.ChangePasswordForm;
import ru.uskov.dmitry.controller.form.UserProfileForm;
import ru.uskov.dmitry.controller.webEntity.DeviceWebEntity;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.exception.ConfirmPasswordException;
import ru.uskov.dmitry.exception.IncorrectNewUserException;
import ru.uskov.dmitry.exception.IncorrectPasswordException;
import ru.uskov.dmitry.service.DeviceService;
import ru.uskov.dmitry.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 25.03.2017.
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    DeviceService deviceService;

    @NotNull
    @Autowired
    UserService userService;

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        User user = userService.getUser(Common.getCurrentUser().getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }


    @RequestMapping(path = "/getDevices", method = RequestMethod.GET)
    @ResponseBody
    public List<DeviceWebEntity> getDevices() {
        return deviceService.getAllActiveForCurrentUser().stream().map(d -> new DeviceWebEntity(d)).collect(Collectors.toList());
    }

    @RequestMapping(path = "/validatePass", method = RequestMethod.POST)
    @ResponseBody
    public boolean validatePass(@RequestParam("password") String password) {
        return userService.validUserPassword(Common.getCurrentUser().getId(), password);
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public void changePassword(@RequestBody ChangePasswordForm changePasswordForm) throws ConfirmPasswordException, IncorrectPasswordException {
        userService.changePassword(Common.getCurrentUser().getId(), changePasswordForm);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void saveUserInfo(@RequestBody UserProfileForm userForm) throws IncorrectNewUserException {
        userService.updateCurrentUser(userForm.getLogin(), userForm.getEmail(), userForm.getName());
    }

}