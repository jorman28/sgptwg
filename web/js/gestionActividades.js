var personasActividades = {};
var fechasVersiones = {};
var clientesSeleccionados = 0;
var empleadosSeleccionados = 0;
var horasTrabajadasDia = 9;

jQuery(function () {
    $("#participante")
            .typeahead({
                onSelect: function (item) {
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
                    preDispatch: function (query) {
                        var proyecto = $("#proyecto").val();
                        return {search: query, search1: proyecto, accion: "consultarPersonasProyecto"};
                    },
                    preProcess: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            var persona = data[i];
                            personasActividades[persona.id] = persona;
                        }
                        return data;
                    }
                }
            });

    $('#proyecto').change(function () {
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

    $("#limpiarParticipante").click(function () {
        $("#participante").val('');
    });

});

function consultarVersiones(idProyecto) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {proyecto: idProyecto, accion: "consultarVersiones"},
        success: function (data) {
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
        error: function (err) {
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
            + '                 ' + persona.nombre + ' - <a href="#" data-toggle="modal" onclick="mostrarModal(' + persona.id + ')"> Añadir fechas y tiempos <a/>'
            + '             </div>'
            + '             <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">'
            + '                 <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(' + persona.id + ' , \'' + persona.cargo + '\');"></span>'
            + '             </div>'
            + '         </div>'
            + '     </li>';
    pintarModalFechas(persona);
    return html;
}

function eliminarPersona(idPersona, cargo) {
    $("#persona" + idPersona).remove();
    $('#modal_' + idPersona).remove();
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

//Función para pintar el modal que contendra la información de las fechas y tiempos de cada empleado
//sera mostrado cuando el usuario selecione el link de Añadir fechas y tiempos
function pintarModalFechas(persona) {
    var html = '<div id="modal_' + persona.id + '" class="modal fade bs-example-modal-lg">'
            + '     <div class="modal-dialog modal-lg">'
            + '         <div class="modal-content">'
            + '             <div class="modal-header">'
            + '                 <span class="glyphicon glyphicon-calendar"></span> <b> Programar actividad para ' + persona.nombre + '</b>'
            + '                 <button type="button" class="close" data-dismiss="modal">&times;</button>'
            + '             </div>'
            + '             <div class="modal-body">'
            + '                 <div class="form-group">'
            + '                     <div class="row">'
            + '                         <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">'
            + '                             <label for="fecha_estimada_inicio_' + persona.id + '">*Fecha estimada inicio:</label>'
            + '                             <input class="form-control" type="text" id="fecha_estimada_inicio_' + persona.id + '" name="fecha_estimada_inicio_' + persona.id + '" onchange="fecha_estimada_inicio_Change(' + persona.id + ')" readonly="true"/>'
            + '                             <script type="text/javascript">'
            + '                                 $(function () {'
            + '                                     /*seleccionar la fecha estimada de inicio de una actividad para una persona*/'
            + '                                     $("#fecha_estimada_inicio_' + persona.id + '")'
            + '                                     .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})'
            + '                                     .on("changeDate", function () {'
            + '                                         $("#fecha_estimada_terminacion_' + persona.id + '").datetimepicker("setStartDate", $("#fecha_estimada_inicio_' + persona.id + '").val());'
            + '                                     });'
            + '                                 });'
            + '                             </script>'
            + '                         </div>'
            + '                         <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">'
            + '                             <label for="fecha_estimada_terminacion_' + persona.id + '">*Fecha estimada fin:</label>'
            + '                             <input class="form-control" type="text" id="fecha_estimada_terminacionn_' + persona.id + '" name="fecha_estimada_terminacionn_' + persona.id + '" disabled = "disabled"/>'//<!--se pone el id del type text fecha_estimada_terminacionn porque a pesar de estar disabled al tener el mismo nombre del hidden el dato no llegaba a controlador-->
            + '                             <input type="hidden" id="fecha_estimada_terminacion_' + persona.id + '" name="fecha_estimada_terminacion_' + persona.id + '"/>'
            + '                             <script type="text/javascript">'
            + '                                 $(function () {'
            + '                                     /*Función que permite seleccionar la fecha estimada de terminacion de una actividad para una persona*/'
            + '                                     $("#fecha_estimada_terminacion_' + persona.id + '")'
            + '                                     .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})'
            + '                                     .on("changeDate", function () {'
            + '                                         $("#fecha_estimada_inicio_' + persona.id + '").datetimepicker("setEndDate", $("#fecha_estimada_terminacion_' + persona.id + '").val());'
            + '                                     });'
            + '                                 });'
            + '                             </script>'
            + '                         </div>'
            + '                         <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">'
            + '                             <label for="fecha_real_inicio_' + persona.id + '">Fecha real de inicio:</label>'
            + '                             <input class="form-control" type="text" id="fecha_real_inicio_' + persona.id + '" name="fecha_real_inicio_' + persona.id + '" readonly="true"/>'
            + '                             <script type="text/javascript">'
            + '                                 /*Función que permite seleccionar la fecha real de inicio de una actividad para una persona*/'
            + '                                 $(function () {'
            + '                                     $("#fecha_real_inicio_' + persona.id + '")'
            + '                                     .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})'
            + '                                     .on("changeDate", function () {'
            + '                                         $("#fecha_real_terminacion_' + persona.id + '").datetimepicker("setStartDate", $("#fecha_real_inicio_' + persona.id + '").val());'
            + '                                     });'
            + '                                 });'
            + '                             </script>'
            + '                         </div>'
            + '                     </div>'
            + '                     <div class="row">'
            + '                         <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">'
            + '                             <label for="fecha_real_terminacion_' + persona.id + '">Fecha real de terminacion:</label>'
            + '                             <input class="form-control" type="text" id="fecha_real_terminacion_' + persona.id + '" name="fecha_real_terminacion_' + persona.id + '" readonly="true"/>'
            + '                             <script type="text/javascript">'
            + '                                 $(function () {'
            + '                                     /*Función que permite seleccionar la fecha real de terminacion de una actividad para una persona*/'
            + '                                     $("#fecha_real_terminacion_' + persona.id + '")'
            + '                                         .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})'
            + '                                         .on("changeDate", function () {'
            + '                                             $("#fecha_real_inicio_' + persona.id + '").datetimepicker("setEndDate", $("#fecha_real_terminacion_' + persona.id + '").val());'
            + '                                         });'
            + '                                     });'
            + '                             </script>'
            + '                         </div>'
            + '                         <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">'
            + '                             <label for="tiempo_estimado' + persona.id + '">*Tiempo estimado (horas):</label>'
            + '                             <input class="form-control" type="number" min="0" step="0.1" pattern="[0-9]+([,\.][0-9]+)?" id="tiempo_estimado_' + persona.id + '" name="tiempo_estimado_' + persona.id + '" onchange="calcularFechaFin(this.value, ' + persona.id + ');" disabled = "disabled"/>'
            + '                             <input type="hidden" id="tiempo_estimado_' + persona.id + '" name="tiempo_estimado_' + persona.id + '"/>'
            + '                         </div>'
            + '                         <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">'
            + '                             <label for="tiempo_invertido_' + persona.id + '">Tiempo invertido (horas):</label>'
            + '                             <input class="form-control" type="number" min="0" step="any" id="tiempo_invertido_' + persona.id + '" name="tiempo_invertido_' + persona.id + '"/>'
            + '                         </div>'
            + '                     </div>'
            + '                 </div>'
            + '             </div>'
            + '             <div class="modal-footer">'
            + '                 <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="glyphicon glyphicon-ok"></i> Aceptar</button>'
            + '             </div>'
            + '         </div>'
            + '     </div>'
            + '</div>';
    $('#modalFechas').append(html);
}

function mostrarModal(idPersona) {
    $("#modal_" + idPersona + "").modal('show');
}

function fecha_estimada_inicio_Change(idPersona) {
    var dato = $('#fecha_estimada_inicio_' + idPersona + '').val();
    if (dato !== undefined && dato !== "") {
        $("#tiempo_estimado_" + idPersona + "").val("0");
        $("#fecha_estimada_terminacionn_" + idPersona + "").val("");
        $("#fecha_estimada_terminacion_" + idPersona + "").val("");
        $("#tiempo_estimado_" + idPersona + "").prop("disabled", false);
    } else {
        $("#tiempo_estimado_" + idPersona + "").val("0");
        $("#fecha_estimada_terminacionn_" + idPersona + "").val("");
        $("#fecha_estimada_terminacion_" + idPersona + "").val("");
        $("#tiempo_estimado_" + idPersona + "").prop("disabled", true);
    }
}

function calcularFechaFin(horas, idPersona) {
    var diasEstimados = 0;
    var varFechaInicial = $("#fecha_estimada_inicio_" + idPersona + "").val();

    var arrFecha = varFechaInicial.split('/');
    var dia = arrFecha[0];
    var mes = arrFecha[1];
    var anno = arrFecha[2];
    var fechaInicial = anno + "-" + mes + "-" + dia;

    $("#tiempo_estimado_" + idPersona + "").data("old", $("#tiempo_estimado_" + idPersona + "").data("new") || "");
    $("#tiempo_estimado_" + idPersona + "").data("new", $("#tiempo_estimado_" + idPersona + "").val());

    var totalDias = (parseInt(horas) + 1) / parseInt(horasTrabajadasDia);
    diasEstimados = Math.ceil(totalDias);

    var date = new Date(fechaInicial);
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var edate = new Date(y, m, d + diasEstimados);
    var ndate = ("0" + edate.getDate()).slice(-2) + '/' + ("0" + (edate.getMonth() + 1)).slice(-2) + "/" + edate.getFullYear();
    $("#fecha_estimada_terminacionn_" + idPersona + "").val(ndate);
    $("#fecha_estimada_terminacion_" + idPersona + "").val(ndate);
}

function ValidarActividades() {
    var empleados = $("input[name='idPersonas']").map(function () {
        return $(this).val();
    }).get();

    var idActividad = $("#id").val();
    var varFechaInicial = $("#fecha_estimada_inicio").val();
    var varFechaFin = $("#fecha_estimada_terminacion").val();

    if (empleados.toString() !== "" && varFechaInicial !== "" && varFechaFin !== "") {
        $.ajax({
            type: "POST",
            url: "ActividadesController",
            dataType: "json",
            data: {empleadosSeleccionados: empleados.toString(), strFechaEstimadaInicial: varFechaInicial, strFechaEstimadaFin: varFechaFin, strIdActividad: idActividad, accion: "consultarFechasActividades"},
            success: function (data) {
                var arrayLength = data.length;
                if (data !== undefined && arrayLength !== 0) {
                    var html = "Las siguientes personas tienen otras actividades asignadas entre las fechas " + varFechaInicial + " y " + varFechaFin + "<br /><br />";
                    var clientes = "<b>Clientes:</b><ul>";
                    var empleados = "<b>Empleados:</b><ul>";
                    for (var persona in data) {
                        persona = data[persona];
                        if (persona.cargo.toLowerCase() === "cliente") {
                            clientes += '<li>' + persona.nombre + '</li>';
                        } else {
                            empleados += '<li>' + persona.nombre + '</li>';
                        }
                    }
                    if (clientes === "<b>Clientes:</b><ul>") {
                        clientes = "";
                    } else {
                        clientes += "</ul>";
                    }

                    if (empleados === "<b>Empleados:</b><ul>") {
                        empleados = "";
                    } else {
                        empleados += "</ul>";
                    }

                    html += clientes + empleados + '<b>¿Desea Continuar Con El Registro?</b>';
                    $("#contenidoWarning").html(html);
                    $("#modalWarning").modal("show");
                } else {
                    $('#guardar').click();
                }

            },
            error: function (err) {
                alert(err);
            }
        });
    } else {
        $('#guardar').click();
    }
}

function guardarComentario() {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "guardarComentario", comentario: jQuery("#comentario").val(), id: $("#id").val()},
        success: function (data) {
            if (data !== undefined) {
                if (data.comentarios !== undefined && data.comentarios !== '') {
                    $("#comentario").val('');
                    $("#listaComentarios").html(data.comentarios);
                }
            }
        },
        error: function () {
        }
    });
}

