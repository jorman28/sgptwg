$(document).ready(function () {
    $('#fecha_creacion').datetimepicker({
        format: 'dd/mm/yyyy',
        language: 'es',
        weekStart: true,
        todayBtn: true,
        autoclose: true,
        todayHighlight: true,
        startView: 2,
        minView: 2});
    llenarTablaAuditorias();

    $("#filtroPersona").typeahead({
        onSelect: function (item) {
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
            preDispatch: function (query) {
                return {accion: "completarPersonas", busqueda: query};
            },
            preProcess: function (data) {
                return data;
            }
        }
    });
});

function llenarTablaAuditorias(pagina) {
    if (pagina === undefined) {
        pagina = 1;
    }
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    //var id_persona = $('#id_persona').val() !== undefined && $('#id_persona').val() !== "" ? $('#id_persona').val() : null;
    var fecha_creacion = $('#fecha_creacion').val() !== undefined && $('#fecha_creacion').val() !== "" ? $('#fecha_creacion').val() : null;
    var clasificacion = $('#clasificacion').val() !== undefined && $('#clasificacion').val() !== "" ? $('#clasificacion').val() : null;
    var accionAud = $('#accionAud').val() !== undefined && $('#accionAud').val() !== "" ? $('#accionAud').val() : null;
    var descripcion = $('#descripcion').val() !== undefined && $('#descripcion').val() !== "" ? $('#descripcion').val() : null;
    var idPersona = $('#idPersona').val() !== undefined && $('#idPersona').val() !== "" ? $('#idPersona').val() : null;
    if ($('#filtroCreador').val() === "") {
        idPersona = null;
    }
    $.ajax({
        type: "POST",
        url: "AuditoriasController",
        dataType: "html",
        data: {accion: "consultar", id: id, idPersona: idPersona, fecha_creacion: fecha_creacion, clasificacion: clasificacion, accionAud: accionAud, descripcion: descripcion, pagina: pagina},
        success: function (data) {
            if (data !== undefined) {
                $('#tablaAuditorias').html(data);
            }
        },
        error: function () {
            console.log('Error al cargar la tabla');
        }
    });
}