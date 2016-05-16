<%-- 
    Document   : gestionActividades
    Created on : 3/12/2015, 09:17:12 PM
    Author     : Jorman Rincón
    Author     : Andrés Giraldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/gestionActividades.js"></script>
        <title>Gestionar Actividades</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <a id="help" href="#" title="Ayuda" class="linkAyuda"><i class="glyphicon glyphicon-question-sign"></i></a>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ActividadesController" method="POST" id="formularioActividades">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>GESTIÓN DE ACTIVIDADES</h2>
                        Los campos marcados con (*) son obligatorios<br><br>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN DE LA ACTIVIDAD</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="proyecto">*Proyecto</label>
                                        <select class="form-control" id="proyecto" name="proyecto" onchange="consultarVersiones(this.value);">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${proyectos}" var="proy">
                                                <option value="${proy.id}" <c:if test="${proyecto == proy.id}">selected</c:if> >${proy.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="version">*Version</label>
                                        <select id="version" name="version" class="form-control" onchange="actualizarFechas(this.value);">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${versiones}" var="ver">
                                                <option value="${ver.id}" <c:if test="${ver.id == version}">selected</c:if>>${ver.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="estado">*Estado</label>
                                        <select id="estado" name="estado" class="form-control">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${estados}" var="esta">
                                                <option value="${esta.id}" <c:if test="${esta.id == estado}">selected</c:if>>${esta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label for="nombre">*Nombre:</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" value="${nombre}" maxlength="80"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label for="descripcion">*Descripción:</label>
                                        <textarea class="form-control" id="descripcion" name="descripcion" maxlength="1000" rows="5">${descripcion}</textarea>
                                    </div>
                                </div>
                            </div>  
                        </div>

                        <c:if test="${not empty id}">
                            <div class="panel panel-info">
                                <div class="panel-heading">PARTICIPANTES</div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <label for="participante">Añadir participante:</label>
                                            <input class="form-control" type="text" id="participante" name="participante"/>
                                            <span id="limpiarParticipante" class="glyphicon glyphicon-remove-circle"></span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <label>Clientes</label>
                                            <ul class="list-group" id="clientesActividad">
                                                <c:if test="${empty clientesActividad}">
                                                    No se han agregado clientes al proyecto
                                                </c:if>
                                                <c:if test="${not empty clientesActividad}">
                                                    <c:forEach items="${clientesActividad}" var="item">
                                                        <li class="list-group-item" id="persona${item.idPersona}">
                                                            <div class="row">
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                                        ${item.nombre}
                                                                    </div>
                                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                                                                            ${item.tiempoEstimado}h - ${item.tiempoInvertido}h
                                                                        </div>
                                                                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                                                                            ${item.fechaInicio} - ${item.fechaFin}
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                    <span class="glyphicon glyphicon-time" style="cursor:pointer;" onclick="registrarTiempo(${item.idPersona});"></span>
                                                                    <span class="glyphicon glyphicon-calendar" style="cursor:pointer;" onclick="estimar(${item.idPersona}, '${item.fechaInicio}', '${item.fechaFin}', ${item.tiempoEstimado});"></span>
                                                                    <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(${item.idPersona});"></span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </c:if>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <label>Empleados</label>
                                            <ul class="list-group" id="empleadosActividad">
                                                <c:if test="${empty empleadosActividad}">
                                                    No se han agregado empleados al proyecto
                                                </c:if>
                                                <c:if test="${not empty empleadosActividad}">
                                                    <c:forEach items="${empleadosActividad}" var="item">
                                                        <li class="list-group-item" id="persona${item.idPersona}">
                                                            <div class="row">
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                                        ${item.nombre}
                                                                    </div>
                                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                                                                            ${item.tiempoEstimado}h - ${item.tiempoInvertido}h  
                                                                        </div>
                                                                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                                                                            ${item.fechaInicio} - ${item.fechaFin}
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                    <span class="glyphicon glyphicon-time" style="cursor:pointer;" onclick="registrarTiempo(${item.idPersona});"></span>
                                                                    <span class="glyphicon glyphicon-calendar" style="cursor:pointer;" onclick="estimar(${item.idPersona}, '${item.fechaInicio}', '${item.fechaFin}', ${item.tiempoEstimado});"></span>
                                                                    <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(${item.idPersona});"></span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </c:if>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty listaComentarios}">
                            <div class="panel panel-info">
                                <div class="panel-heading">COMENTARIOS</div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div id="divComentarios" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <c:import url="/jsp/general/comentarios.jsp"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <div class="row form-group" align="center">
                            <c:if test="${opcionGuardar == 'T'}">
                                <button class="btn btn-primary" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            </c:if>
                            <c:if test="${not empty id}">
                                <button class="btn btn-default" type="submit" name="accion" id="limpiarGestion" value="limpiarGestion">Limpiar</button>
                            </c:if>
                            <c:if test="${empty id}">
                                <button class="btn btn-default" type="submit" name="accion" id="limpiarCreacion" value="limpiarCreacion">Limpiar</button>
                            </c:if>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Volver a Actividades</button>
                        </div>
                    </form>
                    <div id="modalWarning" class="modal fade">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header warningModal">
                                    <span class="glyphicon glyphicon-warning-sign"></span> <b>WARNING!</b>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <div id="contenidoWarning"></div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="confirmarEstimacion" onclick="guardarPersonaActividad(true);">Continuar</button>
                                    <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idPersona" name="idPersona" readonly="true"/>
                    <div id="estimacionTiempo" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    Estimación de trabajo en actividad
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                            <label for="fechaInicio">*Fecha de inicio:</label>
                                            <input type="text" class="form-control" id="fechaInicio" name="fechaInicio" readonly="true"/>
                                        </div>
                                        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                            <label for="tiempo">*Tiempo estimado:</label>
                                            <input type="text" class="form-control" id="tiempo" name="tiempo"/>
                                        </div>
                                        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                            <label for="fechaFin">*Fecha de fin:</label>
                                            <input type="text" class="form-control" id="fechaFin" name="fechaFin" readonly="true"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" onclick="validarEstimacion();" class="btn btn-primary" id="guardarEstimacion" name="guardarEstimacion">Guardar</button>
                                    <button type="button" onclick="limpiarEstimacion();" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idHistorial" />
                    <div id="inversionTiempo" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    Reporte de tiempo
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                            <label for="fechaTrabajo">*Fecha trabajada:</label>
                                            <input type="text" class="form-control" id="fechaTrabajo" name="fechaTrabajo" readonly="true"/>
                                        </div>
                                        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                            <label for="tiempoTrabajado">*Tiempo trabajado:</label>
                                            <input type="text" class="form-control" id="tiempoTrabajado" name="tiempoTrabajado" />
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <label for="detalleTrabajo">*Detalle de trabajo:</label>
                                            <textarea class="form-control" id="detalleTrabajo" name="detalleTrabajo" maxlength="1000" rows="5"></textarea>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <label for="detalleTrabajo">Historial de trabajo</label>
                                            <ul class="list-group" id="historialTrabajo">
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="guardarTiempo" onclick="guardarReporteTiempo();">Guardar</button>
                                    <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
