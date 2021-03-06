$(document).ready(function() {
    initUserTable();
    var currentUser = getUrlParam('userId');
    loadUserInfo(currentUser);
    $("#createUserButton").click(function(){
        showCreateUserDialog();
    });

    getPassInputs().change(function() {
        validateUserPasswordNewUserForm($("#createUserPassword"), $("#createUserConfirmPassword"));
    });
    $("#createUserLogin").change(function() {
        validateLoginInput($(this));
    });
    $("#createUserEmail").change(function() {
        validateEmailInput($(this));
    });

    $('#createUserModaDialog').on('hidden.bs.modal', function (e) {
        $('#createUserConfirmPassword').popover('hide');
        $('#createUserLogin').popover('hide');
        $('#createUserEmail').popover('hide');
    })


    $('#deleteCurrentUser').click(function(){
        showDeleteCurrentUserDialog();
    });
});

function showDeleteCurrentUserDialog() {
    $("#deleteUserModaDialog").modal('show');
}

function ajaxDeleteCurrentUser(){
    $.ajax({
        url: getContextPath() + '/admin/users/delete',
        method: 'POST',
        data:{
            id: getCurrentUserId()
        }
    }).done(function(data) {
        refreshPage();
    }).fail(function(data) {
        Notify.generate('Не удалось удалить пользователя', 'Ошибка', 2);
    });
}

function initUserTable(){
    $("#users-table").DataTable({
        ajax: getContextPath() + "/admin/users/getAll",
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        iDisplayLength : 6,
        bLengthCchange : false,
        pageLength: 6,
        initComplete: addClickUserTableRowListener,
        columns: [
            {
             "data": "id",
             "render": function ( data, type, row ) {
                        return '<span data-user-id="' + row.id + '">'+data+'</span>';
                     }
            },
            {
             "data": "fio",
             "render": function ( data, type, row ) {
                         return '<span field-name="fio" data-user-id="' + row.id + '">'+data+'</span>';
                      }
             },
            {
             "data": "login",
             "render": function ( data, type, row ) {
                  return '<span field-name="login" data-user-id="' + row.id + '">'+data+'</span>';
               }
             },
            {
             "data": "email",
             "render": function ( data, type, row ) {
                  return '<span field-name="email" data-user-id="' + row.id + '">'+data+'</span>';
               }
             },
            {
                "data": "roles",
                "render": function ( data, type, row ) {
                    if(!Array.isArray(data)) {
                        data = [data];
                    }
                    var render = '<span field-name="roles" data-user-id="' + row.id + '">';
                    render += convertUserRolesToTableData(data);
                    render +='</span>';
                    return render;
                }
            },
            { "data": "deviceCount" },
            {
                "data": "active",
                "render": function(data, type, row) {
                    var checkedHtml = '';
                    if(!data) {
                        return '<span active-state-user-id="'+row.id+'" class="label label-danger">Заблокирован</span>';
                    } else {
                        return '<span active-state-user-id="'+row.id+'" class="label label-success">Активен</span>';
                    }

                }
            }
        ]
    });
}

function addClickUserTableRowListener() {

    $("#users-table tbody").on( 'click', 'tr', function () {
         var userId = $(this).find('[data-user-id]').attr('data-user-id');
         loadUserInfo(userId);
    });
}

function loadUserInfo(userId) {
    if(userId == undefined) {
        return;
    }
    $.ajax({
        url: getContextPath() + "/admin/users/getUserInfo",
        data: {
            userId: userId
        },
        method: 'GET'
    }).done(function(data) {
        fillUserInfo(data);
    });
}

function getCurrentUserId() {
    return $('[data-current-user-id]').attr('data-current-user-id');
}

function fillUserInfo(user) {
    var userId = user.id;
    $("#currentUserId").text(userId);
    $("#currentUserId").attr('data-current-user-id', userId);

    $("#currentUserName").val(user.name);
    $("#currentUserLogin").val(user.login);
    $("#currentUserEmail").val(user.email);
    $("#currentUserComment").val(user.comment);

    var roles = user.roles;
    updateCurrentUserRoleCheckboxes(roles);
    setCurrentUserActive(user.active);
    $("#div-user-info").css('display', 'block');
    updateDeviceTable(user.devices);

}


var allDevices;
function updateDeviceTable(activeDevices) {
    $.when(loadAllDevices()).then(function() {
        var activeDevicesIds = $.map(activeDevices, function(val, i){
             return val.id;
        })

        $("#device-to-users-map-table").DataTable({
            data: allDevices,
            bLengthChange: false,
            destroy: true,
            scrollY: "300px",
            scrollCollapse: true,
            bInfo: false,
            paging: false,
            bAutoWidth: false,
            searching: false,
            bLengthChange : false,
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
                        return '<span data-device-id="' + row.id + '">'+data+'</span>';
                    }
                },
                {
                    data: "comment",
                    render: function ( data, type, row ) {
                        return '<span data-device-id="' + row.id + '">'+data+'</span>';
                    }
                },
                {
                    render: function ( data, type, row ) {
                        var checked = ''
                        if(activeDevicesIds.includes(row.id)) {
                            checked = ' checked="checked" ';
                        }
                        return '<input data-device-id-for-map="'+row.id+'" type="checkbox"'+checked+'/>';
                    },
                    orderable: false
                },
                {
                    data: 'active',
                    render: function(data, type, row) {
                        if(!data) {
                            return '<span class="label label-danger">Заблокирован</span>';
                        } else {
                            return '<span class="label label-success">Активен</span>';
                        }

                    }
                }
            ]
        });
    })
}

function loadAllDevices(){
    if(allDevices == undefined) {
        return ajaxLoadDevices();
    } else {
        return getResolvePromise();
    }
}

