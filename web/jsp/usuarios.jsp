<%-- 
    Document   : usuarios
    Created on : 6/07/2015, 07:39:29 PM
    Author     : Pipe
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
        <title>Usuarios</title>
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
                    <form autocomplete="off" action="./UsuariosController" method="POST" id="formularioUsuarios">
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
                        <h1>USUARIOS</h1>
                        <input type="hidden" id="idPersona" name="idPersona" value="${idPersona}" />
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="tipoDocumento">*Tipo de documento:</label>
                                <select class="form-control" id="tipoDocumento" name="tipoDocumento" value="${tipoDocumento}">
                                    <option value="0">SELECCIONE</option>
                                    <c:forEach items="${tiposDocumentos}" var="tipo">
                                        <option value="${tipo.tipo}" <c:if test="${tipoDocumento == tipo.tipo}">selected</c:if> >${tipo.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="documento">*Documento:</label> 
                                <input class="form-control" type="text" id="documento" name="documento" value="${documento}"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="usuario">*Usuario:</label> 
                                <input class="form-control" type="text" id="usuario" name="usuario" value="${usuario}" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="perfil">*Perfil:</label> 
                                <select class="form-control" id="perfil" name="perfil">
                                    <option value="">SELECCIONE</option>
                                    <c:forEach items="${perfiles}" var="per">
                                        <option value="${per.id}" <c:if test="${perfil == per.id}">selected</c:if> >${per.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="clave">*Clave:</label> 
                                <input class="form-control" type="password" id="clave" name="clave" value="${clave}" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="clave">*Confirmar clave:</label> 
                                <input class="form-control" type="password" id="clave2" name="clave2" value="${clave}" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="activo">*Estado:</label> 
                                <select class="form-control" id="activo" name="activo">
                                    <option value="">SELECCIONE</option>
                                    <option value="T" <c:if test="${activo == 'T'}">selected</c:if> >Activo</option>
                                    <option value="F" <c:if test="${activo == 'F'}">selected</c:if> >Inactivo</option>
                                    </select>
                                </div>
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
                                        <th>Tipo documento</th>
                                        <th>Documento</th>
                                        <th>Usuario</th>
                                        <th>Perfil</th>
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${usuarios}" var="item">
                                    <tr>
                                        <td>${item.descripcionTipoDocumento}</td>
                                        <td>${item.documento}</td>
                                        <td>${item.usuario}</td>
                                        <td>${item.descripcionPerfil}</td>
                                        <td>
                                            <c:if test="${item.activo == 'T'}">
                                                Activo
                                            </c:if>
                                            <c:if test="${item.activo == 'F'}">
                                                Inactivo
                                            </c:if>
                                        </td>
                                        <td>
                                            <a class="btn btn-default" href="<%=request.getContextPath()%>/UsuariosController?accion=editar&idPersona=${item.idPersona}">Editar</a>
                                            <button class="btn btn-default" type="button" data-toggle="modal" data-target="#confirmationMessage" onclick="jQuery('#idPersona').val('${item.idPersona}');">Eliminar</button>
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
