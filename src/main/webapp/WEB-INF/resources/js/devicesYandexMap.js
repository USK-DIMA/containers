$(document).ready(function(){
    ymaps.ready(initMap)
});
//todo наверсти порядок в файле
var defaultZoom = 12;
var isInit = false;
var deviceMap;
function initMap() {
    if(isInit){
        return;
    }
    isInit = true;
    deviceMap = new ymaps.Map("map", {
        center: [55.76, 37.64],
        zoom: defaultZoom
    });

}

function onePointDeviceMap(coord, hintContent, balloonContent) {
    ymaps.ready().done(function(){
            removeAllPlacemark(deviceMap);
            addCollection(deviceMap, toCollection(coord, hintContent, balloonContent));
    });
}

function removeAllPlacemark(map) {
    map.geoObjects.removeAll();
}

function toCollection(coord, hintContent, balloonContent) {
    coord = convertStringCoord(coord);
    var collection = new ymaps.GeoObjectCollection();
    collection.add(new ymaps.Placemark(coord, { hintContent: hintContent, balloonContent: balloonContent }));
    return collection;
}

function addCollection(map, collection) {
    if(collection.getLength() == 0 ){
        return;
    }
    map.geoObjects.add(collection);
    if(collection.getLength() == 1 ){
                deviceMap.setCenter(collection.get(0).geometry.getCoordinates());
                deviceMap.setZoom(defaultZoom);
    } else {
        map.setBounds( collection.getBounds() );
    }
}

function convertStringCoord(coord) {
    if(Array.isArray(coord)) {
        return coord;
    }
    var ans = [];
    coord.split(',').forEach(function(v, i, a){
        ans[i] = Number(v);
    });
    return ans;
}

var currentPoints = {};
function setDeviceToMap(deviceInfo, add) {
    if(add) {
        var style = getPlacemarkStyleByPercent(deviceInfo.filling);
        var comment = deviceInfo.comment == undefined || deviceInfo.comment == ''?'(Комментарий отсутсвует)': deviceInfo.comment;
        var placemark = new ymaps.Placemark(convertStringCoord(deviceInfo.coordinate), { hintContent: deviceInfo.filling, balloonContent: comment, iconContent: deviceInfo.name}, {preset: style})
        placemark.properties.iconContent = deviceInfo.name;
        currentPoints[deviceInfo.id] = placemark;
        deviceMap.geoObjects.add(placemark);
    } else {
        var placemark = currentPoints[deviceInfo.id];
        delete currentPoints[deviceInfo.id];
        deviceMap.geoObjects.remove(placemark);
    }
    resizeMap(deviceMap);
}

function resizeMap(map) {
    var geometryObjects = getGeoObjectWithGeometry(map);
    var placemarkCount = geometryObjects.length;

    if(placemarkCount == 0) {
        return;
    }
    if(placemarkCount == 1) {
        var mark = geometryObjects[0];
        deviceMap.setCenter(mark.geometry.getCoordinates());
        deviceMap.setZoom(defaultZoom);
    } else {
        map.setBounds(map.geoObjects.getBounds());
    }
}

function getGeoObjectWithGeometry(map) {
    var objects = map.geoObjects;
    var index = 0;
    var ans = [];
    for(var i = 0; i<objects.getLength(); i++) {
        var o = objects.get(i);
        if(o.geometry != null && o.geometry != undefined) {
            ans[index] = o;
            index++;
        }
    }
    return ans;
}

function getPlacemarkStyleByPercent(percent) {
    if(percent<20) {
        return 'islands#lightBlueStretchyIcon';
    }
    if(percent<50) {
        return 'islands#darkGreenStretchyIcon';
    }
    if(percent<80) {
        return 'islands#darkOrangeStretchyIcon';
    }
    if(percent<90) {
        return 'islands#redStretchyIcon';
    }
    return 'islands#blackStretchyIcon';
}


function buildRows() {
    //currentPoints
    deviceMap.geoObjects.removeAll()
    var pointCount = Object.keys(currentPoints).length;
    if(pointCount == 0) {
        return; //промис
    }
    var geoPoints = [];
    Object.keys(currentPoints).forEach(function(v, i, a){
        var point = currentPoints[v];
        geoPoints[i] = point.geometry.getCoordinates();
        deviceMap.geoObjects.add(point);
    });
    ymaps.route(geoPoints).then(function (route) {
        deviceMap.geoObjects.add(route);
    }, function (error) {
        alert('Возникла ошибка: ' + error.message);
    });
}

function cleanMap() {
    deviceMap.geoObjects.removeAll();
}

