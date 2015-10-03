$(document).ready(function() {
    llenarTabla();
});

function nuevoPerfil(){
    $('#idPerfil').val('');
    $('#nombrePerfil').val('');
    llenarTabla();
}

function consultarPerfil(idPerfil){
    $.ajax({
        type    :"POST",
        url     :"PermisosController",
        dataType:"json",
        data    :{idPerfil:idPerfil,accion:"editar"},
        success: function(data) {
            if(data !== undefined){
                $("#idPerfil").val(data.idPerfil !== undefined ? data.idPerfil : "");
                $("#nombrePerfil").val(data.nombrePerfil !== undefined ? data.nombrePerfil : "");
            }
        },
        error: function(){
        }
    });
}

function llenarTabla(){
    var nombrePerfil = $('#nombrePerfil').val() !== undefined && $('#nombrePerfil').val() !== "" ? $('#nombrePerfil').val() : null;
    $.ajax({
        type    :"POST",
        url     :"PermisosController",
        dataType:"html",
        data    :{accion:"consultar",nombrePerfil:nombrePerfil},
        success: function(data) {
            if(data !== undefined){
                $('#tablaPerfiles').html(data);
            }
        },
        error: function(){
        }
    });
}

function obtenerPermisos(idPerfil){
    $.ajax({
        type    :"POST",
        url     :"PermisosController",
        dataType:"json",
        data    :{accion:"consultarPermisos",idPerfil:idPerfil},
        success: function(data) {
            if(data !== undefined){
                $("#permiso_1").prop("checked", data.permiso_1 !== undefined ? data.permiso_1 : false);
                $("#permiso_2").prop("checked", data.permiso_2 !== undefined ? data.permiso_2 : false);
                $("#permiso_3").prop("checked", data.permiso_3 !== undefined ? data.permiso_3 : false);
                $("#permiso_5").prop("checked", data.permiso_5 !== undefined ? data.permiso_5 : false);
                $("#permiso_7").prop("checked", data.permiso_7 !== undefined ? data.permiso_7 : false);
                $("#permiso_8").prop("checked", data.permiso_8 !== undefined ? data.permiso_8 : false);
                $("#permiso_9").prop("checked", data.permiso_9 !== undefined ? data.permiso_9 : false);
                $("#permiso_10").prop("checked", data.permiso_10 !== undefined ? data.permiso_10 : false);
                $("#permiso_11").prop("checked", data.permiso_11 !== undefined ? data.permiso_11 : false);
                $("#permiso_12").prop("checked", data.permiso_12 !== undefined ? data.permiso_12 : false);
                $("#permiso_13").prop("checked", data.permiso_13 !== undefined ? data.permiso_13 : false);
                $("#permiso_14").prop("checked", data.permiso_14 !== undefined ? data.permiso_14 : false);
                $("#permiso_15").prop("checked", data.permiso_15 !== undefined ? data.permiso_15 : false);
                $("#permiso_16").prop("checked", data.permiso_16 !== undefined ? data.permiso_16 : false);
                
                $("#perfilPermiso").val(idPerfil);
                $("#modalPermisos").modal('show');
            }
        },
        error: function(){
        }
    });
}