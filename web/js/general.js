function ocultarMensajes(){
    $('#msg')[0].style.display = 'none';
    $('#warn')[0].style.display = 'none';
    $('#error')[0].style.display = 'none';
    $('#info')[0].style.display = 'none';
}

function mostrarError(mensaje){
    ocultarMensajes();
    $('#error').html(mensaje);
    $('#error')[0].style.display = 'block';
}

function mostrarInfo(mensaje){
    ocultarMensajes();
    $('#info').html(mensaje);
    $('#info')[0].style.display = 'block';
}

function mostrarWarning(mensaje){
    ocultarMensajes();
    $('#warn').html(mensaje);
    $('#warn')[0].style.display = 'block';
}

function mostrarMensaje(mensaje){
    ocultarMensajes();
    $('#msg').html(mensaje);
    $('#msg')[0].style.display = 'block';
}

function crearVenta(){
    var datosValidos = validarObligatorios();
        if(datosValidos){
            var form = $('#formularioVentas');
            $.ajax({
                type: form.attr('method'),
                url: form.attr('action'),
                data: {cliente:$('#cliente').val(), empleado:$('#empleado').val(), accion:'crearVenta'}
            }).done(function (o) {
                try {
                    if(o !== undefined && o.resultado !== undefined){
                        if(o.resultado === 'ok'){
                            $('#fechaVenta').val(o.fecha);
                            $('#codigoVentaSpan').html(o.codigoVenta);
                            $('#agregarProductos')[0].style.display = 'block';
                            mostrarMensaje(o.mensaje);
                        } else {
                            mostrarError(o.mensaje);
                        }
                    } else {
                        mostrarError('Ocurrió un error creando la venta');
                    }
                } catch (err){
                    mostrarError(err);
                }
            });
        }
    
    return false;
}

