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
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/business-styles.css">
        <title>Estados</title>
    </head>
    <body>
        <div class="container-fluid">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li><a href="<%=request.getContextPath()%>/PaginaInicioController">Inicio</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Seguridad <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="<%=request.getContextPath()%>/UsuariosController">Usuarios</a></li>
                                    <li><a href="#">Permisos</a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Configuración <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="<%=request.getContextPath()%>/PersonasController">Personas</a></li>
                                    <li><a href="#">Cargos</a></li>
                                    <li><a href="#">Estados</a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Proyectos <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Versiones</a></li>
                                    <li><a href="#">Actividades</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Documentación</a></li>
                            <li><a href="#">Reportes</a></li>
                            <li><a href="#">Ayuda</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Cerrar sesión <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="<%=request.getContextPath()%>/InicioSesionController">Continuar</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <c:if test="${mensajeError != null and mensajeError != ''}">
                <div class="alert alert-danger fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${mensajeError}
                </div>
            </c:if>
            <c:if test="${mensajeAlerta != null and mensajeAlerta != ''}">
                <div class="alert alert-warning fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${mensajeAlerta}
                </div>
            </c:if>
            <c:if test="${mensajeExito != null and mensajeExito != ''}">
                <div class="alert alert-success fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${mensajeExito}
                </div>
            </c:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
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
                        <h1>ESTADOS</h1>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="tipoDocumento">*Tipo de Estado:</label>
                                <select class="form-control" id="tipoEstado" name="tipoEstado" value="${tipoEstado}">
                                    <option value="0">SELECCIONE</option>
                                    <option value="Estado de Actividad">Estado de Actividad</option>
                                    <option value="Estado de Versión">Estado de Versión</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="nombre">*Nombre:</label> 
                                <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}"/>
                            </div>
                            <br>
                            <div class="row" align="center">
                                <button class="btn btn-default" type="submit" name="accion" id="consultar" value="consultar">Consultar</button>
                                <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                                <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                            </div>
                            <br/>
                            <table class="table table-striped table-hover table-condensed">
                                <thead>
                                    <tr>
                                        <th>Tipo de Estado</th>
                                        <th>Nombre</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${estadosActividades}" var="item">
                                        <tr>
                                            <td>Estado Actividad</td>
                                            <td>${item.nombre}</td>
                                            <td>
                                                <a class="btn btn-default" href="<%=request.getContextPath()%>/EstadosController?accion=editar&id=${item.id}">Editar</a>
                                                <button class="btn btn-default" type="button" data-toggle="modal" data-target="#confirmationMessage" onclick="jQuery('#id').val('${item.id}');">Eliminar</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>