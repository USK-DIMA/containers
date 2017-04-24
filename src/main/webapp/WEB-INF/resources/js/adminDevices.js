$(document).ready(function() {
    initDevicesTable();
});

var allUser;

function initDevicesTable(){
    getDevicesTable().DataTable({
        ajax: getContextPath() + "/admin/devices/getAll",
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        iDisplayLength : 6,
        bLengthChange : false,
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
    var initPromise = initDivDeviceUserInfo();
    onePointDeviceMap(device.coordinate, device.name, device.comment);
    $.when(initPromise).then(function() {
        fillDeviseUserMapTable(device.users);
    });
}

function initDivDeviceUserInfo() {
    var promise = $.Deferred();
    if(allUser == undefined) {
        promise = ajaxInitAllUser();
    } else {
        promise = getResolvePromise();
    }
    showDivDeviceInfo(true);
    return promise;
}

function ajaxInitAllUser() {
    var dfd = $.Deferred();
    $.ajax({
        url: getContextPath() + '/admin/users/getAll',
        method: 'GET'
    }).done(function(data) {
        allUser = data;
        dfd.resolve();
    }).fail(function(data) {
      Notify.generate('Не удалось загрузить список пользователей', 'Ошибка', 2);
        dfd.reject();
    });
    return dfd.promise();
}

function fillDeviseUserMapTable(activeUsers) {

     var activeUsersId =  $.map( activeUsers, function( val, i ) {
        return val.id;
    });

    $("#devices-user-map-table").DataTable({
        data: allUser,
         sAjaxDataProp: "",
        bLengthChange: false,
        destroy: true,
        scrollY: "220px",
        scrollCollapse: true,
        autoWidth: false,
        bInfo: false,
        paging: false,
        searching: false,
        bLengthChange : false,
        columns: [
            {
                data: "login",
                render: function ( data, type, row ) {
                    return '<span data-user-id="' + row.id + '">'+data+'</span>';
                }
            },
            {
                data: "fio",
                render: function ( data, type, row ) {
                    return '<span data-user-id="' + row.id + '">'+data+'</span>';
                }
            },
            {
                render: function ( data, type, row ) {
                    var checked = ''
                    if(activeUsersId.includes(row.id)) {
                        checked = ' checked="checked" ';
                    }
                    return '<input data-user-id-for-map="'+row.id+'" type="checkbox"'+checked+'/>';
                },
                 orderable: false
            }
        ]
    });
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
    var usersId = getUsersIdForMap();
    var device = {name: name, comment: comment, usersId: usersId};
    $.ajax({
        url: getContextPath() + '/admin/devices/update/'+ deviceId,
        method: 'POST',
        contentType: "application/json",
        dataType: 'text',
        data: JSON.stringify(device)
    }).done(function(data) {
        updateDeviceTable(deviceId, device);
        Notify.generate('Изменения приняты', 'Успешно', 2);
    }).fail(function(data) {
        Notify.generate('Не удалось обновить информацию об устройстве', 'Ошибка', 2);
    });
}

function getUsersIdForMap() {
    var $userIds = $('#devices-user-map-table').find('input[data-user-id-for-map]').filter(function() {
        return $(this).prop('checked');
    }).map(function(){
        return $(this).attr('data-user-id-for-map');
    });

    var ans = []
    $userIds.each(function(i, v){
        ans[i] = v;
    })

    return ans;
}

function updateDeviceTable(deviceId, device) {
    var table = $("#devices-table").DataTable();
    var currentPage = table.page();
    var tr = $($('[data-device-id="'+deviceId+'"]')[0]).parents('tr');
    var data = table.row(tr).data();
    data.name = device.name;
    data.comment = device.comment;
    data.userCount = device.usersId.length;
    table.row(tr).data(data).draw();
    table.page(currentPage).draw(false)
}
