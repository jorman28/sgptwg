<%-- 
    Document   : inicioSesion
    Created on : 10/07/2015, 10:51:23 PM
    Author     : Pipe
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <title>Inicio sesión</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
                    <form autocomplete="off" action="./InicioSesionController" method="POST" id="formularioInicio">
                        <div class="row">
                            <br/>
                            <div align="center">
                                <img src="images/logo.png" class="img-responsive img-thumbnail">
                            </div>
                            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4" align="center">
                                <h2>INICIO SESIÓN</h2>
                            </div>
                            <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                                <label for="documento">Usuario:</label> 
                                <input class="form-control" type="text" id="usuario" name="usuario" value="${usuario}"/>
                            </div>
                            <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                                <label for="clave">Clave:</label> 
                                <input class="form-control" type="password" id="clave" name="clave" value="${clave}" />
                            </div>
                            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4" align="center">
                                <button class="btn btn-default" type="submit" name="accion" value="ingresar">Ingresar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
