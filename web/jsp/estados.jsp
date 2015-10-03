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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/tab.jpg" rel='shortcut icon' type='image/jpeg'>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/general.js"></script>
        <script type="text/javascript" src="js/estados.js"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/business-styles.css">
        <title>Estados</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:if test="${not empty mensajeError}">
                <div class="alert alert-danger fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${mensajeError}
                </div>
            </c:if>
            <c:if test="${not empty mensajeAlerta}">
                <div class="alert alert-warning fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${mensajeAlerta}
                </div>
            </c:if>
            <c:if test="${not empty mensajeExito}">
                <div class="alert alert-success fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${mensajeExito}
                </div>
            </c:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2" id="contenido">
                    <form autocomplete="off" action="./EstadosController" method="POST" id="formularioEstados">
                        <div id="confirmationMessage" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <!-- dialog body -->
                                    <div class="modal-body">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        Realmente desea eliminar el registro?
                                    </div>
                                    <!-- dialog buttons -->
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary" name="accion" id="eliminar" value="eliminar">Si</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">No</button>
                                    </div>
                                </div>
                            </div>
                        </div>
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
