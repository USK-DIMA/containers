function getContextPath(){
    var pathname = window.location.pathname.substr(1);
    return "/" + pathname.substring(0, pathname.indexOf('/'));
}

function getUrlParamMap() {
    var s1 = location.search.substring(1, location.search.length).split('&'),
        r = {}, s2, i;
    for (i = 0; i < s1.length; i += 1) {
        s2 = s1[i].split('=');
        r[decodeURIComponent(s2[0]).toLowerCase()] = decodeURIComponent(s2[1]);
    }
    return r;
};

function getUrlParam(key) {
    return getUrlParamMap()[key.toLowerCase()];
}

function getResolvePromise() {
    var dfd = $.Deferred();
    dfd.resolve();
    return dfd.promise();
}

function refreshPage(map) {
    var url = window.location.pathname;
    if(map != undefined) {
        var keys = Object.keys(map);
        if(keys.length >0) {
            url+='?';
        }
        var deliver = ''
        keys.forEach(function(v, i, a){
            url+=deliver+v+'='+map[v];
            deliver = '&';
        });
    }
    window.location.href = url;
}


//Отдельны файл валидация
function validateLoginInput(input) {
    var valid = validateLogin(input.val());
    showPopover(input, !valid);
    setValidClassInput(input, valid);
    return valid;
}


function validateEmailInput(input) {
    var valid = validateEmail(input.val());
    showPopover(input, !valid);
    setValidClassInput(input, valid);
    return valid;
}

function validateUserPasswordNewUserForm(pass, confirmPass) {
    //var pass = $("#createUserPassword").val();
    //var confPass = $("#createUserConfirmPassword").val();
    var passValue = pass.val();
    var confPassValue = confirmPass.val();
    var valid =  passValue == confPassValue && passValue != undefined && passValue != '';
    if(!valid) {
        confirmPass.popover('show');
        pass.addClass('not-valid-input');
        confirmPass.addClass('not-valid-input');
    } else {
        confirmPass.popover('hide');
        pass.removeClass('not-valid-input');
        confirmPass.removeClass('not-valid-input');
    }
    showPopover(confirmPass, !valid);
    setValidClassInput(pass, valid);
    setValidClassInput(confirmPass, valid);
    return valid;
}

function setValidClassInput(element, isValid){
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
        setValidClassInput($("#createUserEmail"), valid);
    });
    return valid;
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


function validateNameInput(input) {
    var name = $("#name").val();
    var valid =  name != undefined && name != '';
    setValidClassInput(input, valid);
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


//Отдельный файл notification
var animationTypeSec = 1;
$(document).ready(function(){

    setDataTableLanguage();
    $('body').find("#notifies").remove();
    $('body').prepend('<div id="notifies" style="z-index: 1000; transition-property: top; transition-duration: '+animationTypeSec+'s; position:fixed; width:auto; height:auto; top: -100px; right:0;">');
});

function setDataTableLanguage(){

    $.extend( true, $.fn.dataTable.defaults, {
        "language": {
            "lengthMenu": "Показать _MENU_ строк",
            "zeroRecords": "Не найдено",
            "info": "Страница _PAGE_ из _PAGES_",
            "search": "Поиск",
            "infoEmpty": "Нет доступных данных",
            "loadingRecords": "Загрузка...",
            "paginate": {
                  "previous": "Предыдущая",
                  "next": "Следующая"
                }
                //Loading...
                //emptyTable
            //"infoFiltered": "(filtered from _MAX_ total records)"
        }
    });
}

Notify = {
            TYPE_INFO: 0,
            TYPE_SUCCESS: 1,
            TYPE_WARNING: 2,
            TYPE_DANGER: 3,

            generate: function (aText, aOptHeader, aOptType_int) {
                var lTypeIndexes = [this.TYPE_INFO, this.TYPE_SUCCESS, this.TYPE_WARNING, this.TYPE_DANGER];
                var ltypes = ['alert-info', 'alert-success', 'alert-warning', 'alert-danger'];
                var ltype = ltypes[this.TYPE_INFO];

                if (aOptType_int !== undefined && lTypeIndexes.indexOf(aOptType_int) !== -1) {
                    ltype = ltypes[aOptType_int];
                }

                var lText = '';
                if (aOptHeader) {
                    lText += "<h4>"+aOptHeader+"</h4>";
                }
                lText += "<p>"+aText+"</p>";
                var lNotify_e = $("<div class='alert "+ltype+"'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>×</span></button>"+lText+"</div>");

                setTimeout(function () {
                    //lNotify_e.alert('close');
                    $("#notifies")[0].style.top = '-100px';
                    setTimeout(function () {
                        lNotify_e.alert('close');
                    }, animationTypeSec*1000);
                }, 3000);
                lNotify_e.appendTo($("#notifies"));
                $("#notifies")[0].style.top = '50px';
            }
        };