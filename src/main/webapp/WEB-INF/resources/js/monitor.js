$(document).ready(function(){
    connectToMonitorWebSocket(initContainersTable, updateContainersTable);
     $('[data-toggle="tooltip"]').tooltip();
});

function updateContainersTable(newDate) {
    console.log(newDate);
    for(var i=0; i<newDate.length; i++) {
        let d = newDate[i];
        updateFullnessProgressBar(newDate[i].id, newDate[i].fullness, function(){
            updateContainerTableRor(d);
        });
    }
}

function updateContainerTableRor(rowData) {
    var table = $("#containers-table").DataTable();
    function findDataById(id){
        var datas = table.data();
        for(var i=0; i<datas.length; i++) {
            if(datas[i].id == id) {
                return {data: datas[i], position: i};
            }
        }
    }
    var rd = rowData;
    var currentPage = table.page();
    var dataAndPosition = findDataById(rowData.id);
    var data = dataAndPosition.data;

    data.name = rd.name;
    data.fullness = rd.fullness;
    data.lastModify = rd.lastModify;
    data.containerName = rd.containerName;
    data.dimensions = rd.dimensions;

    table.row(dataAndPosition.position).data(data).draw();


     /*table.row.add( [
                rd.name,
                rd.fullness,
                rd.lastModify,
                rd.containerNa,
                rd.dimensions
            ] ).draw( false );
        }*/
}



function renderFullness(percents, deviceId) {
    var color = getColorProgressBarClass(percents);
    return '<div data-device-id-progress="'+deviceId+'" style="margin-top: 5px" class="progress"><div class="progress-bar" style="background-color:'+color+'; width: '+percents+'%" role="progressbar" aria-valuenow="'+percents+'"aria-valuemin="0" aria-valuemax="100">'+percents+'%</div></div>'
}

/**
    метод устаналвивает новое значение процентов для прогресс бара. Это происходит с анимацией.
    После того, как анимация прошла, надо бы в таблице поменять данные (потому что этот метод тупо отрисоывает новое значение, но в табице в data храниться старое значение)
    поэтому в updateCallback мы должны переписать данные
*/
function updateFullnessProgressBar(deviceId, percents, updateCallback) {
    var color = getColorProgressBarClass(percents);
    var bar = $('[data-device-id-progress="'+deviceId+'"]').children('.progress-bar');
    bar.css('width', percents + '%');
    bar.attr('aria-valuenow', percents)
    bar.text(percents + '%')
    bar.css('background-color', color);

    $("body").on($.support.transition.end, bar, function(event){
        updateCallback();
    });
}


function initContainersTable(){
    var dfd = $.Deferred();
     $("#containers-table").DataTable({
        ajax: getContextPath() + "/monitor/getAll",
        sAjaxDataProp: "",
        iDisplayLength : 12,
        initComplete: function() {
            dfd.resolve();
        },
        pageLength: 12,
        columns: [
            {
                "data": "id",
                "width": "5%",
                "render": function ( data, type, row ) {
                    return '<span data-device-id="'+data+'">'+data+'</span>'
                }
            },
            {
                "data": "name",
                "width": "25%"
            },
            {
                "data": "fullness",
                "width": "30%",
                "render": function ( data, type, row ) {
                    return renderFullness(data, row.id)
                }
            },
            {
                "data": "lastModify",
                "width": "10%"
            },
            {
                "data": "containerName",
                "width": "15%"
            },
            {
                "data": "dimensions",
                "width": "15%"
            }
        ]
    });
    return dfd.promise();
}