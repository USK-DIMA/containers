
function initTable(){
    $("#contragent-table").DataTable({
        ajax: getContextPath() + "/admin/service/contragents/getContragentList",
        sAjaxDataProp: "",
        bLengthChange: false,
        searching: false,
        pageLength: 6,
        columns: [
            { "data": "id" },
            { "data": "name" },
            { "data": "usersCount" },
            { "data": "devicesCount" },
            { "data": "active" }
        ]
    });
}

$(document).ready(function(){
    initTable();
});


