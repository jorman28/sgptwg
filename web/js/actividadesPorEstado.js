$(document).ready(function() {
//    llenarTabla();
    $("#responsable").typeahead({
        onSelect: function(item) {
            $("#idPersona").val(item.value);
        },
        ajax: {
            url: "ActividadesPorEstadoController",
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
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(llenarTabla);
});

function drawChart(datos) {
    var data = google.visualization.arrayToDataTable(datos);
    var options = {
        'legend': 'left',
        'is3D': true
    }
    var chart = new google.visualization.PieChart(document.getElementById('graficaActividadesPorEstado'));
    chart.draw(data, options);
}

function llenarTabla() {
    var proyecto = $('#proyecto').val() !== undefined && $('#proyecto').val() !== "0" ? $('#proyecto').val() : null;
    var version = $('#version').val() !== undefined && $('#version').val() !== "0" ? $('#version').val() : null;
    var persona = $('#idPersona').val() !== undefined && $('#idPersona').val() !== "" ? $('#idPersona').val() : null;
    if ($('#responsable').val() === "") {
        persona = null;
    }
    $.ajax({
        type: "POST",
        url: "ActividadesPorEstadoController",
        dataType: "html",
        data: {accion: "consultar", proyecto: proyecto, version: version, persona: persona},
        success: function(data) {
            if (data !== undefined) {
                $('#tablaActividadesPorEstado').html(data);
            }
            drawChart([
                ['Task', 'Hours per Day'],
                ['Work', 11],
                ['Eat', 2],
                ['Commute', 2],
                ['Watch TV', 2],
                ['Sleep', 7]
            ]);
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
    if ($('#responsable').val() === "") {
        persona = null;
    }
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

function consultarVersiones(idProyecto) {
    if (idProyecto === 0 || idProyecto === '0') {
        $("#version").html("<option value='0'>SELECCIONE</option>");
    } else {
        $.ajax({
            type: "POST",
            url: "ActividadesPorEstadoController",
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
}