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
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/personas.js"></script>
        <title>Personas</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
                    <form id="formularioPersonas" autocomplete="off" action="./PersonasController" method="POST"> 
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>CONSULTA DE PERSONAS</h2>
                        Puede filtrar por cualquiera de los campos
                        <br>
                        <br>
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
                                <label for="nombres">*Nombres</label>
                                <input class="form-control" type="text" id="nombres" name="nombres" value="${nombres}" maxlength="50"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="apellidos">*Apellidos</label>
                                <input class="form-control" type="text" id="apellidos" name="apellidos" value="${apellidos}"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="correo">*Correo</label>
                                <input class="form-control" type="text" id="correo" name="correo" value="${correo}" maxlength="50"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="cargo">*Cargo:</label>
                                <select class="form-control" id="cargo" name="cargo">
                                    <option value="0">SELECCIONE</option>
                                    <c:forEach items="${cargos}" var="car">
                                        <option value="${car.id}" <c:if test="${cargo == car.id}">selected</c:if> >${car.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="usuario">*Usuario:</label> 
                                <input class="form-control" type="text" id="usuario" name="usuario" value="${usuario}" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="perfil">*Perfil:</label> 
                                <select class="form-control" id="perfil" name="perfil">
                                    <option value="0">Seleccione</option>
                                    <c:forEach items="${perfiles}" var="per">
                                        <option value="${per.id}" <c:if test="${perfil == per.id}">selected</c:if>>${per.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row" align="center">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="consultarDatos()">Consultar</button>
                                <button class="btn btn-default" type="submit" name="nuevo" id="nuevo" value="nuevo">Crear</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
