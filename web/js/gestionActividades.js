var fechasVersiones = {};
var horasTrabajadasDia = 9;

$(function() {
    $('#fechaInicio')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function() {
                $('#fechaFin').datetimepicker('setStartDate', $('#fechaInicio').val());
            });
    $('#fechaFin')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function() {
                $('#fechaInicio').datetimepicker('setEndDate', $('#fechaFin').val());
            });
    $('#fechaTrabajo')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2});

    $("#participante").typeahead({
        onSelect: function(item) {
            if ($("#persona" + item.value)[0] === undefined) {
                $("#idPersona").val(item.value);
                guardarPersonaActividad();
            }
        },
        ajax: {
            url: "ActividadesController",
            timeout: 500,
            displayField: "nombre",
            valueField: 'id',
            triggerLength: 1,
            items: 10,
            method: "POST",
            preDispatch: function(busqueda) {
                return {busqueda: busqueda, proyecto: $("#proyecto").val(), accion: "consultarPersonasProyecto"};
            },
            preProcess: function(data) {
                return data;
            }
        }
    });

    $("#limpiarParticipante").click(function() {
        $("#participante").val('');
    });
});

function consultarVersiones(idProyecto) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {proyecto: idProyecto, accion: "consultarVersiones"},
        success: function(data) {
            if (data !== undefined) {
                var html = "<option value='0'>SELECCIONE</option>";
                fechasVersiones = {};
                for (var version in data) {
                    version = data[version];
                    html += "<option value='" + version.id + "'>" + version.nombre + "</option>";
                    var fechasVersion = {};
                    fechasVersion['fechaInicio'] = version.fechaInicio;
                    fechasVersion['fechaTerminacion'] = version.fechaTerminacion;
                    fechasVersiones[version.id] = fechasVersion;
                }
                $("#version").html(html);
            }
        },
        error: function() {
            mostrarError('Error consultando las fechas de la versión');
        }
    });
}

function pintarListaPersonas(listaPersonas) {
    var html = "";
    for (var persona in listaPersonas) {
        persona = listaPersonas[persona];
        html += '<li class="list-group-item" id="persona' + persona.idPersona + '">';
        html += '    <div class="row">';
        html += '        <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">';
        html += '           <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">';
        html += persona.nombre;
        html += '           </div>';
        html += '           <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">';
        html += '           <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">';
        html += persona.tiempoEstimado + 'h - ' + persona.tiempoInvertido + 'h';
        html += '</div>';
        html += '<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">';
        html += persona.fechaInicio + ' - ' + persona.fechaFin;
        html += '</div>';
        html += '           </div>';
        html += '        </div>';
        html += '        <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">';
        html += '           <span class="glyphicon glyphicon-time" style="cursor:pointer;" onclick="registrarTiempo(' + persona.idPersona + ');"></span>';
        html += '           <span class="glyphicon glyphicon-calendar" style="cursor:pointer;" onclick="estimar(' + persona.idPersona + ', \'' + persona.fechaInicio + '\', \'' + persona.fechaFin + '\', ' + persona.tiempoEstimado + ');"></span>';
        html += '           <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="botonEliminarPersona(' + persona.idPersona + ');"></span>';
        html += '        </div>';
        html += '   </div>';
        html += '</li>';
    }
    return html;
}

function botonEliminarPersona(idPersona) {
    var html = '<button type="button" class="btn btn-primary" onclick="eliminarPersona(' + idPersona + ');">Si</button> ';
    html += '<button type="button" class="btn btn-primary" data-dismiss="modal">No</button>';
    $("#botonEliminacion").html(html);
    $("#eliminacionDatos").modal('show');
}

