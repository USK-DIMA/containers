package ru.uskov.dmitry.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.uskov.dmitry.controller.form.UserForm;
import ru.uskov.dmitry.controller.webEntity.UserWebEntity;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.exception.IncorrectNewUserException;
import ru.uskov.dmitry.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 11.03.2017.
 */
@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("layoutContent", "fragments/admin/users");
        return "admin";
    }

    @RequestMapping(value = {"/getAll", "/getAll/"}, method = RequestMethod.GET)
    @ResponseBody
    public List<UserWebEntity> getAll() {
        List<User> users = userService.loadAllUsers();
        return users.stream().map(u -> new UserWebEntity(u)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("userForm") User newUser) throws IncorrectNewUserException {
        userService.createUser(newUser);
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "/login/exist/{login}", method = RequestMethod.POST)
    @ResponseBody
    public boolean loginExist(@PathVariable("login") String login) {
        return userService.loginExist(login);
    }

    @RequestMapping(value = "/email/exist", method = RequestMethod.POST)
    @ResponseBody
    public boolean emailExist(@RequestParam("email") String email) {
        return userService.emailExist(email);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void deleteUser(@RequestParam("id") Integer userId) {
        userService.delete(userId);
    }

    @RequestMapping(value = "/setActive", method = RequestMethod.POST)
    @ResponseBody
    public boolean setActive(@RequestParam("id") Integer userId, @RequestParam("active") Boolean active) {
        userService.setActive(userId, active);
        return userService.getUser(userId).getActive();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestBody(required = false) UserForm user) throws IncorrectNewUserException {
        userService.updateUser(user, user.getDeviceId());
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@RequestParam("userId") Integer userId) {
        return userService.getUserWithDevicesIds(userId);
    }

}


