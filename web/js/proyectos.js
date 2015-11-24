var personasAgregadas = {};

$(function() {
    $('#fechaInicioProyecto').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $('#fechaInicioVersion').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $('#fechaFinVersion').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $("#participante").typeahead({
        onSelect: function(item) {
            if (personasAgregadas[item.value] === undefined) {
                personasAgregadas[item.value] = item;
            }
        },
        ajax: {
            url: "ProyectosController",
            timeout: 500,
            displayField: "texto",
            valueField: 'valor',
            triggerLength: 1,
            items: 10,
            method: "POST",
            preDispatch: function(query) {
                return {search: query, accion: "completarPersonas"};
            }
        }
    });
});

function nuevoProyecto() {
    $("#idProyecto").val('');
    $("#nombreProyecto").val('');
    $("#fechaInicioProyecto").val('');
    $("#clientesProyecto").html('No se han agregado clientes al proyecto');
    $("#empleadosProyecto").html('No se han agregado empleados al proyecto');
    $("#modalProyectos").modal("show");
    personasAgregadas = {};
}

function nuevaVersion(idProyecto) {
    $("#idProyectoVersion").val(idProyecto);
    $("#nombreVersion").val('');
    $("#estado").val("0");
    $("#fechaInicioVersion").val('');
    $("#fechaFinVersion").val('');
    $("#alcance").val('');
    $("#modalVersiones").modal("show");
}

function editarProyecto(idProyecto) {
    $.ajax({
        type: "POST",
        url: "ProyectosController",
        dataType: "json",
        data: {idProyecto: idProyecto, accion: "editarProyecto"},
        success: function(data) {
            if (data !== undefined) {
                $("#idProyecto").val(data.idProyecto !== undefined ? data.idProyecto : "");
                $("#nombreProyecto").val(data.nombreProyecto !== undefined ? data.nombreProyecto : "");
                $("#fechaInicioProyecto").val(data.fechaInicio !== undefined ? data.fechaInicio : "");
                $("#clientesProyecto").html(data.clientesProyecto !== undefined ? data.clientesProyecto : 'No se han agregado clientes al proyecto');
                $("#empleadosProyecto").html(data.empleadosProyecto !== undefined ? data.empleadosProyecto : 'No se han agregado empleados al proyecto');
                $("#modalProyectos").modal("show");
            }
        },
        error: function() {
        }
    });
}

function editarVersion(idVersion) {
    $.ajax({
        type: "POST",
        url: "ProyectosController",
        dataType: "json",
        data: {idVersion: idVersion, accion: "editarVersion"},
        success: function(data) {
            if (data !== undefined) {
                $("#idProyectoVersion").val(data.idProyecto !== undefined ? data.idProyecto : "");
                $("#idVersion").val(data.idVersion !== undefined ? data.idVersion : "");
                $("#nombreVersion").val(data.nombreVersion !== undefined ? data.nombreVersion : "");
                $("#estado").val(data.estado !== undefined ? data.estado : "0");
                $("#fechaInicioVersion").val(data.fechaInicio !== undefined ? data.fechaInicio : "");
                $("#fechaFinVersion").val(data.fechaFin !== undefined ? data.fechaFin : "");
                $("#alcance").val(data.alcance !== undefined ? data.alcance : "");
                $("#modalVersiones").modal("show");
            }
        },
        error: function() {
        }
    });
}

function eliminarProyecto(idProyecto) {
    $("#tipoEliminacion").val("PROYECTO");
    $("#idProyecto").val(idProyecto);
    $("#confirmationMessage").modal("show");
}

function eliminarVersion(idVersion) {
    $("#tipoEliminacion").val("VERSION");
    $("#idVersion").val(idVersion);
    $("#confirmationMessage").modal("show");
}