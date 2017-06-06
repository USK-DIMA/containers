var DEFAULT_START_POINT_MAP_ZOOM = 12;
var DEFAULT_START_POINT_MAP_CENTER = [55.9826, 37.20781];
var startPointMap;
var startPointLoaded = $.Deferred();
var startPointIntValue;
var startPointPlacemark;
$(document).ready(function() {
    ajaxLoadStartPoint();
    ymaps.ready(function(){
        initStartPointMap();
        initStartGeoPoint();
    })
})


function updateStartPointCoodrinate(coordinate) {
    var x = coordinate[0].toFixed(5);
    var y = coordinate[1].toFixed(5);
    updateStartPointInputs([x, y]);
}

function updateStartPointPosition(coord) {
    startPointPlacemark.geometry.setCoordinates(coord);
}

function updateStartPointInputs(coord) {
    $("#spart-point-x").val(coord[0]);
    $("#spart-point-y").val(coord[1]);
}


function initStartPointMap() {
    startPointMap = new ymaps.Map('start-point-map', {
        center: DEFAULT_START_POINT_MAP_CENTER,
        zoom: DEFAULT_START_POINT_MAP_ZOOM
    });
}


function initStartGeoPoint() {
    $.when(startPointLoaded.promise()).done(function() {
        updateStartPointInputs(startPointIntValue);
        startPointPlacemark = new ymaps.Placemark(startPointIntValue, undefined, {draggable: true});
        startPointPlacemark.events.add('dragend', function (e) {
            updateStartPointCoodrinate(startPointPlacemark.geometry.getCoordinates());
        });
        startPointMap.geoObjects.add(startPointPlacemark);
        $('[id^=spart-point-]').click(function(){
            updateStartPointPosition(getCurrentStartPointCoordinates());
        });
        $('[id^=spart-point-]').change(function(){
            updateStartPointPosition(getCurrentStartPointCoordinates());
        });
    })
}


function resetStartPoint() {
    updateStartPointPosition(startPointIntValue);
    updateStartPointInputs(startPointIntValue);
}

function ajaxLoadStartPoint() {
    $.ajax({
        url: getContextPath() + "/common/startPoint"
    }).done(function(data){
        startPointIntValue = data;
        startPointLoaded.resolve();
    });
}
function getCurrentStartPointCoordinates() {
    var x = $("#spart-point-x").val();
    var y = $("#spart-point-y").val();
    return [x, y];
}


function saveStartPoint() {
    var newCoordinates = getCurrentStartPointCoordinates();
    $.ajax({
        method: 'POST',
        url: getContextPath() + '/admin/settings/startPoint/save',
        data: JSON.stringify(newCoordinates),
        contentType: "application/json",
    }).done(function(data){
        startPointIntValue = data;
        Notify.generate('Изменения сохранены', 'Успешно', 2);
    }).fail(function(){
         Notify.generate('Обратитесь к администратору', 'Ошибка', 2);
    })
}