$(document).ready(function(){
    initDeviceTable();
});

function getDeviceTableHeight() {
    $(window).height() - $("#devices-table").offset().top  - $("#devices-table").height() - 35 - 25 + "px";
}

function initDeviceTable() {
    var tableHeight = getDeviceTableHeight();
    console.log(tableHeight);
    $("#devices-table").DataTable({
        ajax: getContextPath() + '/devices/getAll',
        sAjaxDataProp: "",
        scrollY: tableHeight,
        scrollCollapse: true,
        autoWidth: true,
        bInfo: false,
        paging: false,
        initComplete: addTableCheckboxListener,
        language: {
          "emptyTable": "Активных устройств не обнаружено"
        },
        columns: [
            {
                data: "id",
                render: function ( data, type, row ) {
                    return '<span data-device-id="' + row.id + '">'+data+'</span>';
                }
            },
            {
                data: "name",
                render: function ( data, type, row ) {
                    return '<a  style="cursor:pointer;" data-device-field-name="name" data-device-id="' + row.id + '">'+data+'</a>';
                }
            },
            {
                data: "filling",
                render: function ( data, type, row ) {
                    var backgroundColor = getColorProgressBarClass(data);
                    return '<span class="label" data="'+data+'" style="background-color: '+backgroundColor+'" data-device-field-name="filling" data-device-id="' + row.id + '">'+data+'%</span>';
                }
            },
            {
                data: "modifyData",
                render: function ( data, type, row ) {
                    return '<span data-device-id="' + row.id + '">'+data+'</span>';
                }
            },
            {
                render: function ( data, type, row ) {
                    return '<input comment="'+row.comment +'" data-device-coordinate="' + row.coordinates + '" data-device-id="'+row.id+'" type="checkbox"/>';
                },
                orderable: false
            },
        ]
    });
}

function addTableCheckboxListener() {
    $("#devices-table").find('input[data-device-coordinate]').change(function(){
        var coordinate = $(this).attr('data-device-coordinate');
        var deviceId = $(this).attr('data-device-id');
        var checked = $(this).prop('checked');
        var filling = $('[data-device-field-name="filling"][data-device-id="'+deviceId+'"]').attr('data');
        console.log(filling);
        var name = $('[data-device-field-name="name"][data-device-id="'+deviceId+'"]').text();
        var comment = $(this).attr('comment');
        setDeviceToMap({name: name, filling: filling, id: deviceId, coordinate: coordinate, comment: comment}, checked)
    });
    $("#show-add-devices").change(function() {
        var checked = $(this).prop('checked');
        var tableInputs = $("#devices-table").find('input[data-device-coordinate]');
        tableInputs.prop('checked', checked);
        tableInputs.trigger('change');
    });

    $('[data-device-field-name="name"]').click(function() {
        var deviceId = $(this).attr('data-device-id');
        ajaxLoadDeviceInfo(deviceId);
    });
}


function ajaxLoadDeviceInfo(deviceId) {
    $.ajax({
        url: getContextPath() + '/devices/get',
        method: 'GET',
        data: {
            id: deviceId
        }
    }).done(function(data) {
        fillDeviceInfo(data);
        console.log(data);
    }).fail(function(data) {
        Notify.generate('Не удалось загрузить информацию об устрйостве', 'Ошибка', 2);
    });
}

function clearDeviceMap() {
    var allCheck = $("#show-add-devices");
    allCheck.prop('checked', false).trigger('change');
    cleanMap();
}
function fillDeviceInfo(device) {
    $("#name").val(device.name);
    $("#comment").val(device.comment);
    $("#coordinate").val(device.coordinate);
    $("#deviceId").text(device.id);
    setProgressBar(device.filling);
    $("#divDeviceInfo").css('display', 'block');
}


function setProgressBar(percents) {
    var bar = $("#fillingProgressBar");
    bar.attr('aria-valuenow', percents);
    bar.css('width', percents + '%');
    bar.css('background-color', getColorProgressBarClass(percents));
    bar.text(percents+'%');
}

function getColorProgressBarClass(percent) {
    if(percent<20) {
            return '#5bc0de';
        }
        if(percent<50) {
            return '#5cb85c';
        }
        if(percent<80) {
            return '#f0ad4e';
        }
        if(percent<90) {
            return '#d9534f';
        }
        return '#000000';
}