function eliminarComentario(idComentario) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {accion: "eliminarComentario", idComentario: idComentario, id: $("#id").val()},
        success: function (data) {
            if (data !== undefined) {
                if (data.comentarios !== undefined && data.comentarios !== '') {
                    $("#comentario").val('');
                    $("#listaComentarios").html(data.comentarios);
                }
            }
        },
        error: function () {
        }
    });
}

function actualizarFechas(idVersion) {
    var fechasVersion = fechasVersiones[idVersion];
    $('#fecha_estimada_inicio').val('');
    $('#fecha_estimada_inicio').datetimepicker('setStartDate', fechasVersion.fechaInicio);
    $('#fecha_estimada_inicio').datetimepicker('setEndDate', fechasVersion.fechaTerminacion);
    $('#fecha_estimada_terminacion').val('');
    $('#fecha_estimada_terminacion').datetimepicker('setStartDate', fechasVersion.fechaInicio);
    $('#fecha_estimada_terminacion').datetimepicker('setEndDate', fechasVersion.fechaTerminacion);
    $('#fecha_real_inicio').val('');
    $('#fecha_real_inicio').datetimepicker('setStartDate', fechasVersion.fechaInicio);
    $('#fecha_real_inicio').datetimepicker('setEndDate', fechasVersion.fechaTerminacion);
    $('#fecha_real_terminacion').val('');
    $('#fecha_real_terminacion').datetimepicker('setStartDate', fechasVersion.fechaInicio);
    $('#fecha_real_terminacion').datetimepicker('setEndDate', fechasVersion.fechaTerminacion);
    $('#tiempo_estimado').val('0');
}