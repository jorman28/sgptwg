<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="alertaExito" class="alert alert-success fade in" hidden="true">
    <button type="button" class="close" aria-label="Close" onclick="cerrarExito();"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeExito">
        ${mensajeExito}
    </div>
</div>
<div id="alertaError" class="alert alert-danger fade in" hidden="true">
    <button type="button" class="close" aria-label="Close" onclick="cerrarError();"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeError">
        ${mensajeError}
    </div>
</div>
<div id="alertaAdvertencia" class="alert alert-warning fade in" hidden="true">
    <button type="button" class="close" aria-label="Close" onclick="cerrarAdvertencia();"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeAdvertencia">
        ${mensajeAlerta}
    </div>
</div>
<div id="alertaInformacion" class="alert alert-info fade in" hidden="true">
    <button type="button" class="close" aria-label="Close" onclick="cerrarInformacion();"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeInformacion">
        ${mensajeInformacion}
    </div>
</div>
<c:if test="${not empty mensajeExito}">
    <script>
        $("#alertaExito").show();
    </script>
</c:if>
<c:if test="${not empty mensajeError}">
    <script>
        $("#alertaError").show();
    </script>
</c:if>
<c:if test="${not empty mensajeAlerta}">
    <script>
        $("#alertaAdvertencia").show();
    </script>
</c:if>
<c:if test="${not empty mensajeInformacion}">
    <script>
        $("#alertaInformacion").show();
    </script>
</c:if>