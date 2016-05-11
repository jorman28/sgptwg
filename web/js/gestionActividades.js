var personasActividades = {};
var fechasVersiones = {};
var clientesSeleccionados = 0;
var empleadosSeleccionados = 0;
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
                var persona = personasActividades[item.value];
                var html = pintarPersonas(persona);
                if (persona.cargo.toLowerCase() === "cliente") {
                    if (clientesSeleccionados === 0) {
                        $("#clientesActividad").html(html);
                    } else {
                        $("#clientesActividad").append(html);
                    }
                    clientesSeleccionados++;
                } else {
                    if (empleadosSeleccionados === 0) {
                        $("#empleadosActividad").html(html);
                    } else {
                        $("#empleadosActividad").append(html);
                    }
                    empleadosSeleccionados++;
                }
                $('#idPersona').val(persona.id);
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
                for (var i = 0; i < data.length; i++) {
                    var persona = data[i];
                    personasActividades[persona.id] = persona;
                }
                return data;
            }
        }
    });

    $('#proyecto').change(function() {
        var dato = $('#proyecto').val();
        if (dato !== undefined && dato !== "" && dato !== "0") {
            $("#participante").val("");
            $("#participante").prop("disabled", false);
        } else {
            $("#participante").val("");
            $("#participante").prop("disabled", true);
        }
        $("#clientesActividad").html('No se han agregado clientes al proyecto');
        clientesSeleccionados = 0;
        $("#empleadosActividad").html('No se han agregado empleados al proyecto');
        empleadosSeleccionados = 0;
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
    var html = '    <li class="list-group-item" id="persona' + persona.id + '">'
            + '         <div class="row">'
            + '             <input type="hidden" id="idPersona' + persona.id + '" name="idPersonas" value="' + persona.id + '" />'
            + '             <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">'
            + '                 ' + persona.nombre
            + '             </div>'
            + '             <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">'
            + '                 <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(' + persona.id + ' , \'' + persona.cargo + '\');"></span>'
            + '             </div>'
            + '         </div>'
            + '     </li>';
    return html;
}

function eliminarPersona(idPersona, cargo) {
    $("#persona" + idPersona).remove();
    if (cargo.toLowerCase() === "cliente") {
        clientesSeleccionados--;
        if (clientesSeleccionados === 0) {
            $("#clientesActividad").html('No se han agregado clientes al proyecto');
        }
    } else {
        empleadosSeleccionados--;
        if (empleadosSeleccionados === 0) {
            $("#empleadosActividad").html('No se han agregado empleados al proyecto');
        }
    }
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
            success: function(data) {
                if (data !== undefined && data !== "") {
                    var html = "Esta persona ya tiene actividades asociadas entre las fechas " + fechaInicio + " y " + fechaFin + "<br/><br/>";
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
        success: function(data) {
            if (data !== undefined && data.resultado !== undefined && data.resultado !== '') {
            } else {
                
            }
        },
        error: function() {
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