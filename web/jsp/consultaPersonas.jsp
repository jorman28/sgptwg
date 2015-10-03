<%-- 
    Document   : consultaPersonas
    Created on : 1/10/2015, 07:48:48 PM
    Author     : Pipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/tab.jpg" rel='shortcut icon' type='image/jpeg'>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/general.js"></script>
        <script type="text/javascript" src="js/personas.js"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/business-styles.css">
        <title>Personas</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
                    <form id="formularioPersonas" autocomplete="off" action="./PersonasController" method="POST"> 
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
                            <h2>PERSONAS</h2>
                        </center>
                        <input type="hidden" id="idPersona" name="idPersona" value="${idPersona}" />
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="tipoDocumento">Tipo de documento:</label>
                                <select class="form-control" id="tipoDocumento" name="tipoDocumento" value="${tipoDocumento}">
                                    <option value="0">SELECCIONE</option>
                                    <c:forEach items="${tiposDocumentos}" var="tipo">
                                        <option value="${tipo.tipo}" <c:if test="${tipoDocumento == tipo.tipo}">selected</c:if> >${tipo.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="documento">Documento:</label> 
                                <input class="form-control" type="text" id="documento" name="documento" value="${documento}"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="nombres">Nombre:</label> 
                                <input class="form-control" type="text" id="nombres" name="nombres" value="${nombres}" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="apellidos">Apellidos:</label> 
                                <input class="form-control" type="text" id="apellidos" name="apellidos" value="${apellidos}" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="tipoPersona">Tipo de persona:</label> 
                                <select class="form-control" id="tipoPersona" name="perfil">
                                    <option value="">SELECCIONE</option>
                                    <option value="EMPLEADO" <c:if test="${tipoPersona == "EMPLEADO"}">selected</c:if> >EMPLEADO</option>
                                    <option value="CLIENTE" <c:if test="${tipoPersona == "CLIENTE"}">selected</c:if> >CLIENTE</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="correo">Correo:</label> 
                                <input class="form-control" type="text" id="correo" name="correo" value="${correo}" />
                            </div>
                        </div>
                        <br>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla()">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                        <br/>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaUsuarios"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
