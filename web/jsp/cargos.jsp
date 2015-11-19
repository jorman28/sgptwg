<%-- 
    Document   : cargos
    Created on : 2/10/2015, 12:32:47 PM
    Author     : erikasta07
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/cargos.js"></script>
        <title>Cargos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2" id="contenido">
                    <form id="formularioCargos" autocomplete="off" action="./CargosController" method="POST"> 
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>CARGOS</h2>
                        Los campos marcados con (*) son obligatorios<br/><br/>
                        <input type="hidden" id="idCargo" name="idCargo" value="${idCargo}" />
                        <div class="row form-group">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="descripcion">*Nombre del cargo</label>
                                <input class="form-control" id="descripcion" name="descripcion" maxlength="50" value="${descripcion}" type="text" />
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla()">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="nuevoCargo();">Limpiar</button>
                        </div>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaCargos"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
