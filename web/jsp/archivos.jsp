<%-- 
    Document   : archivos
    Created on : 25/03/2016, 02:20:04 PM
    Author     : Andres Giraldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/archivos.js"></script>
        <title>Archivos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <c:import url="/jsp/general/eliminacion.jsp"/>
                    <center>
                        <h2>ARCHIVOS</h2>
                    </center>
                    <input type="hidden" id="id" name="id" value="${id}" />
                </div>
            </div>
        </div>
    </body>
</html>
