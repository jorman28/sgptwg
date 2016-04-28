$(document).ready(function () {
    llenarTablaEstados();
});
function nuevoEstado() {
    $('#id').val('');
    $('#tipoEstado').val('0');
    $('#nombre').val('');
    $('#estadoPrev').val('0');
    $('#estadoSig').val('0');
    $('#eFinal').val('0');
}

function consultarEstado(id) {
    $.ajax({
        type: "POST",
        url: "EstadosController",
        dataType: "json",
        data: {id: id, accion: "editar"},
        success: function (data) {
            console.log(data);
            if (data !== undefined) {
                $("#id").val(data.id !== undefined ? data.id : "");
                $("#tipoEstado").val(data.tipoEstado !== undefined ? data.tipoEstado : "");
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");

                //$("#estadoPrev").val(data.estadoPrev !== undefined ? data.estadoPrev : "");
                var html = "";
                html = "<option value='0'>SELECCIONE</option>";
                for (var estado in data.estadoPrev) {
                    var estadoPrevio = data.estadoPrevio.estadoPrevioId !== undefined ? data.estadoPrevio.estadoPrevioId : "0";
                    estado = data.estadoPrev[estado];
                    if (estadoPrevio === estado.id) {
                        html += "<option value='" + estado.id + "' selected>" + estado.nombre + "</option>";
                    } else {
                        html += "<option value='" + estado.id + "'>" + estado.nombre + "</option>";
                    }
                }
                $("#estadoPrev").html(html);
                
                html = "<option value='0'>SELECCIONE</option>";
                for (var estado in data.estadoSig) {
                    var estadoSiguiente = data.estadoSiguiente.estadoSiguienteId !== undefined ? data.estadoSiguiente.estadoSiguienteId : "0";
                    estado = data.estadoSig[estado];
                    if (estadoSiguiente === estado.id) {
                        html += "<option value='" + estado.id + "' selected>" + estado.nombre + "</option>";
                    } else {
                        html += "<option value='" + estado.id + "'>" + estado.nombre + "</option>";
                    }
                }
                $("#estadoSig").html(html);

                $("#eFinal").val(data.eFinal !== undefined ? data.eFinal : "");

                //se remueve el atributo disabled
                $("#estadoPrev").prop("disabled", false);
                $("#estadoSig").prop("disabled", false);
            }
        },
        error: function () {
        }
    });
}

function llenarTablaEstados() {
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var tipoEstado = $('#tipoEstado').val() !== undefined && $('#tipoEstado').val() !== "0" ? $('#tipoEstado').val() : null;
    var nombre = $('#nombre').val() !== undefined && $('#nombre').val() !== "" ? $('#nombre').val() : null;
    var estadoPrev = $('#estadoPrev').val() !== undefined && $('#estadoPrev').val() !== "" ? $('#estadoPrev').val() : null;
    var estadoSig = $('#estadoSig').val() !== undefined && $('#estadoSig').val() !== "" ? $('#estadoSig').val() : null;
    var eFinal = $('#eFinal').val() !== undefined && $('#eFinal').val() !== "" ? $('#eFinal').val() : null;
    $.ajax({
        type: "POST",
        url: "EstadosController",
        dataType: "html",
        data: {accion: "consultar", id: id, tipoEstado: tipoEstado, nombre: nombre, estadoPrev: estadoPrev, estadoSig: estadoSig, eFinal: eFinal},
        success: function (data) {
            if (data !== undefined) {
                $('#tablaEstados').html(data);
            }
        },
        error: function () {
            console.log('Error al cargar la tabla');
        }
    });
}

function ConsultarEstados(tipoEstado, busquedaAsincronica) {
    $.ajax({
        type: "POST",
        url: "EstadosController",
        dataType: "json",
        data: {tipoEstado: tipoEstado, busquedaAsincronica: busquedaAsincronica, accion: "ConsultarEstados"},
        success: function (data) {
            console.log(data);
            if (data !== undefined) {
                var html = "<option value='0'>SELECCIONE</option>";
                for (var estado in data) {
                    estado = data[estado];
                    html += "<option value='" + estado.id + "'>" + estado.nombre + "</option>";
                }
                //se remueve el atributo disabled
                $("#estadoPrev").prop("disabled", false);
                $("#estadoSig").prop("disabled", false);
                //se añaden los resultados encontrados en los objetos de estados previo y siguiente 
                $("#estadoPrev").html(html);
                $("#estadoSig").html(html);
            }
        },
        error: function (err) {
            alert(err);
        }
    });
}