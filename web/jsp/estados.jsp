<%-- 
    Document   : estados
    Created on : 08-jul-2015, 23:33:00
    Author     : Jorman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/estados.js"></script>
        <title>Estados</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./EstadosController" method="POST" id="formularioEstados">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <center>
                            <h2>REGISTRO DE ESTADOS</h2>
                            Los campos marcados con (*) son obligatorios
                        </center>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORACIÓN DEL ESTADO</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="nombre">*Tipo de Estado:</label>
                                        <select id="tipoEstado" name="tipoEstado" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="ACTIVIDADES">ACTIVIDADES</option>
                                            <option value="VERSIONES">VERSIONES</option>
                                        </select>                                        
                                    </div>
                                    
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="nombre">*Nombre:</label>
                                        <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}"/>
                                    </div>
                                </div>
                                <br>
                            </div>                            
                        </div>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTablaEstados()">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaEstados"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
