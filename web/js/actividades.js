$(document).ready(function() {
    llenarTablaActividades();
});

function consultarActividad(id){
    $.ajax({
        type    :"POST",
        url     :"ActividadesController",
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

function llenarTablaActividades(){
    var id = $('#id').val() !== undefined && $('#id').val() !== "" ? $('#id').val() : null;
    var proyecto = $('#proyecto').val() !== undefined && $('#proyecto').val() !== "0" ? $('#proyecto').val() : null;
    var version = $('#version').val() !== undefined && $('#version').val() !== "0" ? $('#version').val() : null;
    var descripcion = $('#descripcion').val() !== undefined && $('#descripcion').val() !== "" ? $('#descripcion').val() : null;
    var estado = $('#estado').val() !== undefined && $('#estado').val() !== "0" ? $('#estado').val() : null;
    var fecha = $('#fecha').val() !== undefined && $('#fecha').val() !== "" ? $('#fecha').val() : null;
    var responsable = $('#responsable').val() !== undefined && $('#responsable').val() !== "" ? $('#responsable').val() : null;
    $.ajax({
        type    :"POST",
        url     :"ActividadesController",
        dataType:"html",
        data    :{accion:"consultar",id:id,proyecto:proyecto,version:version,descripcion:descripcion,estado:estado,fecha:fecha,responsable:responsable},
        success: function(data) {
            if(data !== undefined){
                $('#tablaActividades').html(data);
            }
        },
        error: function(){
            console.log('Error al cargar la tabla');
        }
    });
}