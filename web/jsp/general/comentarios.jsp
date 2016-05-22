<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="form-group">
    <div id="listaComentarios">${listaComentarios}</div>
</div>
<c:if test="${opcionComentar}">
    <div class="form-group">
        <textarea class="form-control" id="comentario" name="comentario" placeholder="Ingrese un comentario"></textarea>
        <div align="right">
            <button class="btn btn-default" type="button" name="accion" id="comentar" onclick="guardarComentario();">Comentar</button>
        </div>
    </div>
</c:if>
<input type="hidden" id="idComentario"/>
<div id="eliminacionComentarios" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                Realmente desea eliminar el comentario?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="eliminarComentario();">Si</button>
                <button type="button" class="btn btn-primary" onclick="$('#idComentario').val('');" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>