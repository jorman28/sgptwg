<%-- 
    Document   : personas
    Created on : 8/07/2015, 09:33:56 PM
    Author     : Erika Jhoana
--%>

<%@page import="com.twg.persistencia.daos.PerfilesDao"%>
<%@page import="com.twg.persistencia.beans.PerfilesBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.twg.persistencia.daos.TiposDocumentosDao"%>
<%@page import="com.twg.persistencia.beans.TiposDocumentosBean"%>
<%@page import="java.util.List"%>
<%@page import="com.twg.persistencia.daos.CargosDao"%>
<%@page import="com.twg.persistencia.beans.CargosBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
    List<CargosBean> cargos = new CargosDao().consultarCargos(null);
    request.setAttribute("cargos", cargos);

    List<TiposDocumentosBean> tiposDocumento = new TiposDocumentosDao().consultarTiposDocumentos();
    request.setAttribute("tiposDocumento", tiposDocumento);

    List<PerfilesBean> perfiles = new PerfilesDao().consultarPerfiles();
    request.setAttribute("perfiles", perfiles);

    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    request.setAttribute("fechaInicio", formatoFecha.format(new Date()));

%>
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
                                        <label>* Documento</label>
                                        <input class="form-control" id="documento" name="documento" maxlength="15" value="${documento}" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label>* Tipo de documento</label>
                                        <select class="form-control" id="tipoDocumento" name="tipoDocumento" >
                                            <option value="0">Seleccione</option>
                                            <c:forEach items="${tiposDocumento}" var="tipoD">
                                                <option value="${tipoD.tipo}" <c:if test="${tipoDocumento == tipoD.tipo}">selected</c:if>>${tipoD.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="nombres">* Nombres</label>
                                        <input class="form-control" id="nombres" name="nombres" maxlength="50" value="${nombres}" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="apellidos">* Apellidos</label>
                                        <input class="form-control" id="apellidos" name="apellidos" maxlength="50" value="${apellidos}" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="telefono">* Teléfono</label>
                                        <input class="form-control" id="telefono" name="telefono" maxlength="15" value="${telefono}" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="celular">Celular</label>
                                        <input class="form-control" id="celular" name="celular" maxlength="15" value="${celular}" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="correo">Correo</label>
                                        <input class="form-control" id="correo" name="correo" maxlength="50" value="${correo}" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="direccion">* Dirección</label>
                                        <input class="form-control" id="direccion" name="direccion" maxlength="50" value="${direccion}" type="text" />
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
                                        <label>* Usuario</label>
                                        <input class="form-control" id="usuario" name="usuario" type="text" value="${usuario}" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label>* Perfil</label>
                                        <select class="form-control" id="perfil" name="perfil">
                                            <option value="0">Seleccione</option>
                                            <c:forEach items="${perfiles}" var="per">
                                                <option value="${per.id}" <c:if test="${perfil == per.id}">selected</c:if>>${per.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="fechaInicio">* Contraseña</label>
                                        <input class="form-control" id="clave1" name="clave1" type="password" value="${clave1}" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="fechaInicio">* Confirmar contraseña</label>
                                        <input class="form-control" id="clave2" name="clave2" type="password" value="${clave2}" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" align="center">
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-default" value="crearPersona" name="accion">
                                    Guardar
                                </button>
                                <button class="btn btn-default" type="submit" name="accion" id="consultar" value="consultar">Consultar</button>
                                <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                            </div>
                            <div class="col-lg-12"><br></div>
                        </div>
                        <br/>
                        <table class="table table-striped table-hover table-condensed">
                            <thead>
                                <tr>
                                    <th>Documento</th>
                                    <th>Nombres</th>
                                    <th>Apellidos</th>
                                    <th>Teléfono</th>
                                    <th>Celular</th>
                                    <th>Correo</th>
                                    <th>Dirección</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listaPersonas}" var="item">
                                    <tr>
                                        <td>${item.documento}</td>
                                        <td>${item.nombres}</td>
                                        <td>${item.apellidos}</td>
                                        <td>${item.telefono}</td>
                                        <td>${item.celular}</td>
                                        <td>${item.correo}</td>
                                        <td>${item.direccion}</td>
                                        <td>
                                            <a class="btn btn-default" href="<%=request.getContextPath()%>/PersonasController?accion=editar&idPersona=${item.id}">Editar</a>
                                            <button class="btn btn-default" type="button" data-toggle="modal" data-target="#confirmationMessage" onclick="jQuery('#idPersona').val('${item.id}');">Eliminar</button>
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
