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

    $("#id_persona").typeahead({
        onSelect: function (item) {
            $("#id_personaH").val(item.value);
        },
        ajax: {
            url: "AuditoriasController",
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

function consultarAuditoria(id){
    $.ajax({
        type    :"POST",
        url     :"AuditoriasController",
        dataType:"json",
        data    :{id:id, accion:"detalle"},
        success: function(data) {
            console.log(data);
            if(data !== undefined){
                $("#id").val(data.id !== undefined ? data.id : "");
                $("#id_personaH").val(data.id_persona !== undefined ? data.id_persona : "");
                $("#id_persona").val(data.nombrePersona !== undefined ? data.nombrePersona : "");
                $("#fecha_creacion").val(data.fecha_creacion !== undefined ? data.fecha_creacion : "");
                $("#clasificacion").val(data.clasificacion !== undefined ? data.clasificacion : "");
                $("#accionAud").val(data.accionAud !== undefined ? data.accionAud : "");
                $("#descripcion").val(data.descripcion !== undefined ? data.descripcion : "");
            }
        },
        error: function(data){
            console.log(data);
        }
    });
}

function llenarTablaAuditorias(pagina) {
    if (pagina === undefined) {
        pagina = 1;
    }
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var id_personaH = $('#id_personaH').val() !== undefined && $('#id_personaH').val() !== "" ? $('#id_personaH').val() : null;
    if ($('#id_persona').val() === "") {
        id_personaH = null;
    }
    //var id_persona = $('#id_persona').val() !== undefined && $('#id_persona').val() !== "" ? $('#id_persona').val() : null;
    var fecha_creacion = $('#fecha_creacion').val() !== undefined && $('#fecha_creacion').val() !== "" ? $('#fecha_creacion').val() : null;
    var clasificacion = $('#clasificacion').val() !== undefined && $('#clasificacion').val() !== "" ? $('#clasificacion').val() : null;
    var accionAud = $('#accionAud').val() !== undefined && $('#accionAud').val() !== "" ? $('#accionAud').val() : null;
    var descripcion = $('#descripcion').val() !== undefined && $('#descripcion').val() !== "" ? $('#descripcion').val() : null;
    $.ajax({
        type: "POST",
        url: "AuditoriasController",
        dataType: "html",
        data: {accion: "consultar", id: id, id_personaH: id_personaH, fecha_creacion: fecha_creacion, clasificacion: clasificacion, accionAud: accionAud, descripcion: descripcion, pagina: pagina},
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
