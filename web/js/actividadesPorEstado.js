$(document).ready(function() {
    llenarTabla();
});

function llenarTabla() {
    var proyecto = $('#proyecto').val() !== undefined && $('#proyecto').val() !== "0" ? $('#proyecto').val() : null;
    var version = $('#version').val() !== undefined && $('#version').val() !== "0" ? $('#version').val() : null;
    var persona = $('#idPersona').val() !== undefined && $('#idPersona').val() !== "" ? $('#idPersona').val() : null;
    $.ajax({
        type: "POST",
        url: "ActividadesPorEstadoController",
        dataType: "html",
        data: {accion: "consultar", proyecto: proyecto, version: version, persona: persona},
        success: function(data) {
            if (data !== undefined) {
                $('#tablaActividadesPorEstado').html(data);
            }
        },
        error: function() {
            console.log('Error al cargar la tabla');
        }
    });
}

function generarReporte() {
    var proyecto = $('#proyecto').val() !== undefined && $('#proyecto').val() !== "0" ? $('#proyecto').val() : null;
    var version = $('#version').val() !== undefined && $('#version').val() !== "0" ? $('#version').val() : null;
    var persona = $('#idPersona').val() !== undefined && $('#idPersona').val() !== "" ? $('#idPersona').val() : null;
    $.ajax({
        type: "POST",
        url: "ActividadesPorEstadoController",
        dataType: "json",
        data: {accion: "generarReporte", proyecto: proyecto, version: version, persona: persona},
        success: function(data) {
            console.log(data);
        },
        error: function() {
            console.log('Error al cargar la tabla');
        }
    });
}