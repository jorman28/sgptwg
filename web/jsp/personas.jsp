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
                        <center>
                            <c:if test="${formulario == 'CREACION'}">
                                <h2>REGISTRO DE PERSONAS</h2>
                            </c:if>
                            <c:if test="${formulario == 'EDICION'}">
                                <h2>EDICIÓN DE PERSONAS</h2>
                            </c:if>
                            Los campos marcados con asterisco (*) son obligatorios.
                        </center>
                        <input type="hidden" id="idPersona" name="idPersona" value="${idPersona}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN PERSONAL</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="documento">*Documento:</label> 
                                        <input class="form-control" type="text" id="documento" name="documento" value="${documento}"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="tipoDocumento">*Tipo de documento:</label>
                                        <select class="form-control" id="tipoDocumento" name="tipoDocumento">
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
                                        <label for="telefono">Teléfono</label>
                                        <input class="form-control" type="text" id="telefono" name="telefono" value="${telefono}" maxlength="15" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="celular">Celular</label>
                                        <input class="form-control" type="text" id="celular" name="celular" value="${celular}" maxlength="15"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="correo">*Correo</label>
                                        <input class="form-control" type="text" id="correo" name="correo" value="${correo}" maxlength="50"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="direccion">*Dirección</label>
                                        <input class="form-control" type="text" id="direccion" name="direccion" value="${direccion}" maxlength="50"/>
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
                                </div>
                            </div>
                        </div>
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
                                            <option value="0">Seleccione</option>
                                            <c:forEach items="${perfiles}" var="per">
                                                <option value="${per.id}" <c:if test="${perfil == per.id}">selected</c:if>>${per.nombre}</option>
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
                                <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiar()">Limpiar</button>
                                <button class="btn btn-default" type="submit" name="accion" id="cancelar" value="cancelar">Cancelar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
