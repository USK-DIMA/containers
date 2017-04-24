package ru.uskov.dmitry.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.uskov.dmitry.controller.form.DeviceForm;
import ru.uskov.dmitry.controller.webEntity.DeviceWebEntity;
import ru.uskov.dmitry.entity.Device;
import ru.uskov.dmitry.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitry on 25.03.2017.
 */
@Controller
@RequestMapping("/admin/devices")
public class AdminDeviceController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("layoutContent", "fragments/admin/devices");
        return "admin";
    }

    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public List<DeviceWebEntity> getAll() {
        return deviceService.getAll().stream().map(d -> new DeviceWebEntity((d))).collect(Collectors.toList());
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Device get(@RequestParam("id") Long deviceId) {
        Device device = deviceService.get(deviceId);
        device.getUsers().stream().forEach(u -> u.setDevices(null));
        return device;
    }

    @RequestMapping(path = "/update/{deviceId}", method = RequestMethod.POST)
    @ResponseBody
    public void updateDevice(@PathVariable("deviceId") Long deviceId, @RequestBody() DeviceForm deviceForm) {
        deviceService.update(deviceId, deviceForm.getName(), deviceForm.getComment(), deviceForm.getUsersId());
    }

    @RequestMapping(path = "/setActive", method = RequestMethod.POST)
    @ResponseBody
    public void setActive(@RequestParam("deviceId") Long deviceId, @RequestParam("active") Boolean active) {
        deviceService.setActive(deviceId, active);
    }

}