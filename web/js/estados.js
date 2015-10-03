$(document).ready(function() {
    llenarTablaEstados();
});

function nuevoEstado(){
    $('#tipoEstado').val('0');
    $('#id').val('');
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
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");
            }
        },
        error: function(){
        }
    });
}

function llenarTablaEstados(){
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var nombre = $('#nombre').val() !== undefined && $('#nombre').val() !== "" ? $('#nombre').val() : null;
    $.ajax({
        type    :"POST",
        url     :"EstadosController",
        dataType:"html",
        data    :{accion:"consultar",id:id,nombre:nombre},
        success: function(data) {
            if(data !== undefined){
                $('#tablaEstados').html(data);
            }
        },
        error: function(){
        }
    });
}