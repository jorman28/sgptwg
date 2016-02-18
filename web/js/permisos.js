$(document).ready(function() {
    llenarTabla();
});

function nuevoPerfil() {
    $('#idPerfil').val('');
    $('#nombrePerfil').val('');
    llenarTabla();
}

function consultarPerfil(idPerfil) {
    $.ajax({
        type: "POST",
        url: "PermisosController",
        dataType: "json",
        data: {idPerfil: idPerfil, accion: "editar"},
        success: function(data) {
            if (data !== undefined) {
                $("#idPerfil").val(data.idPerfil !== undefined ? data.idPerfil : "");
                $("#nombrePerfil").val(data.nombrePerfil !== undefined ? data.nombrePerfil : "");
            }
        },
        error: function() {
        }
    });
}

function llenarTabla() {
    var nombrePerfil = $('#nombrePerfil').val() !== undefined && $('#nombrePerfil').val() !== "" ? $('#nombrePerfil').val() : null;
    $.ajax({
        type: "POST",
        url: "PermisosController",
        dataType: "html",
        data: {accion: "consultar", nombrePerfil: nombrePerfil},
        success: function(data) {
            if (data !== undefined) {
                $('#tablaPerfiles').html(data);
            }
        },
        error: function() {
        }
    });
}

function obtenerPermisos(idPerfil) {
    $.ajax({
        type: "POST",
        url: "PermisosController",
        dataType: "json",
        data: {accion: "consultarPermisos", idPerfil: idPerfil},
        success: function(data) {
            if (data !== undefined) {
                $("#permiso_1").prop("checked", data.permiso_1 !== undefined ? data.permiso_1 : false);
                $("#permiso_1_1").prop("checked", data.permiso_40 !== undefined ? data.permiso_40 : false);
                $("#permiso_1_2").prop("checked", data.permiso_41 !== undefined ? data.permiso_41 : false);
                $("#permiso_1_3").prop("checked", data.permiso_42 !== undefined ? data.permiso_42 : false);
                $("#permiso_1_4").prop("checked", data.permiso_43 !== undefined ? data.permiso_43 : false);
                $("#permiso_1_5").prop("checked", data.permiso_44 !== undefined ? data.permiso_44 : false);
                $("#permiso_2").prop("checked", data.permiso_2 !== undefined ? data.permiso_2 : false);
                $("#permiso_3").prop("checked", data.permiso_3 !== undefined ? data.permiso_3 : false);
                $("#permiso_3_1").prop("checked", data.permiso_28 !== undefined ? data.permiso_28 : false);
                $("#permiso_3_2").prop("checked", data.permiso_29 !== undefined ? data.permiso_29 : false);
                $("#permiso_5").prop("checked", data.permiso_5 !== undefined ? data.permiso_5 : false);
                $("#permiso_7").prop("checked", data.permiso_7 !== undefined ? data.permiso_7 : false);
                $("#permiso_8").prop("checked", data.permiso_8 !== undefined ? data.permiso_8 : false);
                $("#permiso_8_1").prop("checked", data.permiso_30 !== undefined ? data.permiso_30 : false);
                $("#permiso_8_2").prop("checked", data.permiso_31 !== undefined ? data.permiso_31 : false);
                $("#permiso_9").prop("checked", data.permiso_9 !== undefined ? data.permiso_9 : false);
                $("#permiso_10").prop("checked", data.permiso_10 !== undefined ? data.permiso_10 : false);
                $("#permiso_10_1").prop("checked", data.permiso_20 !== undefined ? data.permiso_20 : false);
                $("#permiso_10_2").prop("checked", data.permiso_21 !== undefined ? data.permiso_21 : false);
                $("#permiso_10_3").prop("checked", data.permiso_22 !== undefined ? data.permiso_22 : false);
                $("#permiso_10_4").prop("checked", data.permiso_23 !== undefined ? data.permiso_23 : false);
                $("#permiso_11").prop("checked", data.permiso_11 !== undefined ? data.permiso_11 : false);
                $("#permiso_11_1").prop("checked", data.permiso_24 !== undefined ? data.permiso_24 : false);
                $("#permiso_11_2").prop("checked", data.permiso_25 !== undefined ? data.permiso_25 : false);
                $("#permiso_11_3").prop("checked", data.permiso_26 !== undefined ? data.permiso_26 : false);
                $("#permiso_11_4").prop("checked", data.permiso_27 !== undefined ? data.permiso_27 : false);
                $("#permiso_12").prop("checked", data.permiso_12 !== undefined ? data.permiso_12 : false);
                $("#permiso_13").prop("checked", data.permiso_13 !== undefined ? data.permiso_13 : false);
                $("#permiso_14").prop("checked", data.permiso_14 !== undefined ? data.permiso_14 : false);
                $("#permiso_15").prop("checked", data.permiso_15 !== undefined ? data.permiso_15 : false);
                $("#permiso_15_1").prop("checked", data.permiso_17 !== undefined ? data.permiso_17 : false);
                $("#permiso_15_2").prop("checked", data.permiso_18 !== undefined ? data.permiso_18 : false);
                $("#permiso_15_3").prop("checked", data.permiso_19 !== undefined ? data.permiso_19 : false);
                $("#permiso_16").prop("checked", data.permiso_16 !== undefined ? data.permiso_16 : false);
                $("#permiso_16_1").prop("checked", data.permiso_32 !== undefined ? data.permiso_32 : false);
                $("#permiso_16_2").prop("checked", data.permiso_33 !== undefined ? data.permiso_33 : false);
                $("#permiso_16_3").prop("checked", data.permiso_34 !== undefined ? data.permiso_34 : false);
                $("#permiso_16_4").prop("checked", data.permiso_35 !== undefined ? data.permiso_35 : false);
                $("#permiso_16_5").prop("checked", data.permiso_36 !== undefined ? data.permiso_36 : false);
                $("#permiso_16_6").prop("checked", data.permiso_37 !== undefined ? data.permiso_37 : false);
                $("#permiso_16_7").prop("checked", data.permiso_38 !== undefined ? data.permiso_38 : false);
                $("#permiso_16_8").prop("checked", data.permiso_39 !== undefined ? data.permiso_39 : false);

                $("#perfilPermiso").val(idPerfil);
                $("#modalPermisos").modal('show');
            }
        },
        error: function() {
        }
    });
}

function encenderPermisosHijos(permiso) {
    var value = false;
    if ($('#' + permiso).is(':checked')) {
        value = true;
    }
    for (var i = 1; i <= 8; i++) {
        $("#" + permiso + "_" + i).prop("checked", value);
    }
}

function encenderPermisoPadre(permiso) {
    var value = false;
    for (var i = 1; i <= 8; i++) {
        if ($("#" + permiso + "_" + i).is(':checked')) {
            value = true;
            break;
        }
    }
    $("#" + permiso).prop("checked", value);
}