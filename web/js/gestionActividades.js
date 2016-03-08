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
});

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

function consultarPersonasProyecto(idProyecto) {
    $.ajax({
        type: "POST",
        url: "ActividadesController",
        dataType: "json",
        data: {proyecto: idProyecto, accion: "consultarPersonasProyecto"},
        success: function (data) {
            if (data !== undefined) {
                var varEmpleados = "<optgroup label='Empleado(s)'>";
                var varClientes = "<optgroup label='Cliente(s)'>";
                var varCargoCliente = "CLIENTE";
                var html = "";
                for (var persona in data) {
                    persona = data[persona];
                    if (persona.cargo.toLowerCase() === varCargoCliente.toLowerCase()) {
                        varClientes += "<option value='" + persona.id + "'>" + persona.nombre + "</option>";
                    } else {
                        varEmpleados += "<option value='" + persona.id + "'>" + persona.nombre + "</option>";
                    }
                }
                if (varClientes !== "<optgroup label='Cliente(s)'>") {
                    varClientes += "</optgroup>";
                } else {
                    varClientes = "";
                }

                if (varEmpleados !== "<optgroup label='Empleado(s)'>") {
                    varEmpleados += "</optgroup>";
                } else {
                    varEmpleados = "";
                }
                html = varEmpleados + varClientes;
                $("#persona").html(html);
                $("#personaActividad").html("");
            }
        },
        error: function (err) {
            alert(err);
        }
    });
}


function addItem() {
    $("#persona optgroup:selected").appendTo("#personaActividad");
    $("#persona option:selected").appendTo("#personaActividad");
    $("#personaActividad option").attr("selected", false);
}
function addallItems() {
    $("#persona optgroup").appendTo("#personaActividad");
    $("#persona option").appendTo("#personaActividad");
    $("#personaActividad option").attr("selected", false);
}
function removeItem() {
    $("#personaActividad option:selected").appendTo("#persona");
    $("#persona option").attr("selected", false);
}
function removeallItems() {
    $("#personaActividad optgroup").appendTo("#persona");
    $("#personaActividad option").appendTo("#persona");
    $("#persona option").attr("selected", false);
}


//function Validar() {
//    var mensaje = "";
//    if (document.getElementById("personaActividad").length === 0) {
//        mensaje = mensaje + "La lista de participantes en la actividad no debe estar vacía.";
//        document.getElementById("personaActividad").focus();
//    }
//    if (mensaje !== "") {
//        
//        var s = '<%=mensajeError%>'; 
//        var varError = "<div class='alert alert-danger fade in' role='alert'>\n\
//                        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n\
//                        <span aria-hidden='true>&times;</span></button>" + mensaje + "\n\
//                     </div>";
//        document.getElementById('idCampoUno').value = varError;
//        return false;
//    }
//}

function allValues() {

    var varObject = document.getElementById('personaActividad');
    for (var i = 0, l = varObject.options.length, o; i < l; i++)
    {
        o = varObject.options[i];
        o.selected = true;
    }
}