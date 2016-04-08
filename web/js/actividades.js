$(document).ready(function() {
    $('#fecha').datetimepicker({
        format: 'dd/mm/yyyy', 
        language: 'es', 
        weekStart: true, 
        todayBtn: true, 
        autoclose: true, 
        todayHighlight: true, 
        startView: 2, 
        minView: 2});
    $("#nombreResponsable").typeahead({
        onSelect: function(item) {
            $("#responsable").val(item.value);
        },
        ajax: {
            url: "ActividadesController",
            timeout: 500,
            displayField: "nombre",
            valueField: 'id',
            triggerLength: 1,
            items: 10,
            method: "POST",
            preDispatch: function(query) {
                return {busqueda: query, accion: "completarPersonas"};
            },
            preProcess: function(data) {
                return data;
            }
        }
    });
    llenarTablaActividades();
});

function consultarActividad(id) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {id: id, accion: "editar"},
        success: function(data) {
            if (data !== undefined) {
                $("#id").val(data.id !== undefined ? data.id : "");
                $("#descripcion").val(data.descripcion !== undefined ? data.descripcion : "");
                //$("#nombre").val(data.nombre !== undefined ? data.nombre : "");
            }
        },
        error: function() {
        }
    });
}

function llenarTablaActividades() {
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var proyecto = $('#proyecto').val() !== undefined && $('#proyecto').val() !== "0" ? $('#proyecto').val() : null;
    var version = $('#version').val() !== undefined && $('#version').val() !== "0" ? $('#version').val() : null;
    var descripcion = $('#descripcion').val() !== undefined && $('#descripcion').val() !== "" ? $('#descripcion').val() : null;
    var estado = $('#estado').val() !== undefined && $('#estado').val() !== "0" ? $('#estado').val() : null;
    var fecha = $('#fecha').val() !== undefined && $('#fecha').val() !== "" ? $('#fecha').val() : null;
    var responsable = $('#responsable').val() !== undefined && $('#responsable').val() !== "" ? $('#responsable').val() : null;
    if ($("#nombreResponsable").val() === undefined || $("#nombreResponsable").val() === '') {
        responsable = '';
        $("#responsable").val('');
    }
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "html",
        data: {accion: "consultar", id: id, proyecto: proyecto, version: version, descripcion: descripcion, estado: estado, fecha: fecha, responsable: responsable},
        success: function(data) {
            if (data !== undefined) {
                $('#tablaActividades').html(data);
            }
        },
        error: function() {
            console.log('Error al cargar la tabla');
        }
    });
}

function consultarVersiones(idProyecto) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {proyecto: idProyecto, accion: "consultarVersiones"},
        success: function(data) {
            if (data !== undefined) {
                var html = "<option value='0'>SELECCIONE</option>";
                for (var version in data) {
                    version = data[version];
                    html += "<option value='" + version.id + "'>" + version.nombre + "</option>";
                }
                $("#version").html(html);
            }
        },
        error: function(err) {
            alert(err);
        }
    });
}
