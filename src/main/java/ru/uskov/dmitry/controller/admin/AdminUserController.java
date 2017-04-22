package ru.uskov.dmitry.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.uskov.dmitry.controller.webEntity.UserWebEntity;
import ru.uskov.dmitry.entity.User;
import ru.uskov.dmitry.exception.EmailAlreadyExistException;
import ru.uskov.dmitry.exception.LoginAlreadyExistException;
import ru.uskov.dmitry.service.UserService;

import java.util.ArrayList;
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
/*        List<User> userList = userService.loadAllUsers();
        System.out.println("UserListSize: " + userList.size());

        if (userList != null && userList.size() > 0) {
            System.out.println(userList.get(0).getRoles());
        }


        if (test != null && test) {
            List<Contragent> contragents = contragentService.getAll();
            if (contragents != null && contragents.size() > 0) {
                userService.saveTestUser(contragents.get(0));
            }
        }
        contragentService.createTest();*/
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
    public String createUser(@ModelAttribute("userForm") User newUser) throws LoginAlreadyExistException, EmailAlreadyExistException {
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
    public void deleteUser(@RequestParam("id") Long userId) {
        userService.delete(userId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void setActive(@RequestParam("id") Long userId, @RequestParam("active") Boolean active) {
        userService.setActive(userId, active);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestParam("user") User user) {
        userService.updateUser(user);
    }

    @RequestMapping(value = "/getUser/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }


    @RequestMapping(value = "/devices/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> getUserDevices(@PathVariable("userId") Long userId) {
        return new ArrayList();
    }




}