function pintarHistorialTrabajo(historialTrabajo) {
    var html = '';
    for (var trabajo in historialTrabajo) {
        trabajo = historialTrabajo[trabajo];
        html += '<li class="list-group-item" id="historial1">';
        html += '   <div class="row">';
        html += '       <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">';
        html += '           <div class="row">';
        html += '               <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">';
        html += trabajo.fecha;
        html += '               </div>';
        html += '               <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">';
        html += trabajo.tiempo + ' h';
        html += '               </div>';
        html += '           </div>';
        html += '       </div>';
        html += '       <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">';
        html += '           <span class="glyphicon glyphicon-pencil" style="cursor:pointer;" onclick="editarHistorial(' + trabajo.id + ', \'' + trabajo.fecha + '\', ' + trabajo.tiempo + ',\'' + trabajo.descripcion + '\');"></span>';
        html += '           <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="botonEliminarHistorial(' + trabajo.id + ');"></span>';
        html += '       </div>';
        html += '   </div>';
        html += '</li>';
    }
    return html;
}

function botonEliminarHistorial(idHistorial) {
    var html = '<button type="button" class="btn btn-primary" onclick="eliminarHistorial(' + idHistorial + ');">Si</button> ';
    html += '<button type="button" class="btn btn-primary" data-dismiss="modal">No</button>';
    $("#botonEliminacion").html();
    $("#eliminacionDatos").modal('show');
}

function actualizarFechas(idVersion) {
    var fechasVersion = fechasVersiones[idVersion];
    $('#fechaInicio').val('');
    $('#fechaInicio').datetimepicker('setStartDate', fechasVersion.fechaInicio);
    $('#fechaInicio').datetimepicker('setEndDate', fechasVersion.fechaTerminacion);
    $('#fechaFin').val('');
    $('#fechaFin').datetimepicker('setStartDate', fechasVersion.fechaInicio);
    $('#fechaFin').datetimepicker('setEndDate', fechasVersion.fechaTerminacion);
    $('#tiempo').val('0');
    $('#idPersona').val('');
}

function guardarPersonaActividad(estimacion) {
    var fechaInicio = $("#fechaInicio").val();
    var fechaFin = $("#fechaFin").val();
    var tiempo = $("#tiempo").val();
    if (estimacion === undefined) {
        estimacion = '';
    }
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "guardarPersonaActividad", id: $("#id").val(), responsable: $("#idPersona").val(),
            fechaInicio: fechaInicio, fechaFin: fechaFin, tiempo: tiempo, estimacion: estimacion},
        success: function(almacenamiento) {
            if (almacenamiento.resultado !== undefined && almacenamiento.resultado === 'ok') {
                if (almacenamiento.clientesActividad !== undefined && almacenamiento.clientesActividad.length > 0) {
                    $('#clientesActividad').html(pintarListaPersonas(almacenamiento.clientesActividad));
                } else {
                    $('#clientesActividad').html('No se han agregado clientes al proyecto');
                }
                if (almacenamiento.empleadosActividad !== undefined && almacenamiento.empleadosActividad.length > 0) {
                    $('#empleadosActividad').html(pintarListaPersonas(almacenamiento.empleadosActividad));
                } else {
                    $('#empleadosActividad').html('No se han agregado empleados al proyecto');
                }
                $("#participante").val('');
            } else if (almacenamiento !== undefined && almacenamiento.resultado === 'advertencia') {
                mostrarAdvertencia(almacenamiento.mensaje);
            } else if (almacenamiento.mensaje !== undefined && almacenamiento.mensaje !== '') {
                mostrarError(almacenamiento.mensaje);
            } else {
                mostrarError('Error guardando datos de responsable');
            }
            $("#estimacionTiempo").modal("hide");
        },
        error: function() {
            mostrarError('Error guardando datos de responsable');
            $("#estimacionTiempo").modal("hide");
        }
    });
}

