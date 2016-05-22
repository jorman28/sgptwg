<%-- 
    Document   : inicio
    Created on : 10/07/2015, 10:47:40 PM
    Author     : Andrés Giraldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/inicio.js"></script>
        <title>Inicio</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <div class="panel panel-info">
                        <div class="panel-heading">FILTROS</div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                    <label for="proyecto">Proyecto</label>
                                    <select class="form-control" id="proyecto" name="proyecto" onchange="consultarVersiones(this.value);">
                                        <option value="0">SELECCIONE</option>
                                        <c:forEach items="${proyectos}" var="tipo">
                                            <option value="${tipo.id}">${tipo.nombre}</option>
                                        </c:forEach>
                                    </select>                                         
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                    <label for="version">Versión</label>
                                    <select class="form-control" id="version" name="version">
                                        <option value="0">SELECCIONE</option>
                                    </select>                                     
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                    <label for="responsable">Reponsable</label>
                                    <input class="form-control" type="text" id="responsable" name="responsable"/>
                                    <input type="hidden" id="idPersona" name="idPersona"/>
                                </div>
                            </div>
                            <div class="row" align="center">
                                <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="cargarDatos()">Consultar</button>
                            </div>
                        </div>                            
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">ACTIVIDADES POR ESTADO</div>
                        <div class="panel-body">
                            <div class="row form-group" align="center">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div id="tablaActividadesPorEstado"></div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div id="graficaActividadesPorEstado" style="width:100%; height:140px;"></div>
                                </div>
                            </div>
                            <div class="row" align="center">
                                <button class="btn btn-default" type="button" onclick="generarReporte('estados')">Generar reporte</button>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">INVERSIÓN DE TIEMPO</div>
                        <div class="panel-body">
                            <div class="row form-group" align="center">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <div id="avanceProyectos" style="width:100%; height:300px;"></div>
                                </div>
                            </div>
                            <div class="row" align="center">
                                <button class="btn btn-default" type="button" onclick="generarReporte('avance')">Generar reporte</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
