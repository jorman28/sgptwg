$(document).ready(function() {
    llenarTabla();
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
    google.charts.setOnLoadCallback(pintarGrafica);
});

var datos = [];

function pintarGrafica() {
    if (google.visualization !== undefined) {
        var data = google.visualization.arrayToDataTable(datos);
        var options = {
            'is3D': true,
            'chartArea': {top: 0, width: '100%', height: '100%'}
        };
        var chart = new google.visualization.PieChart(document.getElementById('graficaActividadesPorEstado'));
        chart.draw(data, options);
    }
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
        dataType: "json",
        data: {accion: "consultar", proyecto: proyecto, version: version, persona: persona},
        success: function(data) {
            if (data !== undefined) {
                if (data.html !== undefined) {
                    $('#tablaActividadesPorEstado').html(data.html);
                }
                if (data.estados !== undefined) {
                    datos = data.estados;
                    pintarGrafica();
                }
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
    if ($('#responsable').val() === "") {
        persona = null;
    }
    $.ajax({
        type: "POST",
        url: "ActividadesPorEstadoController",
        dataType: "json",
        data: {accion: "generarReporte", proyecto: proyecto, version: version, persona: persona},
        success: function(data) {
            if (data !== undefined && data.archivo !== undefined) {
                console.log('El reporte ha sido generado con éxito');
                var link = document.createElement('a');
                link.href = 'ActividadesPorEstadoController?accion=obtenerArchivo&archivo=' + data.archivo;
                link.target = '_blank';
                link.click();
            } else {
                alert('Error al generar el reporte');
            }
        },
        error: function() {
            alert('Error al generar el reporte');
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