function eliminarPersona(idPersona) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "eliminarEmpleado", id: $("#id").val(), responsable: idPersona},
        success: function(eliminacion) {
            if (eliminacion.resultado !== undefined && eliminacion.resultado === 'ok') {
                if (eliminacion.clientesActividad !== undefined && eliminacion.clientesActividad.length > 0) {
                    $('#clientesActividad').html(pintarListaPersonas(eliminacion.clientesActividad));
                } else {
                    $('#clientesActividad').html('No se han agregado clientes al proyecto');
                }
                if (eliminacion.empleadosActividad !== undefined && eliminacion.empleadosActividad.length > 0) {
                    $('#empleadosActividad').html(pintarListaPersonas(eliminacion.empleadosActividad));
                } else {
                    $('#empleadosActividad').html('No se han agregado empleados al proyecto');
                }
            } else if (eliminacion.mensaje !== undefined && eliminacion.mensaje !== '') {
                mostrarError(eliminacion.mensaje);
            } else {
                mostrarError('El responsable de la actividad no pudo ser eliminado');
            }
        },
        error: function() {
            mostrarError('Error eliminando responsable de la actividad');
        }
    });
}

function estimar(idPersona, fechaInicio, fechaFin, tiempo) {
    $("#idPersona").val(idPersona);
    $("#fechaInicio").val(fechaInicio);
    $("#fechaFin").val(fechaFin);
    $("#tiempo").val(tiempo);
    $('#fechaFin').datetimepicker('setStartDate', fechaInicio);
    $('#fechaInicio').datetimepicker('setEndDate', fechaFin);
    $("#estimacionTiempo").modal("show");
}

function validarEstimacion() {
    var idActividad = $("#id").val();
    var idResponsable = $("#idPersona").val();
    var fechaInicio = $("#fechaInicio").val();
    var fechaFin = $("#fechaFin").val();
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "consultarFechasActividades", responsable: idResponsable, fechaInicio: fechaInicio, fechaFin: fechaFin, id: idActividad},
        success: function(actividades) {
            if (actividades !== undefined && actividades.length > 0) {
                var html = "Esta persona ya tiene actividades asociadas entre las fechas " + fechaInicio + " y " + fechaFin + "<br>";
                for (var i in actividades) {
                    var actividad = actividades[i];
                    html += actividad.nombre + ' ' + actividad.fechaInicio + ' - ' + actividad.fechaFin + ' (' + actividad.tiempoEstimado + ' h) - (' + actividad.tiempoInvertido + ' h) <br>';
                }
                html += '<b>¿Desea continuar con el registro?</b>';
                $("#contenidoWarning").html(html);
                $("#modalWarning").modal("show");
            } else {
                $('#confirmarEstimacion').click();
            }
        },
        error: function() {
            mostrarError('Error validando la estimación de trabajo');
        }
    });
}

function registrarTiempo(idPersona) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "consultarHistorialTrabajo", id: $("#id").val(), responsable: idPersona},
        success: function(historialTrabajo) {
            $("#idPersona").val(idPersona);
            $("#fechaTrabajo").val('');
            $("#tiempoTrabajado").val('');
            $("#detalleTrabajo").val('');
            $("#idHistorial").val('');
            if (historialTrabajo !== undefined && historialTrabajo.length > 0) {
                $("#historialTrabajo").html(pintarHistorialTrabajo(historialTrabajo));
            } else {
                $("#historialTrabajo").html('No se ha registrado trabajo para la actividad');
            }
            $("#inversionTiempo").modal("show");
        },
        error: function() {
            mostrarError('Error consultando el historial de trabajo');
            $("#inversionTiempo").modal("hide");
        }
    });
}

function editarHistorial(idHistorial, fecha, tiempo, descripcion) {
    $("#idHistorial").val(idHistorial);
    $("#fechaTrabajo").val(fecha);
    $("#tiempoTrabajado").val(tiempo);
    $("#detalleTrabajo").val(descripcion);
}

