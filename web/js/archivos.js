$(document).ready(function() {
    llenarTabla();
    $('#filtroFecha').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $("#filtroCreador").typeahead({
        onSelect: function(item) {
            $("#idPersona").val(item.value);
        },
        ajax: {
            url: "ArchivosController",
            timeout: 500,
            displayField: "nombre",
            valueField: 'id',
            triggerLength: 1,
            items: 10,
            method: "POST",
            preDispatch: function(query) {
                return {accion: "completarPersonas", busqueda: query};
            },
            preProcess: function(data) {
                return data;
            }
        }
    });
});

function nuevoArchivo() {
    $("#id").val("");
    $("#nombre").val("");
    $("#descripcion").val("");
    $("#fechaCreacion").val($("#hiddenFecha").val());
    $("#creador").val($("#hiddenCreador").val());
    $("#divArchivo").show();
    $("#divDescarga").hide();
    $("#modalArchivos").modal("show");
}

function llenarTabla() {
    var contiene = $('#filtroContiene').val() !== undefined && $('#filtroContiene').val() !== "" ? $('#filtroContiene').val() : null;
    var fecha = $('#filtroFecha').val() !== undefined && $('#filtroFecha').val() !== "" ? $('#filtroFecha').val() : null;
    var idPersona = $('#idPersona').val() !== undefined && $('#idPersona').val() !== "" ? $('#idPersona').val() : null;
    if ($('#filtroCreador').val() === "") {
        idPersona = null;
    }
    $.ajax({
        type: "POST",
        url: "ArchivosController",
        dataType: "html",
        data: {accion: "consultar", filtroContiene: contiene, filtroFecha: fecha, idPersona: idPersona},
        success: function(data) {
            if (data !== undefined) {
                $('#tablaArchivos').html(data);
            }
        },
        error: function() {
        }
    });
}

function consultarArchivo(idArchivo) {
    $.ajax({
        type: "POST",
        url: "ArchivosController",
        dataType: "json",
        data: {accion: "editar", id: idArchivo},
        success: function(data) {
            if (data !== undefined) {
                $("#id").val(data.id !== undefined ? data.id : "");
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");
                $("#descripcion").val(data.descripcion !== undefined ? data.descripcion : "");
                $("#creador").val(data.persona !== undefined ? data.persona : "");
                $("#fechaCreacion").val(data.fecha !== undefined ? data.fecha : "");
                $("#divArchivo").hide();
                $("#divDescarga").html(data.archivo);
                $("#divDescarga").show();
                $("#modalArchivos").modal("show");
            }
        },
        error: function() {
        }
    });
}

function cargarArchivo(objetoArchivo) {
    var archivo = objetoArchivo.files[0];
    $("#nombreArchivo").val(archivo.name);
}

function descargarArchivo(archivo) {
    var link = document.createElement('a');
    link.href = 'ArchivosController?accion=obtenerArchivo&nombreArchivo=' + archivo;
    link.target = '_blank';
    link.click();
}