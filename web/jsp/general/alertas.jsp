<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="alertaExito" class="alert alert-success fade in" role="alert" hidden="true">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeExito">
        ${mensajeExito}
    </div>
</div>
<div id="alertaError" class="alert alert-danger fade in" role="alert" hidden="true">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeError">
        ${mensajeError}
    </div>
</div>
<div id="alertaAlerta" class="alert alert-warning fade in" role="alert" hidden="true">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <div id="mensajeAlerta">
        ${mensajeAlerta}
    </div>
</div>
<div id="alertaInformacion" class="alert alert-info fade in" role="alert" hidden="true">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
        $("#alertaAlerta").show();
    </script>
</c:if>
<c:if test="${not empty mensajeInformacion}">
    <script>
        $("#alertaInformacion").show();
    </script>
</c:if>