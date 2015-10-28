<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty mensajeExito}">
    <div class="alert alert-success fade in" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        ${mensajeExito}
    </div>
</c:if>
<c:if test="${not empty mensajeError}">
    <div class="alert alert-danger fade in" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        ${mensajeError}
    </div>
</c:if>
<c:if test="${not empty mensajeAlerta}">
    <div class="alert alert-warning fade in" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        ${mensajeAlerta}
    </div>
</c:if>
<c:if test="${not empty mensajeInformacion}">
    <div class="alert alert-info fade in" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        ${mensajeInformacion}
    </div>
</c:if>