function ajaxLoadDevices() {
    var dfd = $.Deferred();
    $.ajax({
        url: getContextPath() + '/admin/devices/getAll',
        method: 'GET',
    }).done(function(data) {
        allDevices = data;
        dfd.resolve();
    }).fail(function(data) {
        Notify.generate('Не удалось загрузить устройства', 'Ошибка', 2);
        dfd.reject();
    });
    return dfd.promise();
}


function updateTableRow(user) {
    var table = $("#users-table").DataTable();
    var currentPage = table.page();
    var userId = user.id;
    var tr = $($('[data-user-id="'+userId+'"]')[0]).parents('tr');
    var data = table.row(tr).data();
    data.email = user.email;
    data.fio = user.name;
    data.login = user.login;
    data.roles = user.roles;
    data.deviceCount = user.deviceId.length;
    table.row(tr).data(data).draw();
    table.page(currentPage).draw(false)
}

function convertUserRolesToTableData(roles) {
    var ans = '';
    if(roles != undefined) {
        if(!Array.isArray(roles)) {
            roles = [roles];
        };
        roles.forEach(function(r, i, a){
            ans += $('[data-role="'+r+'"]').attr('data-role-alter-name') + '\n';
        });
    }
    return ans;
}


function setTableValue(userId, fieldName, value) {
    $('[data-user-id="'+userId+'"][field-name="'+fieldName+'"]').text(value);
}

function updateCurrentUserRoleCheckboxes(roles) {
    var $inputs = getAllCurrentUserRoleCheckboxes();
    $inputs.each(function(){
        var checkRole = $(this).attr('data-role');
        $(this).prop('checked', roles.includes(checkRole));
    });
    $inputs.trigger('change');
}

function saveCurrentUserInfo() {
    var user = {};
    user.id = getCurrentUserId();
    user.name = $("#currentUserName").val();
    user.login = $("#currentUserLogin").val();
    user.email = $("#currentUserEmail").val();
    user.comment = $("#currentUserComment").val();
    user.roles = getCurrentUserSelectedRoles();
    user.deviceId = getCurrentUserSelectedDevices();
    ajaxUpdateUser(user);
}


function getCurrentUserSelectedDevices() {
    var deviceIds = $('#device-to-users-map-table').find('input[data-device-id-for-map]').filter(function(){
        return $(this).prop('checked');
    }).map(function(){
        return $(this).attr('data-device-id-for-map');
    })
    var ans = [];
    deviceIds.each(function(i, v, a){
        ans[i] = v;
    });
    return ans;
}


function ajaxUpdateUser(user) {
    $.ajax({
        url: getContextPath() + "/admin/users/update",
        contentType: "application/json",
        dataType: 'text',
        data: JSON.stringify(user),
        method: 'POST'
    }).done(function(data) {
        updateTableRow(user);
        Notify.generate('Изменения приняты', 'Успешно', 2);
    }).fail(function(data){
        Notify.generate('Выбранны недействительные логин или почта', 'Ошибка обновления', 2);
    });
}

function validateAllCurrentUserFields() {
    var email = $("#currentUserEmail").val();
    var login = $("#currentUserLogin").val();

    return validateEmailInput($("#currentUserEmail")) & validateLoginInput($("#currentUserLogin"));

}

function getCurrentUserSelectedRoles(){
    var arr =  getAllCurrentUserRoleCheckboxes().filter(function(){
        return $(this).prop('checked');
    }).map(function(){
        return $(this).attr('data-role');
    });
    var ans = [];
    for(var i=0 ;i<arr.length; i++){
        ans[i] = arr[i];
    }
    return ans;
}

function getAllCurrentUserRoleCheckboxes() {
    return $("#div-user-info").find('input[name="role"]');
}

function setCurrentUserActive(active) {
    var text=''
    if(active)  {
        text='(Активный)';
        $("#blockedCurrentUser").show();
        $("#activeCurrentUser").hide();
    } else {
        $("#blockedCurrentUser").hide();
        $("#activeCurrentUser").show();
        text='(Заблокирован)';
    }
    $("#currentUserActiveText").text(text);
}

function ajaxSetActiveCurrentUser(active) {
    var userId = getCurrentUserId();
    $.ajax({
        url: getContextPath() + '/admin/users/setActive',
        method: 'POST',
        data: {
            id: userId,
            active: active
        }
    }).done(function(data) {
        setCurrentUserActive(data);
        updateActiveForUserInTable(userId, data);
    });
}

function updateActiveForUserInTable(userId, isActive) {
    var $isActiveSpan = $('[active-state-user-id="'+userId+'"]');
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

function getPassInputs() {
    return $('#createUserPassword, #createUserConfirmPassword');
}

function showCreateUserDialog() {
    $("#createUserModaDialog").modal('show');
}

/*function validateUserPasswordNewUserForm() {
    var pass = $("#createUserPassword").val();
    var confPass = $("#createUserConfirmPassword").val();
    var valid =  pass == confPass;
    if(!valid) {
        $('#createUserConfirmPassword').popover('show');
        getPassInputs().addClass('not-valid-input');
    } else {
        $('#createUserConfirmPassword').popover('hide');
        getPassInputs().removeClass('not-valid-input');
    }
    showPopover($('#createUserConfirmPassword'), !valid);
    setValidClass(getPassInputs(), valid);
    return valid;
}*/

function validateAllFieldsCreateUserForm() {
    return (validateUserPasswordNewUserForm($("#createUserPassword"), $("#createUserConfirmPassword")) & validateEmailInput($("#createUserEmail")) & validateLoginInput($("#createUserLogin"))) == true;
}

/*function setValidClass(element, isValid){
    if(isValid) {
        element.removeClass('not-valid-input');
    } else {
        element.addClass('not-valid-input');
    }
}*/


