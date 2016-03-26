/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var personasActividades = {};
var clientesSeleccionados = 0;
var empleadosSeleccionados = 0;
var horasTrabajadasDia = 9;

jQuery(function () {
    $('#fecha_estimada_inicio')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_estimada_terminacion').datetimepicker('setStartDate', $('#fecha_estimada_inicio').val());
            });
    $('#fecha_estimada_terminacion')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_estimada_inicio').datetimepicker('setEndDate', $('#fecha_estimada_terminacion').val());
            });


    $('#fecha_real_inicio')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_real_terminacion').datetimepicker('setStartDate', $('#fecha_real_inicio').val());
            });
    $('#fecha_real_terminacion')
            .datetimepicker({format: 'dd/mm/yyyy', language: 'es', weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
            .on('changeDate', function () {
                $('#fecha_real_inicio').datetimepicker('setEndDate', $('#fecha_real_terminacion').val());
            });

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

    $('#fecha_estimada_inicio').change(function () {
        var dato = $('#fecha_estimada_inicio').val();
        if (dato !== undefined && dato !== "") {
            $("#tiempo_estimado").val("0");
            $("#tiempo_estimado").prop("disabled", false);
        } else {
            $("#tiempo_estimado").val("0");
            $("#tiempo_estimado").prop("disabled", true);
        }
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
                for (var version in data) {
                    version = data[version];
                    html += "<option value='" + version.id + "'>" + version.nombre + "</option>";
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
    var varFechaInicial = $("#fecha_estimada_inicio").val();

    var arrFecha = varFechaInicial.split('/');
    var dia = arrFecha[0];
    var mes = arrFecha[1];
    var anno = arrFecha[2];
    var fechaInicial = anno + "-" + mes + "-" + dia;

    $("#tiempo_estimado").data("old", $("#tiempo_estimado").data("new") || "");
    $("#tiempo_estimado").data("new", $("#tiempo_estimado").val());

    var totalDias = (parseInt(horas) + 1) / parseInt(horasTrabajadasDia);
    diasEstimados = Math.ceil(totalDias);

    var date = new Date(fechaInicial);
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var edate = new Date(y, m, d + diasEstimados);
    var ndate = ("0" + edate.getDate()).slice(-2) + '/' + ("0" + (edate.getMonth() + 1)).slice(-2) + "/" + edate.getFullYear();
    $("#fecha_estimada_terminacionn").val(ndate);
    $("#fecha_estimada_terminacion").val(ndate);
}

function Validar() {
    var empleados = $("input[name='idPersonas']").map(function () {
        return $(this).val();
    }).get();

    var varFechaInicial = $("#fecha_estimada_inicio").val();
    var varFechaFin = $("#fecha_estimada_terminacion").val();

    if (empleados.toString() !== "" && varFechaInicial !== "" && varFechaFin !== "") {
        $.ajax({
            type: "POST",
            url: "ActividadesController",
            dataType: "json",
            data: {idPersonasSeleccionadas: empleados, strFechaEstimadaInicial: varFechaInicial, strFechaEstimadaFin:varFechaFin ,accion: "consultarFechasActividades"},
            success: function (data) {
                if (data !== undefined) {
                    var html = "Las siguientes personas tienen actividades asignadas para las fechas señaladas <br /><br />";
                    var clientes = "<b>Clientes</b><br /><br />";
                    var empleados = "<b>Empleados</b><br /><br />";
                    for (var persona in data) {
                        persona = data[persona];
                        if (persona.cargo.toLowerCase() === "cliente") {
                            clientes += persona.nombre;
                        } else {
                            empleados += persona.nombre;
                        }
                    }
                    if (clientes === "<b>Clientes</b><br /><br />") {
                        clientes = "";
                    }
                    if (empleados === "<b>Empleados</b><br /><br />") {
                        empleados = "";
                    }

                    html += clientes + empleados + '<br /><br /> ¿Desea Continuar?';
                    $("#contenidoWarning").html(html);
                    $("#modalWarning").modal("show");
                }
            },
            error: function (err) {
                alert(err);
            }
        });
        alert('return true');
        return true;
    } else {
        alert('no se ha seleccionado personas');
        return false;
    }
}