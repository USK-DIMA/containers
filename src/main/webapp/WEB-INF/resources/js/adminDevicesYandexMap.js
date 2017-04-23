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

