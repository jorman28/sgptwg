/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var personasActividades = {};
var clientesSeleccionados = 0;
var empleadosSeleccionados = false;

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
                        if (persona.cargo === 'Cliente') {
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
                            personasActividades[persona.id] = persona;
                        }
                        return data;
                        $("#participante").val("");
                    }
                }
            });
});


function cargarFrm() {
    var idActividad = document.getElementById("id").value;
    if (idActividad !== undefined && idActividad !== null && idActividad !== "") {
        nuevaActividad();
    } else {
        editarActividad(idActividad);
    }
}

function nuevaActividad() {
    $("#id").val('');
    $("#persona").val('0');
    $("#version").val('0');
    $("#descripcion").val('');
    $("#fecha_estimada_inicio").val('');
    $("#fecha_estimada_terminacion").val('');
    $("#fecha_real_inicio").val('');
    $("#fecha_real_terminacion").val('');
    $("#tiempo_estimado").val('');
    $("#tiempo_invertido").val('');
    $("#estado").val('0');
    $("#clientesActividad").html('No se han agregado clientes al proyecto');
    $("#empleadosActividad").html('No se han agregado empleados al proyecto');
    personasActividades = {};
    clientesSeleccionados = 0;
    empleadosSeleccionados = 0;
}

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
            $("#clientesActividad").html('No se han agregado clientes al proyecto');
        }
    } else {
        empleadosSeleccionados--;
        if (empleadosSeleccionados === 0) {
            $("#empleadosActividad").html('No se han agregado empleados al proyecto');
        }
    }
}

function editarActividad(idActividad) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {idProyecto: idActividad, accion: "gestionarActividad"},
        success: function (data) {
            personasActividades = {};
            clientesSeleccionados = 0;
            empleadosSeleccionados = 0;
            if (data !== undefined) {
                $("#id").val(data.idActividad !== undefined ? data.idActividad : "");
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



//function consultarPersonasProyecto(idProyecto) {
//    $.ajax({
//        type: "POST",
//        url: "ActividadesController",
//        dataType: "json",
//        data: {proyecto: idProyecto, accion: "consultarPersonasProyecto"},
//        success: function (data) {
//            if (data !== undefined) {
//                var varEmpleados = "<optgroup label='Empleado(s)'>";
//                var varClientes = "<optgroup label='Cliente(s)'>";
//                var varCargoCliente = "CLIENTE";
//                var html = "";
//                for (var persona in data) {
//                    persona = data[persona];
//                    if (persona.cargo.toLowerCase() === varCargoCliente.toLowerCase()) {
//                        varClientes += "<option value='" + persona.id + "'>" + persona.nombre + "</option>";
//                    } else {
//                        varEmpleados += "<option value='" + persona.id + "'>" + persona.nombre + "</option>";
//                    }
//                }
//                if (varClientes !== "<optgroup label='Cliente(s)'>") {
//                    varClientes += "</optgroup>";
//                } else {
//                    varClientes = "";
//                }
//
//                if (varEmpleados !== "<optgroup label='Empleado(s)'>") {
//                    varEmpleados += "</optgroup>";
//                } else {
//                    varEmpleados = "";
//                }
//                html = varEmpleados + varClientes;
//                $("#persona").html(html);
//                $("#personaActividad").html("");
//            }
//        },
//        error: function (err) {
//            alert(err);
//        }
//    });
//}