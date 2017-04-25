package ru.uskov.dmitry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.uskov.dmitry.controller.webEntity.DeviceWebEntity;
import ru.uskov.dmitry.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 25.03.2017.
 */

@Controller
@RequestMapping(value = "/devices")
public class DeviceController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;


    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        return "devices";
    }

    @RequestMapping(path = "getAll", method = RequestMethod.GET)
    @ResponseBody
    public List<DeviceWebEntity> getDevices() {
        return deviceService.getAllActiveForCurrentUser().stream().map(d -> new DeviceWebEntity(d)).collect(Collectors.toList());
    }


}