package ru.uskov.dmitry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.uskov.dmitry.common.Common;
import ru.uskov.dmitry.controller.form.MonitorTableForm;
import ru.uskov.dmitry.entity.Device;
import ru.uskov.dmitry.service.DeviceService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 27.05.2017.
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private DeviceService deviceService;


    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("userId", Common.getCurrentUser().getId());
        return "monitor";
    }


    @RequestMapping(path = {"/getAll", "/getAll/"}, method = RequestMethod.GET)
    @ResponseBody
    public List<MonitorTableForm> getAll() {
        return convert(deviceService.getAllActiveForCurrentUser());
    }


    @RequestMapping(path = "/update/device", method = RequestMethod.GET)
    @ResponseBody
    public void updateDevice(@RequestParam("deviceId") Long deviceId, @RequestParam("fullness") Integer fullness) {
        deviceService.updateTest(deviceId, fullness);
    }


    public static List<MonitorTableForm> convert(Collection<Device> devices) {
        return devices.stream().map(d -> new MonitorTableForm(d)).collect(Collectors.toList());
    }

}