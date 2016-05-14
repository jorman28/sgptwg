var fechasVersiones = {};
var horasTrabajadasDia = 9;

jQuery(function() {
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

    $("#participante").typeahead({
        onSelect: function(item) {
            if ($("#persona" + item.value)[0] === undefined) {
                jQuery("#idPersona").val(item.value);
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

    $('#fechaInicio').change(function() {
        $("#tiempo").val("0");
        $("#fechaFin").val("");
        var dato = $('#fechaInicio').val();
        if (dato !== undefined && dato !== "") {
            $("#tiempo").prop("disabled", false);
        } else {
            $("#tiempo").prop("disabled", true);
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
        error: function(err) {
            alert(err);
        }
    });
}

function pintarListaPersonas(listaPersonas) {
    var html = "";
    for (var persona in listaPersonas) {
        html += pintarPersonas(listaPersonas[persona]);
    }
    return html;
}

function pintarPersonas(persona) {
    var html = '<li class="list-group-item" id="persona' + persona.idPersona + '">';
    html += '    <div class="row">';
    html += '        <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">';
    html += '           <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">';
    html += persona.nombre;
    html += '           </div>';
    html += '           <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">';
    html += persona.fechaInicio + ' - ' + persona.fechaFin + ' (' + persona.tiempo + ' h)';
    html += '           </div>';
    html += '        </div>';
    html += '        <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">';
    html += '           <span class="glyphicon glyphicon-time" style="cursor:pointer;" onclick="estimar(' + persona.idPersona + ', \'' + persona.fechaInicio + '\', \'' + persona.fechaFin + '\', ' + persona.tiempo + ');"></span>';
    html += '           <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(' + persona.idPersona + ');"></span>';
    html += '        </div>';
    html += '   </div>';
    html += '</li>';
    return html;
}

function eliminarPersona(idPersona) {
    $("#persona" + idPersona).remove();
}

function calcularFechaFin(horas) {
    var diasEstimados = 0;
    var varFechaInicial = $("#fechaInicio").val();

    var arrFecha = varFechaInicial.split('/');
    var dia = arrFecha[0];
    var mes = arrFecha[1];
    var anno = arrFecha[2];
    var fechaInicial = anno + "-" + mes + "-" + dia;

    $("#tiempo").data("old", $("#tiempo").data("new") || "");
    $("#tiempo").data("new", $("#tiempo").val());

    var totalDias = (parseInt(horas) + 1) / parseInt(horasTrabajadasDia);
    diasEstimados = Math.ceil(totalDias);

    var date = new Date(fechaInicial);
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var edate = new Date(y, m, d + diasEstimados);
    var ndate = ("0" + edate.getDate()).slice(-2) + '/' + ("0" + (edate.getMonth() + 1)).slice(-2) + "/" + edate.getFullYear();
    $("#fechaFin").val(ndate);
}

function validarEstimacion() {
    var idActividad = $("#id").val();
    var idResponsable = $("#idPersona").val();
    var fechaInicio = $("#fechaInicio").val();
    var fechaFin = $("#fechaFin").val();

    if (idResponsable !== "" && fechaInicio !== "" && fechaFin !== "") {
        $.ajax({
            type: "POST",
            url: "ActividadesController",
            dataType: "json",
            data: {responsable: idResponsable, fechaInicio: fechaInicio, fechaFin: fechaFin, id: idActividad, accion: "consultarFechasActividades"},
            success: function(actividades) {
                if (actividades !== undefined && actividades.length > 0) {
                    var html = "Esta persona ya tiene actividades asociadas entre las fechas " + fechaInicio + " y " + fechaFin + "<br>";
                    for (var i in actividades) {
                        var actividad = actividades[i];
                        html += actividad.nombre + ' ' + actividad.fechaInicio + ' - ' + actividad.fechaFin + ' (' + actividad.tiempo + ' h) <br>';
                    }
                    html += '<b>¿Desea continuar con el registro?</b>';
                    $("#contenidoWarning").html(html);
                    $("#modalWarning").modal("show");
                } else {
                    $('#confirmarEstimacion').click();
                }
            },
            error: function(err) {
                alert(err);
            }
        });
    }
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
        data: {accion: "guardarPersonaActividad", id: $("#id").val(), responsable: jQuery("#idPersona").val(),
            fechaInicio: fechaInicio, fechaFin: fechaFin, tiempo: tiempo, estimacion: estimacion},
        success: function(almacenamiento) {
            if (almacenamiento.resultado !== undefined && almacenamiento.resultado === 'ok') {
                jQuery('#mensajeExito').html('El responsable ha sido guardado con éxito');
                jQuery('#alertaExito').show();
                if (almacenamiento.clientesActividad !== undefined && almacenamiento.clientesActividad.length > 0) {
                    jQuery('#clientesActividad').html(pintarListaPersonas(almacenamiento.clientesActividad));
                } else {
                    jQuery('#clientesActividad').html('No se han agregado clientes al proyecto');
                }
                if (almacenamiento.empleadosActividad !== undefined && almacenamiento.empleadosActividad.length > 0) {
                    jQuery('#empleadosActividad').html(pintarListaPersonas(almacenamiento.empleadosActividad));
                } else {
                    jQuery('#empleadosActividad').html('No se han agregado empleados al proyecto');
                }
                $("#participante").val('');
            } else if (almacenamiento.mensaje !== undefined && almacenamiento.mensaje !== '') {
                jQuery('#mensajeError').html(almacenamiento.mensaje);
                jQuery('#alertaError').show();
            } else {
                jQuery('#mensajeError').html('Error guardando datos de responsable');
                jQuery('#alertaError').show();
            }
        },
        error: function() {
            jQuery('#mensajeError').html('Error guardando datos de responsable');
            jQuery('#alertaError').show();
        }
    });
}

function limpiarEstimacion() {
    $("#fechaInicio").val('');
    $("#fechaFin").val('');
    $("#tiempo").val('0');
}

function guardarComentario() {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "guardarComentario", comentario: jQuery("#comentario").val(), id: $("#id").val()},
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
        }
    });
}