$(document).ready(function() {
    llenarTablaEstados();
});
function nuevoEstado(){
    $('#id').val('');
    $('#tipoEstado').val('0');
    $('#nombre').val('');
}

function consultarEstado(id){
    $.ajax({
        type    :"POST",
        url     :"EstadosController",
        dataType:"json",
        data    :{id:id,accion:"editar"},
        success: function(data) {
            if(data !== undefined){
                $("#id").val(data.id !== undefined ? data.id : "");
                $("#tipoEstado").val(data.tipoEstado !== undefined ? data.tipoEstado : "");
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");
            }
        },
        error: function(){
        }
    });
}

function llenarTablaEstados(){
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var tipoEstado = $('#tipoEstado').val() !== undefined && $('#tipoEstado').val() !== "0" ? $('#tipoEstado').val() : null;
    var nombre = $('#nombre').val() !== undefined && $('#nombre').val() !== "" ? $('#nombre').val() : null;
    $.ajax({
        type    :"POST",
        url     :"EstadosController",
        dataType:"html",
        data    :{accion:"consultar",id:id,tipoEstado:tipoEstado,nombre:nombre},
        success: function(data) {
            if(data !== undefined){
                $('#tablaEstados').html(data);
            }
        },
        error: function(){
            console.log('Error al cargar la tabla');
        }
    });
}