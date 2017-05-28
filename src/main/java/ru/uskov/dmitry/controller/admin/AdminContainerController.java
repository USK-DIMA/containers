package ru.uskov.dmitry.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.uskov.dmitry.controller.form.ContainerTypeForm;
import ru.uskov.dmitry.entity.ContainerType;
import ru.uskov.dmitry.service.ContainerTypeService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 27.05.2017.
 */

@Controller
@RequestMapping("/admin/containers")
public class AdminContainerController {
    private static final Logger logger = LoggerFactory.getLogger(AdminContainerController.class);

    @Autowired
    ContainerTypeService containerTypeService;

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("layoutContent", "fragments/admin/containers");
        return "admin";
    }

    @RequestMapping(path = {"/getAll", "/getAll/"}, method = RequestMethod.GET)
    @ResponseBody
    public List<ContainerTypeForm> getAll() {
        return containerTypeService.getAll().stream().map(t -> new ContainerTypeForm(t)).collect(Collectors.toList());
    }

    @RequestMapping(path = {"/save", "/save/"}, method = RequestMethod.POST)
    @ResponseBody
    public void save(ContainerType containerType) {
        containerTypeService.save(containerType);
    }


    @RequestMapping(path = {"/create", "/create/"}, method = RequestMethod.POST)
    public String create(ContainerType containerType) {
        containerTypeService.create(containerType);
        return "redirect:/admin/containers";
    }


}