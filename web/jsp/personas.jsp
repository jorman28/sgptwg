<%-- 
    Document   : personas
    Created on : 8/07/2015, 09:33:56 PM
    Author     : Erika Jhoana
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
        <script type="text/javascript" src="js/personas.js"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/business-styles.css">
        <title>Personas</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
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
                            <h2>REGISTRO DE PERSONAS</h2>
                            Los campos marcados con asterisco (*) son obligatorios.
                        </center>
                        <input type="hidden" id="idPersona" name="idPersona" value="${idPersona}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORACIÓN PERSONAL</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="documento">*Documento:</label> 
                                        <input class="form-control" type="text" id="documento" name="documento" value="${documento}"/>
                                    </div>
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
                                        <label for="nombres">* Nombres</label>
                                        <input class="form-control" type="text" id="nombres" name="nombres" value="${nombres}" maxlength="50"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="apellidos">* Apellidos</label>
                                        <input class="form-control" type="text" id="apellidos" name="apellidos" value="${apellidos}"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="telefono">* Teléfono</label>
                                        <input class="form-control" type="text" id="telefono" name="telefono" value="${telefono}" maxlength="15" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="celular">Celular</label>
                                        <input class="form-control" type="text" id="celular" name="celular" value="${celular}" maxlength="15"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="correo">Correo</label>
                                        <input class="form-control" type="text" id="correo" name="correo" value="${correo}" maxlength="50"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="direccion">* Dirección</label>
                                        <input class="form-control" type="text" id="direccion" name="direccion" value="${direccion}" maxlength="50"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--<div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN EXTENDIDA</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label>* Tipo de persona</label>
                                        <select class="form-control" id="tipoPersona" name="tipoPersona">
                                            <option value="0">Seleccione</option>
                                            <option value="1">Empleado</option>
                                            <option value="2">Cliente</option>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label>* Cargo</label>
                                        <select class="form-control" id="Id_Cargo" name="Id_Cargo">
                                            <option value="0">Seleccione</option>
                        <%--<c:forEach items="${cargos}" var="cargo">--%>
                            <option value="${cargo.id}">${cargo.nombre}</option>
                        <%--</c:forEach>--%>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label for="fechaInicio">* Fecha de registro</label>
                    <input class="form-control" id="fechaInicio" name="fechaInicio" readonly type="text" value="${fechaInicio}" />
                </div>
            </div>
        </div>
    </div>-->
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN DE USUARIO</div>
                            <div class="panel-body">
                                <div class="row">
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
                                </div>
                            </div>
                        </div>
                        <div class="row" align="center">
                            <div class="col-lg-12">
                                <button class="btn btn-default" type="submit" value="guardar" name="accion">Guardar</button>
                                <c:if test="${formulario == 'CREACION'}">
                                    <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiar()">Limpiar</button>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
