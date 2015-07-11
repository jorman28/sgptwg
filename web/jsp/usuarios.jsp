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
        <title>Usuario</title>
    </head>
    <body>
        <div class="container-fluid">
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
