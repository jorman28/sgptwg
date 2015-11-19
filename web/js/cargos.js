$(document).ready(function() {
    llenarTabla();
});

function nuevoCargo(){
    $('#idCargo').val('');
    $('#descripcion').val('');
}

function consultarCargo(id){
    $.ajax({
        type    :"POST",
        url     :"CargosController",
        dataType:"json",
        data    :{idCargo:id, accion:"editar"},
        success: function(data) {
            if(data !== undefined){
                $("#idCargo").val(data.id !== undefined ? data.id : "");
                $("#descripcion").val(data.nombre !== undefined ? data.nombre : "");
            }
        },
        error: function(){
        }
    });
}

function llenarTabla(){
    var descripcion = $('#descripcion').val() !== undefined && $('#descripcion').val() !== "" ? $('#descripcion').val() : null;
    $.ajax({
        type    :"POST",
        url     :"CargosController",
        dataType:"html",
        data    :{accion:"consultar", descripcion:descripcion},
        success: function(data) {
            if(data !== undefined){
                $('#tablaCargos').html(data);
            }
        },
        error: function(){
        }
    });
}