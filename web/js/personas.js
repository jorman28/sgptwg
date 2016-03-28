$(document).ready(function() {
    consultarDatos();
});

jQuery(function () {
    $("#telefono").keypress(function (e) {
        if (e.which !== 8 && e.which !== 0 && (e.which < 48 || e.which > 57)) {
            //alert("Digits Only");
            return false;
        }
    });
    
    $("#celular").keypress(function (e) {
        if (e.which !== 8 && e.which !== 0 && (e.which < 48 || e.which > 57)) {
            //alert("Digits Only");
            return false;
        }
    });
});

function editarPersona(idPersona) {
    $('#idPersona').val(idPersona);
    $('#editar').click();
}

function consultarDatos() {
    var tipoDocumento = $('#tipoDocumento').val() !== undefined && $('#tipoDocumento').val() !== "0" ? $('#tipoDocumento').val() : null;
    var documento = $('#documento').val() !== undefined && $('#documento').val() !== "" ? $('#documento').val() : null;
    var nombres = $('#nombres').val() !== undefined && $('#nombres').val() !== "" ? $('#nombres').val() : null;
    var apellidos = $('#apellidos').val() !== undefined && $('#apellidos').val() !== "" ? $('#apellidos').val() : null;
    var correo = $('#correo').val() !== undefined && $('#correo').val() !== "" ? $('#correo').val() : null;
    var usuario = $('#usuario').val() !== undefined && $('#usuario').val() !== "" ? $('#usuario').val() : null;
    var cargo = $('#cargo').val() !== undefined && $('#cargo').val() !== "0" ? $('#cargo').val() : null;
    var perfil = $('#perfil').val() !== undefined && $('#perfil').val() !== "0" ? $('#perfil').val() : null;
    $.ajax({
        type: "POST",
        url: "PersonasController",
        dataType: "html",
        data: {accion: "consultar", documento: documento, tipoDocumento: tipoDocumento, nombres: nombres, apellidos: apellidos, correo: correo, cargo: cargo, usuario: usuario, perfil: perfil},
        success: function (data) {
            if (data !== undefined) {
                $('#tablaPersonas').html(data);
            }
        },
        error: function () {
        }
    });
}