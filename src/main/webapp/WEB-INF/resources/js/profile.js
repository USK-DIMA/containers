$(document).ready(function(){
    initDeviceTable();
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