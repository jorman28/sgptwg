<%-- 
    Document   : inicio
    Created on : 10/07/2015, 10:47:40 PM
    Author     : Pipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <title>Inicio</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <div align="center">
                        <!--<h2>PÁGINA DE INICIO</h2>-->
                        <img src="images/navegacional.png" class="img-responsive img-thumbnail">
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
