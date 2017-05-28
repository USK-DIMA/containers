$(document).ready(function(){
    initDeviceTable();
    $("#newPass, #confirmNewPass").change(function(){
        validateUserPasswordNewUserForm($("#newPass"), $("#confirmNewPass"));
    });
   /* $("#login").change(validateLoginCurrentUser);
    $("#email").change(validateEmailCurrentUser);
    $("#name").change(validateNameCurrentUser);*/
});

function initDeviceTable(){
    $("#devices-table").DataTable({
        ajax: getContextPath() + '/profile/getDevices',
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        bLengthChange : false,
        pageLength: 10,
        "language": {
          "emptyTable": "Активных устройств не обнаружено"
        },
        columns: [
            //{data: 'id'},
            //{data: 'createData'},
            {data: 'name'},
            {data: 'comment'},
            {data: 'modifyData'}
        ]
    });
}

function saveUserInfo(){

    $.ajax({
        url: getContextPath() + '/profile/save',
        method: 'POST',
        contentType: "application/json",
        dataType: 'text',
        data: JSON.stringify({
            name: $("#name").val(),
            login: $("#login").val(),
            email: $("#email").val(),
        })
    }).done(function(data) {
        Notify.generate('Изменения сохранены', 'Успешно', 2);
    }).fail(function(data) {
        Notify.generate('Лоиг или почта уже заняты', 'Ошибка', 2);
    });

}


function validateChangeForm() {
    var passVal =  validatePassChangePassword();
    var newPassVal = validateUserPasswordNewUserForm($("#newPass"), $("#confirmNewPass"));
    return passVal && newPassVal;
}

function validatePassChangePassword() {
    var input  = $("#oldPassword");
    var valid = validatePassword(input.val())
    setValidClassInput(input, valid);
    console.log(valid);
    return valid;
}

function validatePassword(pass) {
    var valid;
    $.ajax({
        url: getContextPath() + '/profile/validatePass',
        method: 'POST',
        async: false,
        data: {
            password: pass,
        }
    }).done(function(data) {
        valid = data;
    }).fail(function(data){
        Notify.generate('Во время проверки пароля произошла ошибка', 'Ошибка', 2);
    });
    return valid;
}


function validateUserForm() {
    var login = validateLoginCurrentUser();
    var name = validateNameCurrentUser();
    var email = validateEmailCurrentUser();
    return login && email && name;
}

function validateLoginCurrentUser() {
    return validateLoginInput($("#login"));
}

function validateNameCurrentUser() {
    var name = $("#name").val();
    validateNameInput($("#name"));
    return name != undefined && name != '';
}

function validateEmailCurrentUser() {
    return validateEmailInput($("#email"));
}

function changePassword() {
    if(validateChangeForm()) {
        $.ajax({
            url: getContextPath() + '/profile/changePassword',
            method: 'POST',
            dataType: 'text',
            contentType: "application/json",
            data: JSON.stringify({
                oldPassword: $("#oldPassword").val(),
                newPassword: $("#newPass").val(),
                confirmPassword: $("#confirmNewPass").val(),
            })
        }).done(function(data) {
            Notify.generate('Изменения сохранены', 'Успешно', 2);
            $("#oldPassword").val('');
            $("#newPass").val('');
            $("#confirmNewPass").val('');
        }).fail(function(data) {
            Notify.generate('Во время сохранения возникли ошибки', 'Ошибка', 2);
        });
    }
}
