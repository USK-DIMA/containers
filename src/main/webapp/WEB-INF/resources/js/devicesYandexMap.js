$(document).ready(function(){
    ymaps.ready(initMap)
});
//todo наверсти порядок в файле
var defaultZoom = 12;
var isInit = false;
var deviceMap;
var startPointPlacemark;

function createStartPointPlacemark(coordinates) {
    ymaps.ready().done(function() {
            startPointPlacemark = new ymaps.Placemark(coordinates, undefined, { preset: 'islands#blueHomeIcon'});
            printStartPointPlacemark();
            resizeMap(deviceMap);
    });
}

function getStartPoint() {
    return startPointPlacemark;
}


function printStartPointPlacemark() {
    deviceMap.geoObjects.add(startPointPlacemark);
}


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
    if(getStartPoint() != undefined){
        collection.add(getStartPoint());
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

function highlightDevicePoint(deviceId,  highlight) {
    var placemark = currentPoints[deviceId];
    if(placemark == undefined || placemark == null) {
        return;
    }
    if(highlight) {
        //todo выделяем метку
        //placemark.options.set('preset', 'islands#greenIcon');
    } else {
        //todo убираем выделение метки
        //placemark.options.unset('preset');
    }

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
    deviceMap.geoObjects.removeAll();
    var pointCount = Object.keys(currentPoints).length;
    if(pointCount == 0) {
        return; //промис
    }
    var geoPoints = [];
    if(getStartPoint() != undefined){
        var startPoint = getStartPoint();
        geoPoints.push(startPoint.geometry.getCoordinates());
        deviceMap.geoObjects.add(startPoint);
    }
    Object.keys(currentPoints).forEach(function(v, i, a){
        var point = currentPoints[v];
        geoPoints.push(point.geometry.getCoordinates());
        deviceMap.geoObjects.add(point);
    });

    optimisateGeoPoints(geoPoints).done(function(points) {
        console.log('начинаю отрисовку');
        ymaps.route(points).then(function (route) {

            route.getPaths().options.set({strokeColor: '0000ffff', strokeWidth: 2, opacity: 0.9})
            deviceMap.geoObjects.add(route.getPaths());
        }, function (error) {
            alert('Возникла ошибка: ' + error.message);
        });
    })
}




function optimisateGeoPoints(pointForOptimisate) {
    var dfd = $.Deferred();

    getTimeMatrix(pointForOptimisate).done(function(matrix) {
        var targetPoints = pointForOptimisate;
        console.log('начинаю проводить расщёты');
        var easyWay = buildEasyWay(matrix);
        console.log('Расщёты произведены. Кратчаший путь: ' + easyWay);
        var optimisatePoints = []
        easyWay.forEach(function(pointIndex) {
            optimisatePoints.push(targetPoints[pointIndex]);
        })
        optimisatePoints.push(targetPoints[easyWay[0]])
        console.log('передаю управление для отрисовки');

        dfd.resolve(optimisatePoints);
    })

    return dfd.promise();
}


function getTimeMatrix(pointForOptimisate) {
    var dfd = $.Deferred();
    var matrix = [];
    var pointCount = pointForOptimisate.length;
    var matrixSize = pointCount * pointCount;
    var cellLoaded = 0;

    function incrementCellLoaded() {
        cellLoaded++;
        if(cellLoaded == matrixSize) {
            console.log('матрица заполнена. Передаю управление для расчётов');
            dfd.resolve(matrix);
        }
    }


    for(var i = 0; i < pointCount; i++) {
        matrix[i] = [];
        for(var j = 0; j < pointCount; j++) {
            if(i == j) {
                matrix[i][j] = null;
                incrementCellLoaded();
            } else {
                $.when(requestTime(pointForOptimisate[i], pointForOptimisate[j], i, j)).done(function(time, i, j){
                    matrix[i][j] = time;
                    incrementCellLoaded();
                })
            }
        }
    }

    return dfd.promise();
}

function requestTime(p1, p2, rowIndex, columnIndex) {
    var dfd = $.Deferred();
    ymaps.route([p1, p2]).then(function (route) {
        var pp1 = rowIndex;
        var pp2 = columnIndex;
        console.log('Только что получили ответ от Яндкеса. Для точек ' + pp1 + " " + pp2 + " время составило " + route.getJamsTime())
        dfd.resolve(route.getJamsTime(), pp1, pp2);
    }, function (error) {
        alert('Возникла ошибка: ' + error.message);
    });
    return dfd.promise();
}


function cleanMap() {
    deviceMap.geoObjects.removeAll();
    printStartPointPlacemark();
}

