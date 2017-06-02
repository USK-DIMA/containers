package ru.uskov.dmotry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dmitry on 01.06.2017.
 */
public class RequestMain {
    private static final Logger logger = LoggerFactory.getLogger(RequestMain.class);

    public static void main(String[] args) throws JsonProcessingException {
        Request request = new Request();
        request.setDevices(generateRequestDevices());
        request.setContainers(generateRequestContainers());

        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(request));
    }

    private static List<RequestContentType> generateRequestContainers() {
        RequestContentType cont = new RequestContentType();
        cont.setType_id(3L);
        cont.setDepth(1500);
        cont.setLength(700);
        cont.setHeight(500);
        cont.setMaterial("Пластик");
        cont.setName("Контейнер упрочненный «ТБО-0,8 м3» ");
        return Collections.singletonList(cont);
    }

    private static List<RequestDevice> generateRequestDevices() {
        RequestDevice device = new RequestDevice();
        device.setDevice_id(1L);
        device.setFullness(new Byte(String.valueOf(57)));
        device.setContainer_type_id(3L);
        device.setCreatin_date("20.03.2017  13:12:41");
        device.setCoordinates("55.76, 37.517");
        device.setLast_modify("24.03.2017  16:31:46");
        return Collections.singletonList(device);
    }

}