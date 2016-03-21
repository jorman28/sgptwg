<%-- 
    Document   : autenticacion
    Created on : 22/02/2016, 09:08:43 PM
    Author     : Andres Giraldo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <title>Sin permisos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <center>
                        <h2>ACCESO DENEGADO</h2>
                        <h2>Usted no cuenta con los permisos necesarios para ingresar a esta página</h2>
                        <button class="btn btn-default" onclick="window.history.back();">Aceptar</button>
                    </center>
                </div>
            </div>
        </div>
    </body>
</html>
