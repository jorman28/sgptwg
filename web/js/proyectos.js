$(function() {
    $('#fechaInicio').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $("#participante").typeahead({
        onSelect: function(item) {
            console.log(item);
        },
        ajax: {
            url: "ProyectosController",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 3,
            method: "POST",
            preDispatch: function(query) {
                return {search: query, accion: "hola"};
            }
        }
    });
});

function nuevoProyecto(){
    $("#idProyecto").val('');
    $("#nombreProyecto").val('');
    $("#fechaInicioProyecto").val('');
    $("#clientesProyecto").html('No se han agregado clientes al proyecto');
    $("#empleadosProyecto").html('No se han agregado empleados al proyecto');
    $("#modalProyectos").modal("show");
}

function nuevaVersion(idProyecto){
    $("#idProyectoVersion").val(idProyecto);
    $("#nombreVersion").val('');
    $("#estado").val('');
    $("#fechaInicioVersion").val('');
    $("#fechaFinVersion").val('');
    $("#alcance").val('');
    $("#modalVersiones").modal("show");
}