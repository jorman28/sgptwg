var personasProyecto = {};
var clientesSeleccionados = 0;
var empleadosSeleccionados = false;

$(function() {
    $('#fechaInicioProyecto').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $('#fechaInicioVersion').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $('#fechaFinVersion').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $("#participante").typeahead({
        onSelect: function(item) {
            if ($("#persona" + item.value)[0] === undefined) {
                var persona = personasProyecto[item.value];
                var html = pintarPersona(persona);
                if (persona.cargo === 'Cliente') {
                    if (clientesSeleccionados === 0) {
                        $("#clientesProyecto").html(html);
                    } else {
                        $("#clientesProyecto").append(html);
                    }
                    clientesSeleccionados++;
                } else {
                    if (empleadosSeleccionados === 0) {
                        $("#empleadosProyecto").html(html);
                    } else {
                        $("#empleadosProyecto").append(html);
                    }
                    empleadosSeleccionados++;
                }
            }
        },
        ajax: {
            url: "ProyectosController",
            timeout: 500,
            displayField: "nombre",
            valueField: 'id',
            triggerLength: 1,
            items: 10,
            method: "POST",
            preDispatch: function(query) {
                return {search: query, accion: "completarPersonas"};
            },
            preProcess: function(data) {
                for (var i = 0; i < data.length; i++) {
                    var persona = data[i];
                    personasProyecto[persona.id] = persona;
                }
                return data;
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
    $("#participante").val('');
    $("#modalProyectos").modal("show");
    personasProyecto = {};
    clientesSeleccionados = 0;
    empleadosSeleccionados = 0;
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
            personasProyecto = {};
            clientesSeleccionados = 0;
            empleadosSeleccionados = 0;
            if (data !== undefined) {
                $("#idProyecto").val(data.idProyecto !== undefined ? data.idProyecto : "");
                $("#nombreProyecto").val(data.nombreProyecto !== undefined ? data.nombreProyecto : "");
                $("#fechaInicioProyecto").val(data.fechaInicio !== undefined ? data.fechaInicio : "");
                $("#clientesProyecto").html(data.clientesProyecto !== undefined ? pintarListaPersonas(data.clientesProyecto) : 'No se han agregado clientes al proyecto');
                if (data.clientesProyecto !== undefined) {
                    clientesSeleccionados = data.clientesProyecto.length;
                }
                $("#empleadosProyecto").html(data.empleadosProyecto !== undefined ? pintarListaPersonas(data.empleadosProyecto) : 'No se han agregado empleados al proyecto');
                if (data.empleadosProyecto !== undefined) {
                    empleadosSeleccionados = data.empleadosProyecto.length;
                }
                $("#participante").val('');
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

function pintarListaPersonas(listaPersonas) {
    var html = "";
    for (var persona in listaPersonas) {
        html += pintarPersona(persona);
    }
    return html;
}

function pintarPersona(persona) {
    var html = '    <li class="list-group-item" id="persona' + persona.id + '">'
            + '         <div class="row">'
            + '             <input type="hidden" id="idPersona' + persona.id + '" name="idsPersonas" value="' + persona.id + '" />'
            + '             <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">'
            + '                 ' + persona.nombre
            + '             </div>'
            + '             <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">'
            + '                 <span class="glyphicon glyphicon-remove" onclick="eliminarPersona(' + persona.id + ' , \'' + persona.cargo + '\');"></span>'
            + '             </div>'
            + '         </div>'
            + '     </li>';
    return html;
}

function eliminarPersona(idPersona, cargo) {
    $("#persona" + idPersona).remove();
    if (cargo === "Cliente") {
        clientesSeleccionados--;
        if (clientesSeleccionados === 0) {
            $("#clientesProyecto").html('No se han agregado clientes al proyecto');
        }
    } else {
        empleadosSeleccionados--;
        if (empleadosSeleccionados === 0) {
            $("#empleadosProyecto").html('No se han agregado empleados al proyecto');
        }
    }
}