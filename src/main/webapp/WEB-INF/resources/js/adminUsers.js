function initUserTable(){
    $("#users-table").DataTable({
        ajax: getContextPath() + "/admin/users/getAll",
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        iDisplayLength : 6,
        bLengthChange : false,
        pageLength: 6,
        columns: [
            { "data": "id" },
            { "data": "fio" },
            { "data": "login" },
            { "data": "email" },
            {
                "data": "roles",
                "render": function ( data, type, row ) {
                    if(!Array.isArray(data)) {
                        data = [data];
                    }
                    var render = '';
                    data.forEach(function(v, i, a){
                        if(v.name !== undefined) {
                        render +=v.name +'\n';
                        } else{
                            render +=v +'\n';
                        }
                    });
                    return render;
                }
            },
            { "data": "deviceCount" },
            {
                "data": "active",
                "render": function(data, type, row) {
                    var checkedHtml = '';
                    if(!data) {
                        return '<span class="label label-danger">Заблокирован</span>';
                    } else {
                        return '<span class="label label-success">Активен</span>';
                    }

                }
            }
        ]
    });
}

function initDeviceTable() {
    $("#users-table2").DataTable({
        ajax: getContextPath() + "/admin/users/getAll",
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        pageLength: 7,
        columns: [
            { "data": "id" },
            { "data": "fio" },
            { "data": "login" },
            { "data": "email" }
        ]
    });
}

$(document).ready(function() {
    initUserTable();
    initDeviceTable();
    $("#createUserButton").click(function(){
        showCreateUserDialog();
    });

    getPassInputs().change(function() {
        validateUserPasswordNewUserForm();
    });
    $("#createUserLogin").change(function() {
        validateLoginNewUserForm();
    });
    $("#createUserEmail").change(function() {
        validateEmailNewUserForm();
    });

    $('#createUserModaDialog').on('hidden.bs.modal', function (e) {
        $('#createUserConfirmPassword').popover('hide');
        $('#createUserLogin').popover('hide');
        $('#createUserEmail').popover('hide');
    })
});

function validateLoginNewUserForm() {
    var input = $("#createUserLogin");
    var valid = validateLogin(input.val());
    showPopover(input, !valid);
    setValidClass(input, valid);
}

function validateEmailNewUserForm() {
    var input = $("#createUserEmail");
    var valid = validateEmail(input.val());
    showPopover(input, !valid);
    setValidClass(input, valid);
}

function getPassInputs() {
    return $('#createUserPassword, #createUserConfirmPassword');
}

function showCreateUserDialog() {
    $("#createUserModaDialog").modal('show');
}

function validateUserPasswordNewUserForm() {
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
}

/**
   Показывает на элементе Popover, если show = true,
   иначе скрывает
*/
function showPopover(element, show) {
    if(show) {
        element.popover('show');
    } else {
        element.popover('hide');
    }
}

function setValidClass(element, isValid){
    if(isValid) {
        element.removeClass('not-valid-input');
    } else {
        element.addClass('not-valid-input');
    }
}

function validateLogin(login) {
    if(login == '' || login == undefined){
        return false;
    }
    var valid = false;
    $.ajax({
        url: getContextPath() + "/admin/users/login/exist/" + login,
        method: 'POST',
        async: false
    }).done(function(data) {
        valid = !data;
    });
    return valid;
}

function validateEmail(email) {
    var valid = false;
    $.ajax({
        url: getContextPath() + "/admin/users/email/exist",
        data: {
            email: email
        },
        method: 'POST',
        async: false
    }).done(function(data) {
        valid = !data;
        showPopover($("#createUserEmail"), !valid);
        setValidClass($("#createUserEmail"), valid);
    });
    return valid;
}


function validateAllFieldsCreateUserForm() {
    return validateUserPasswordNewUserForm() & validateEmailNewUserForm() & validateLoginNewUserForm();
}
