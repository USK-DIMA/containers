$(document).ready(function() {
    initDevicesTable();
});

function initDevicesTable(){
    getDevicesTable().DataTable({
        ajax: getContextPath() + "/admin/devices/getAll",
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        iDisplayLength : 6,
        bLengthChange : false,
        autoWidth: false,
        pageLength: 6,
        initComplete: addClickDeviceTableRowListener,
        columns: [
            {
             "data": "id",
             "render": function ( data, type, row ) {
                        return '<span data-device-id="' + row.id + '">'+data+'</span>';
                     }
            },
            {
             "data": "createData",
             "render": function ( data, type, row ) {
                         return '<span field-name="createData" data-device-id="' + row.id + '">'+data+'</span>';
                      }
             },
            {
             "data": "name",
             "render": function ( data, type, row ) {
                  return '<span field-name="name" data-device-id="' + row.id + '">'+data+'</span>';
               }
             },
             {
              "data": "comment",
              "render": function ( data, type, row ) {
                   return '<span field-name="comment" data-device-id="' + row.id + '">'+data+'</span>';
                }
              },
            {
             "data": "userCount",
             "render": function ( data, type, row ) {
                  return '<span field-name="userCount" data-device-id="' + row.id + '">'+data+'</span>';
               }
             },
            {
                "data": "modifyData",
                "render": function ( data, type, row ) {
                    return '<span field-name="modifyData" data-device-id="' + row.id + '">' + data + '</span>';
                }
            },
            {
                "data": "active",
                "render": function(data, type, row) {
                    var checkedHtml = '';
                    if(!data) {
                        return '<span active-state-device-id="'+row.id+'" class="label label-danger">Заблокирован</span>';
                    } else {
                        return '<span active-state-device-id="'+row.id+'" class="label label-success">Активен</span>';
                    }
                }
            }
        ]
    });
}

function getDevicesTable() {
    return $("#devices-table");
}

function addClickDeviceTableRowListener() {
    $("#devices-table tbody").on( 'click', 'tr', function () {
         var deviceId = $(this).find('[data-device-id]').attr('data-device-id');
         loadDeviceInfo(deviceId);
    });
}

function loadDeviceInfo(deviceId) {
    $.ajax({
        url: getContextPath() + '/admin/devices/get',
        data: {
            id: deviceId
        }
    }).done(function(data) {
        fillDeviceInfo(data);
    }).fail(function(data){
        Notify.generate('Не удалось загрузить информацию об устройстве', 'Ошибка', 2);
    });
}

function fillDeviceInfo(device) {
    if(device == undefined) {
        return;
    }
    var deviceId = device.id;
    setCurrentDeviceId(deviceId);
    setCurrentDeviceActive(deviceId, device.active);
    $("#deviceName").val(device.name);
    $("#deviceComment").val(device.comment);
    $("#deviceCoordinate").val(device.coordinate);
    showDivDeviceInfo(true);
    onePointDeviceMap(device.coordinate, device.name, device.comment);
}

function showDivDeviceInfo(show) {
    if(show){
        $("#div-device-info").css('display', 'block');
    } else{
        $("#div-device-info").css('display', 'none');
    }
}

function setCurrentDeviceId(deviceId) {
    var $span = $("#currentDeviceId");
    $span.text(deviceId);
    $span.attr('data-current-device-id', deviceId);
}

function setCurrentDeviceActive(deviceId, active) {
    if(getCurrentDeviceId() != deviceId) {
        return;
    }
    var text = ''
    if(active) {
        text = '(Активный)';
        $("#blockedCurrentDevice").show();
        $("#activeCurrentUser").hide();
    } else {
        $("#blockedCurrentDevice").hide();
        $("#activeCurrentUser").show();
        text = '(Заблокирован)';
    }
    $("#currentDeviceActiveText").text(text);
}

function getCurrentDeviceId() {
    return $('#currentDeviceId').attr('data-current-device-id');
}

function ajaxSetActiveCurrentDevice(active) {
 var deviceId = getCurrentDeviceId();
 $.ajax({
    url: getContextPath() + '/admin/devices/setActive',
    method: 'POST',
    data: {
        deviceId: deviceId,
        active: active
    }
 }).done(function(data) {
      setCurrentDeviceActive(deviceId, active);
      updateActiveForDeviceInTable(deviceId, active);
 }).fail(function(data) {
      Notify.generate('Не удалось сменить статус устрйоства', 'Ошибка', 2);
 })
}

function updateActiveForDeviceInTable(userId, isActive) {
    var $isActiveSpan = $('[active-state-device-id="'+userId+'"]');
    var oldClass;
    var newClass;
    var text;
    if(isActive) {
        newClass = 'label-success';
        oldClass = 'label-danger';
        text = 'Активeн';
    } else {
        oldClass = 'label-success';
        newClass = 'label-danger';
        text = 'Заблокирован';
    }
    $isActiveSpan.removeClass(oldClass);
    $isActiveSpan.addClass(newClass);
    $isActiveSpan.text(text);
}

function saveCurrentDeviceInfo() {
    var deviceId = getCurrentDeviceId();
    var name = $("#deviceName").val();
    var comment = $("#deviceComment").val();
    var device = {name: name, comment: comment};
    $.ajax({
        url: getContextPath() + '/admin/devices/update/'+ deviceId,
        method: 'POST',
        contentType: "application/json",
        dataType: 'text',
        data: JSON.stringify(device)
    }).done(function(data) {
        updateDeviceTable(deviceId, device);
    }).fail(function(data) {
        Notify.generate('Не удалось обновить информацию об устройстве', 'Ошибка', 2);
    });
}

function updateDeviceTable(deviceId, device) {
    var table = $("#devices-table").DataTable();
    var currentPage = table.page();
    var tr = $($('[data-device-id="'+deviceId+'"]')[0]).parents('tr');
    var data = table.row(tr).data();
    data.name = device.name;
    data.comment = device.comment;
    table.row(tr).data(data).draw();
    table.page(currentPage).draw(false)
}
