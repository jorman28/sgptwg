<%-- 
    Document   : actividades
    Created on : 2/12/2015, 11:18:11 PM
    Author     : erikasta07
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/actividades.js"></script>
        <title>Actividades</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <a id="help" href="./manuales/Ayuda_Actividades.pdf" target="_blank" title="Ayuda" class="linkAyuda"><i class="glyphicon glyphicon-question-sign"></i></a>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ActividadesController" method="POST" id="formularioActividades">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>ACTIVIDADES</h2>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">FILTROS</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="proyecto">Proyecto</label>
                                        <select class="form-control" id="proyecto" name="proyecto" onchange="consultarVersiones(this.value);">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${proyectos}" var="proy">
                                                <option value="${proy.id}" <c:if test="${proyecto == proy.id}">selected</c:if> >${proy.nombre}</option>
                                            </c:forEach>
                                        </select>                                         
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="version">Versión</label>
                                        <select class="form-control" id="version" name="version">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${versiones}" var="vers">
                                                <option value="${vers.id}" <c:if test="${version == vers.id}">selected</c:if>>${vers.nombre}</option>
                                            </c:forEach>
                                        </select>                                     
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="descripcion">Contiene</label>
                                        <input class="form-control" type="text" id="descripcion" name="descripcion" value="${descripcion}" maxlength="1000"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="estado">Estado</label>
                                        <select class="form-control" id="estado" name="estado">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${estados}" var="tipo">
                                                <option value="${tipo.id}" <c:if test="${estado == tipo.id}">selected</c:if> >${tipo.nombre}</option>
                                            </c:forEach>
                                        </select>                                       
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="fecha">Fecha</label>
                                        <input type="text" id="fecha" name="fecha" class="form form-control"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="responsable">Responsable</label>
                                        <input class="form-control" type="text" id="nombreResponsable" name="nombreResponsable" value="${nombreResponsable}" <c:if test="${!opcionConsultar}">readonly="true"</c:if>/>
                                        <input class="form-control" type="hidden" id="responsable" name="responsable" value="${responsable}"/>
                                    </div>
                                </div>
                                <br>
                            </div>                            
                        </div>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTablaActividades()">Consultar</button>
                            <c:if test="${opcionGuardar}">
                                <button class="btn btn-default" type="submit" name="accion" id="crearActividad" value="crearActividad">Crear</button>
                            </c:if>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                            <button class="btn btn-default" type="button" data-toggle="modal" data-target="#generacionReporte">Generar reporte</button>
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaActividades"></div>
                        </div>
                        <button hidden type="submit" name="accion" id="gestionarActividad" value="gestionarActividad"></button>
                        <button hidden type="submit" name="accion" id="duplicarActividad" value="duplicarActividad"></button>
                    </form>
                    <div id="generacionReporte" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    Selección de reporte
                                </div>
                                <div class="modal-body">
                                    Elija el tipo de reporte que desea generar
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" id="tipoSimple" name="tipoReporte" value="simple"/>
                                                    Listado general de actividades
                                                </label>
                                            </div>
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" id="tipoDetallado" name="tipoReporte" value="detallado"/>
                                                    Listado detallado de actividades
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" onclick="generarReporte();">Generar</button>
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
