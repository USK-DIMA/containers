package ru.uskov.dmitry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.uskov.dmitry.controller.form.MonitorRequest;
import ru.uskov.dmitry.controller.form.MonitorTableContainer;
import ru.uskov.dmitry.controller.form.MonitorTableForm;
import ru.uskov.dmitry.service.DeviceService;

import java.util.Date;
import java.util.List;

/**
 * Created by Dmitry on 28.05.2017.
 */
@Controller
public class MonitorWebSocketController {


    @Autowired
    private DeviceService deviceService;

    public MonitorWebSocketController() {
        System.out.println("MonitorWebSocketController has created");
    }

    @MessageMapping("/getNew")
    @SendTo("/topic/getNew")
    public MonitorTableContainer getNewForUser(MonitorRequest monitorRequest) throws Exception {
        Long newTimestamp = new Date().getTime();
        List<MonitorTableForm> forms = MonitorController.convert(deviceService.getAllActiveForUser(monitorRequest.getUserId(), new Date(monitorRequest.getTimestamp())));
        return new MonitorTableContainer(newTimestamp, forms);
    }

    @MessageMapping("/init")
    @SendTo("/topic/init")
    public Long initMonitor() throws Exception {
        return new Date().getTime();
    }
}