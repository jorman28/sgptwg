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
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/usuarios.js"></script>
        <title>Usuarios</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./UsuariosController" method="POST" id="formularioUsuarios">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>USUARIOS</h2>
                        Los campos marcados con (*) son obligatorios
                        <br/>
                        <br/>
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
                            <c:if test="${opcionConsultar == 'T'}">
                                <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla()">Consultar</button>
                            </c:if>
                            <c:if test="${opcionGuardar == 'T'}">
                                <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            </c:if>
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
