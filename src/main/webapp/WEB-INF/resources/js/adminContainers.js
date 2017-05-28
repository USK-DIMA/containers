$(document).ready(function(){
    $("#createContainerButton").click(function(){
        $("#createContainerModalDialog").modal('show');
    });

    initContainerTable();

})



function initContainerTable(){
    $("#containers-table").DataTable({
            ajax: getContextPath() + "/admin/containers/getAll",
            sAjaxDataProp: "",
            iDisplayLength : 12,
            pageLength: 12,
            columns: [
                {
                    "data": "id"
                },
                {
                    "data": "name"
                },
                {
                    "data": "create"
                },
                {
                    "data": "width"
                },
                {
                    "data": "height"
                },
                {
                    "data": "lenght"
                },
                {
                    "data": "deviceCount"
                }
            ]
        });
}


