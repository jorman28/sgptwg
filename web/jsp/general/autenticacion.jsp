<%-- 
    Document   : autenticacion
    Created on : 22/02/2016, 09:08:43 PM
    Author     : Pipe
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
        <h1>ACCESO DENEGADO</h1>
        <h2>Usted no cuenta con los permisos necesarios para ingresar a esta página</h2>
        <button class="btn btn-default" onclick="window.history.back();">Aceptar</button>
    </body>
</html>
