<%-- 
    Document   : actividadesPorEstado
    Created on : 21/03/2016, 04:01:43 PM
    Author     : Andrés Giraldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/actividadesPorEstado.js"></script>
        <title>Actividades por estado</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ActividadesPorEstadoController" method="POST" id="formularioActividades">
                        <center>
                            <h2>ACTIVIDADES POR ESTADO</h2>
                        </center>
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
                                <br>
                                <div class="row" align="center">
                                    <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla()">Consultar</button>
                                </div>
                            </div>                            
                        </div>
                        <div class="row" align="center">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div id="graficaActividadesPorEstado" style="width:100%; height:200px;"></div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div id="tablaActividadesPorEstado"></div>
                            </div>
                        </div>
                        <br>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="reporte" value="reporte" onclick="generarReporte()">Generar reporte</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
