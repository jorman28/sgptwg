$(document).ready(function() {
    llenarTabla();
});

function nuevoUsuario(){
    $('#identificacion').val('');
    $('#tipoDocumento').val('00');
    $('#usuario').val('');
    $('#clave').val('');
    $('#perfil').val('0');
}

function consultarUsuario(idPersona){
    $.ajax({
        type    :"POST",
        url     :"UsuariosController",
        dataType:"json",
        data    :{idPersona:idPersona,accion:"editar"},
        success: function(data) {
            if(data !== undefined){
                $("#idPersona").val(data.idPersona !== undefined ? data.idPersona : "");
                $("#documento").val(data.documento !== undefined ? data.documento : "");
                $("#documento").attr('disabled', true);
                $("#tipoDocumento").val(data.tipoDocumento !== undefined ? data.tipoDocumento : 0);
                $("#tipoDocumento").attr('disabled', true);
                $("#usuario").val(data.usuario !== undefined ? data.usuario : "");
                $("#perfil").val(data.perfil !== undefined ? data.perfil : "");
                $("#activo").val(data.activo !== undefined ? data.activo : "F");
            }
        },
        error: function(){
        }
    });
}

function llenarTabla(){
    var documento = $('#documento').val() !== undefined && $('#documento').val() !== "" ? $('#idPersona').val() : null;
    var tipoDocumento = $('#tipoDocumento').val() !== undefined && $('#tipoDocumento').val() !== "0" ? $('#tipoDocumento').val() : null;
    var usuario = $('#usuario').val() !== undefined && $('#usuario').val() !== "" ? $('#usuario').val() : null;
    var perfil = $('#perfil').val() !== undefined && $('#perfil').val() !== "0" ? $('#perfil').val() : null;
    var activo = $('#activo').val() !== undefined && $('#perfil').val() !== "0" ? $('#activo').val() : null;
    $.ajax({
        type    :"POST",
        url     :"UsuariosController",
        dataType:"html",
        data    :{accion:"consultar",documento:documento,tipoDocumento:tipoDocumento,usuario:usuario,perfil:perfil,activo:activo},
        success: function(data) {
            if(data !== undefined){
                $('#tablaUsuarios').html(data);
            }
        },
        error: function(){
        }
    });
}