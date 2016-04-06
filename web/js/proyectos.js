var personasProyecto = {};
var clientesSeleccionados = 0;
var empleadosSeleccionados = false;

$(function () {
    $('#fechaInicioProyecto').datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});
    $('#fechaInicioVersion')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fechaFinVersion').datetimepicker('setStartDate', $('#fechaInicioVersion').val());
            });
    $('#fechaFinVersion')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fechaInicioVersion').datetimepicker('setEndDate', $('#fechaFinVersion').val());
            });
    $("#participante")
            .typeahead({
                onSelect: function (item) {
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
                    preDispatch: function (query) {
                        return {search: query, accion: "completarPersonas"};
                    },
                    preProcess: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            var persona = data[i];
                            personasProyecto[persona.id] = persona;
                        }
                        return data;
                    }
                }
            });
});

jQuery(function () {
    $("#limpiarParticipante").click(function () {
        $("#participante").val('');
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

function nuevaVersion(idProyecto, fechaInicio) {
    $("#idProyectoVersion").val(idProyecto);
    $("#idVersion").val('');
    $("#nombreVersion").val('');
    $("#estado").val("0");
    $("#fechaInicioVersion").val('');
    $("#fechaFinVersion").val('');
    $("#alcance").val('');
    $('#fechaInicioVersion').datetimepicker('setStartDate', fechaInicio);
    $('#fechaFinVersion').datetimepicker('setStartDate', fechaInicio);
    $('#fechaInicioVersion').datetimepicker('setEndDate', null);
    $('#fechaFinVersion').datetimepicker('setEndDate', null);
    $("#divComentarios").hide();
    $("#modalVersiones").modal("show");
}

function editarProyecto(idProyecto) {
    $.ajax({
        type: "POST",
        url: "ProyectosController",
        dataType: "json",
        data: {idProyecto: idProyecto, accion: "editarProyecto"},
        success: function (data) {
            personasProyecto = {};
            clientesSeleccionados = 0;
            empleadosSeleccionados = 0;
            if (data !== undefined) {
                $("#idProyecto").val(data.idProyecto !== undefined ? data.idProyecto : "");
                $("#nombreProyecto").val(data.nombreProyecto !== undefined ? data.nombreProyecto : "");
                $("#fechaInicioProyecto").val(data.fechaInicio !== undefined ? data.fechaInicio : "");
                $("#clientesProyecto").html(data.clientes !== undefined ? pintarListaPersonas(data.clientes) : 'No se han agregado clientes al proyecto');
                if (data.clientes !== undefined) {
                    clientesSeleccionados = data.clientes.length;
                }
                $("#empleadosProyecto").html(data.empleados !== undefined ? pintarListaPersonas(data.empleados) : 'No se han agregado empleados al proyecto');
                if (data.empleados !== undefined) {
                    empleadosSeleccionados = data.empleados.length;
                }
                $("#participante").val('');
                $("#modalProyectos").modal("show");
            }
        },
        error: function () {
        }
    });
}

function editarVersion(idVersion) {
    $.ajax({
        type: "POST",
        url: "ProyectosController",
        dataType: "json",
        data: {idVersion: idVersion, accion: "editarVersion"},
        success: function (data) {
            if (data !== undefined) {
                $("#idProyectoVersion").val(data.idProyecto !== undefined ? data.idProyecto : "");
                $("#idVersion").val(data.idVersion !== undefined ? data.idVersion : "");
                $("#nombreVersion").val(data.nombreVersion !== undefined ? data.nombreVersion : "");
                $("#estado").val(data.estado !== undefined ? data.estado : "0");
                $("#costo").val(data.costo !== undefined ? data.costo : "0");
                $("#fechaInicioVersion").val(data.fechaInicio !== undefined ? data.fechaInicio : "");
                if (data.fechaInicio !== undefined) {
                    $('#fechaFinVersion').datetimepicker('setStartDate', data.fechaInicio);
                }
                $("#fechaFinVersion").val(data.fechaFin !== undefined ? data.fechaFin : "");
                if (data.fechaFin !== undefined) {
                    $('#fechaInicioVersion').datetimepicker('setEndDate', data.fechaFin);
                }
                $('#fechaInicioVersion').datetimepicker('setStartDate', data.fechaProyecto);
                $("#alcance").val(data.alcance !== undefined ? data.alcance : "");
                $("#listaComentarios").html(data.comentarios);
                $("#divComentarios").show();
                $("#modalVersiones").modal("show");
            }
        },
        error: function () {
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
        html += pintarPersona(listaPersonas[persona]);
    }
    return html;
}

function pintarPersona(persona) {
    var html = '    <li class="list-group-item" id="persona' + persona.id + '">'
            + '         <div class="row">'
            + '             <input type="hidden" id="idPersona' + persona.id + '" name="idPersonas" value="' + persona.id + '" />'
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

function guardarComentario() {
    $.ajax({
        type: "POST",
        url: "ProyectosController",
        dataType: "json",
        data: {accion: "guardarComentario", comentario: jQuery("#comentario").val(), idVersion: $("#idVersion").val()},
        success: function(data) {
            if (data !== undefined) {
                if (data.comentarios !== undefined && data.comentarios !== '') {
                    $("#comentario").val('');
                    $("#listaComentarios").html(data.comentarios);
                }
            }
        },
        error: function() {
        }
    });
}

function eliminarComentario(idComentario) {
    $.ajax({
        type: "POST",
        url: "ProyectosController",
        dataType: "json",
        data: {accion: "eliminarComentario", idComentario: idComentario, idVersion: $("#idVersion").val()},
        success: function(data) {
            if (data !== undefined) {
                if (data.comentarios !== undefined && data.comentarios !== '') {
                    $("#comentario").val('');
                    $("#listaComentarios").html(data.comentarios);
                }
            }
        },
        error: function() {
        }
    });
}