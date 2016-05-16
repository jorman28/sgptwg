function mostrarExito(mensaje) {
    $('#alertaAdvertencia').hide();
    $('#alertaError').hide();
    $('#alertaInformacion').hide();
    $('#mensajeExito').html(mensaje);
    $('#alertaExito').removeClass('in');
    $('#alertaExito').show();
    setTimeout(function() {
        mostrarAlerta('#alertaExito');
    }, 300);
}

function mostrarError(mensaje) {
    $('#alertaExito').hide();
    $('#alertaAdvertencia').hide();
    $('#alertaInformacion').hide();
    $('#mensajeError').html(mensaje);
    $('#alertaError').removeClass('in');
    $('#alertaError').show();
    setTimeout(function() {
        mostrarAlerta('#alertaError');
    }, 300);
}

function mostrarAdvertencia(mensaje) {
    $('#alertaExito').hide();
    $('#alertaError').hide();
    $('#alertaInformacion').hide();
    $('#mensajeAdvertencia').html(mensaje);
    $('#alertaAdvertencia').removeClass('in');
    $('#alertaAdvertencia').show();
    setTimeout(function() {
        mostrarAlerta('#alertaAdvertencia');
    }, 300);
}

function mostrarInformacion(mensaje) {
    $('#alertaExito').hide();
    $('#alertaAdvertencia').hide();
    $('#alertaError').hide();
    $('#mensajeInformacion').html(mensaje);
    $('#alertaInformacion').removeClass('in');
    $('#alertaInformacion').show();
    setTimeout(function() {
        mostrarAlerta('#alertaInformacion');
    }, 300);
}

function cerrarExito() {
    $('#alertaExito').removeClass('in');
    setTimeout(function() {
        cerrarAlerta('#alertaExito');
    }, 300);
}

function cerrarError() {
    $('#alertaError').removeClass('in');
    setTimeout(function() {
        cerrarAlerta('#alertaError');
    }, 300);
}

function cerrarInformacion() {
    $('#alertaInformacion').removeClass('in');
    setTimeout(function() {
        cerrarAlerta('#alertaInformacion');
    }, 300);
}

function cerrarAdvertencia() {
    $('#alertaAdvertencia').removeClass('in');
    setTimeout(function() {
        cerrarAlerta('#alertaAdvertencia');
    }, 300);
}

function mostrarAlerta(identificador) {
    $(identificador).addClass('in');
    $('body, html').animate({scrollTop: '0px'}, 400);
}

function cerrarAlerta(identificador) {
    $(identificador).hide();
}