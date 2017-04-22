function getContextPath(){
    var pathname = window.location.pathname.substr(1);
    return "/" + pathname.substring(0, pathname.indexOf('/'));
}

function showNotification(data) {
    console.log(data);
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

var animationTypeSec = 1;

$(document).ready(function(){
    $('body').find("#notifies").remove();
    $('body').prepend('<div id="notifies" style="z-index: 1000; transition-property: top; transition-duration: '+animationTypeSec+'s; position:fixed; width:auto; height:auto; top: -100px; right:0;">');
});


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