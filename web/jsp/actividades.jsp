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
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ActividadesController" method="POST" id="formularioActividades">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <center>
                            <h2>ACTIVIDADES</h2>
                        </center>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">FILTROS</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="proyecto">Proyecto</label>
                                        <select id="proyecto" name="proyecto" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="PROYECTOS">PROYECTOS</option>
                                        </select>                                        
                                    </div>
                                    
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="version">Versión</label>
                                        <select id="version" name="version" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="VERSIONES">VERSIONES</option>
                                        </select>                                        
                                    </div>
                                    
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="descripcion">Descripción</label>
                                        <input class="form-control" type="text" id="descripcion" name="descripcion" value="${descripcion}"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="estado">Estado</label>
                                        <select id="estado" name="estado" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="ESTADOS">ESTADOS</option>
                                        </select>                                        
                                    </div>
                                    
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="fecha">Fecha</label>
                                        <input type="date" id="fecha" name="fecha" class="form form-control"/>
                                    </div>
                                        
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="responsable">Documento persona</label>
                                        <input class="form-control" type="text" id="responsable" name="responsable" value="${responsable}"/>
                                    </div>
                                </div>
                                <br>
                            </div>                            
                        </div>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTablaActividades()">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="crear" value="crear">Crear</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaActividades"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
