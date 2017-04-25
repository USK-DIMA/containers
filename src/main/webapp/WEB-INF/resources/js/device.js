$(document).ready(function(){
    initDeviceTable();
});

function initDeviceTable() {
    $("#devices-table").DataTable({
        ajax: getContextPath() + '/devices/getAll',
        sAjaxDataProp: "",
        scrollY: "800px",
        scrollCollapse: true,
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
                    //return '<span  data-device-field-name="name" data-device-id="' + row.id + '">'+data+'</span>';
                    return '<a  style="cursor:pointer;" data-device-field-name="name" data-device-id="' + row.id + '">'+data+'</a>';
                }
            },
            {
                data: "filling",
                render: function ( data, type, row ) {
                    return '<span data-device-field-name="filling" data-device-id="' + row.id + '">'+data+'</span>';
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
        var filling = $('[data-device-field-name="filling"][data-device-id="'+deviceId+'"]').text();
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
}