function guardarReporteTiempo() {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "guardarEsfuerzo", esfuerzo: $("#idHistorial").val(), id: $("#id").val(), responsable: $("#idPersona").val(),
            fecha: $("#fechaTrabajo").val(), tiempo: $("#tiempoTrabajado").val(), descripcion: $("#detalleTrabajo").val()},
        success: function(resultado) {
            if (resultado !== undefined && resultado.resultado === 'ok') {
                $("#fechaTrabajo").val('');
                $("#tiempoTrabajado").val('');
                $("#detalleTrabajo").val('');
                $("#idHistorial").val('');
                if (resultado.clientesActividad !== undefined && resultado.clientesActividad.length > 0) {
                    $('#clientesActividad').html(pintarListaPersonas(resultado.clientesActividad));
                } else {
                    $('#clientesActividad').html('No se han agregado clientes al proyecto');
                }
                if (resultado.empleadosActividad !== undefined && resultado.empleadosActividad.length > 0) {
                    $('#empleadosActividad').html(pintarListaPersonas(resultado.empleadosActividad));
                } else {
                    $('#empleadosActividad').html('No se han agregado empleados al proyecto');
                }
                if (resultado.historialTrabajo !== undefined && resultado.historialTrabajo.length > 0) {
                    $("#historialTrabajo").html(pintarHistorialTrabajo(resultado.historialTrabajo));
                } else {
                    $("#inversionTiempo").modal("hide");
                }
            } else if (resultado !== undefined && resultado.resultado === 'advertencia') {
                mostrarAdvertencia(resultado.mensaje);
                $("#inversionTiempo").modal("hide");
            } else if (resultado.mensaje !== undefined && resultado.mensaje !== '') {
                mostrarError(resultado.mensaje);
                $("#inversionTiempo").modal("hide");
            } else {
                mostrarError('El registro de tiempo no pudo ser guardado');
                $("#inversionTiempo").modal("hide");
            }
        },
        error: function() {
            mostrarError('Error guardando el registro de tiempo');
            $("#inversionTiempo").modal("hide");
        }
    });
}

function eliminarHistorial(idHistorial) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "eliminarEsfuerzo", esfuerzo: idHistorial, responsable: $("#idPersona").val()},
        success: function(resultado) {
            if (resultado !== undefined && resultado.resultado === 'ok') {
                $("#fechaTrabajo").val('');
                $("#tiempoTrabajado").val('');
                $("#detalleTrabajo").val('');
                $("#idHistorial").val('');
                if (resultado.clientesActividad !== undefined && resultado.clientesActividad.length > 0) {
                    $('#clientesActividad').html(pintarListaPersonas(resultado.clientesActividad));
                } else {
                    $('#clientesActividad').html('No se han agregado clientes al proyecto');
                }
                if (resultado.empleadosActividad !== undefined && resultado.empleadosActividad.length > 0) {
                    $('#empleadosActividad').html(pintarListaPersonas(resultado.empleadosActividad));
                } else {
                    $('#empleadosActividad').html('No se han agregado empleados al proyecto');
                }
                if (resultado.historialTrabajo !== undefined && resultado.historialTrabajo.length > 0) {
                    $("#historialTrabajo").html(pintarHistorialTrabajo(resultado.historialTrabajo));
                } else {
                    $("#historialTrabajo").html("No se ha registrado trabajo para la actividad");
                }
            } else if (resultado.mensaje !== undefined && resultado.mensaje !== '') {
                mostrarError(resultado.mensaje);
                $("#inversionTiempo").modal("hide");
            } else {
                mostrarError('El registro de tiempo no pudo ser eliminado');
                $("#inversionTiempo").modal("hide");
            }
        },
        error: function() {
            mostrarError('Error eliminando el registro de tiempo');
            $("#inversionTiempo").modal("hide");
        }
    });
}

function guardarComentario() {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "guardarComentario", comentario: $("#comentario").val(), id: $("#id").val()},
        success: function(data) {
            if (data !== undefined) {
                if (data.comentarios !== undefined && data.comentarios !== '') {
                    $("#comentario").val('');
                    $("#listaComentarios").html(data.comentarios);
                }
            }
        },
        error: function() {
            mostrarError('Error guardando comentario');
        }
    });
}

function eliminarComentario(idComentario) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "eliminarComentario", idComentario: idComentario, id: $("#id").val()},
        success: function(data) {
            if (data !== undefined) {
                if (data.comentarios !== undefined && data.comentarios !== '') {
                    $("#comentario").val('');
                    $("#listaComentarios").html(data.comentarios);
                }
            }
        },
        error: function() {
            mostrarError('Error eliminando comentario');
        }
    });
}