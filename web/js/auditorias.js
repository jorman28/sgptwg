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
});

var personas = {};
jQuery(function () {
    $("#id_persona")
            .typeahead({
                onSelect: function (item) {
                    var persona = personas[item.value];
                    $('#id_persona').val(persona.id);
                },
                ajax: {
                    url: "AuditoriasController",
                    timeout: 500,
                    displayField: "nombre",
                    valueField: 'id',
                    triggerLength: 1,
                    items: 10,
                    method: "POST",
                    preDispatch: function (nombrePerson) {
                        return {nombrePerson: nombrePerson, accion: "consultarPersonas"};
                    },
                    preProcess: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            var persona = data[i];
                            personas[persona.id] = persona;
                        }
                        return data;
                    }
                }
            });
});

function llenarTablaAuditorias() {
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var id_persona = $('#id_persona').val() !== undefined && $('#id_persona').val() !== "" ? $('#id_persona').val() : null;
    var fecha_creacion = $('#fecha_creacion').val() !== undefined && $('#fecha_creacion').val() !== "" ? $('#fecha_creacion').val() : null;
    var clasificacion = $('#clasificacion').val() !== undefined && $('#clasificacion').val() !== "" ? $('#clasificacion').val() : null;
    var accionAud = $('#accionAud').val() !== undefined && $('#accionAud').val() !== "" ? $('#accionAud').val() : null;
    var descripcion = $('#descripcion').val() !== undefined && $('#descripcion').val() !== "" ? $('#descripcion').val() : null;
    $.ajax({
        type: "POST",
        url: "AuditoriasController",
        dataType: "html",
        data: {accion: "consultar", id: id, id_persona: id_persona, fecha_creacion: fecha_creacion, clasificacion: clasificacion, accionAud: accionAud, descripcion: descripcion},
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