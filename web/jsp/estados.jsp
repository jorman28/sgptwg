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
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/estados.js"></script>
        <title>Estados</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./EstadosController" method="POST" id="formularioEstados">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
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
                                        <label for="tipoEstado">*Tipo de Estado:</label>
                                        <select id="tipoEstado" name="tipoEstado" class="form-control" onchange="ConsultarEstados(this.value, true);">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="ACTIVIDADES" <c:if test="${tipoEstado == 'ACTIVIDADES'}">selected</c:if>>ACTIVIDADES</option>
                                            <option value="VERSIONES" <c:if test="${tipoEstado == 'VERSIONES'}">selected</c:if>>VERSIONES</option>
                                        </select>                                        
                                    </div>

                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="nombre">*Nombre:</label>
                                        <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}" maxlength="30"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="estadoPrev">Estado previo:</label>
                                        <select id="estadoPrev" name="estadoPrev" class="form-control" disabled="disabled">
                                            <option value ="0">SELECCIONE</option>
                                            <c:forEach items="${estadosPrev}" var="tipoPrev">
                                                <option value="${tipoPrev.id}" <c:if test="${estadoPrev == tipoPrev.id}">selected</c:if> >${tipoPrev.nombre}</option>
                                            </c:forEach>
                                        </select>                                        
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="estadoSig">Estado siguiente:</label>
                                        <select id="estadoSig" name="estadoSig" class="form-control" disabled="disabled">
                                            <option value ="0">SELECCIONE</option>
                                            <c:forEach items="${estadosSig}" var="tipoSig">
                                                <option value="${tipoSig.id}" <c:if test="${estadoSig == tipoSig.id}">selected</c:if> >${tipoSig.nombre}</option>
                                            </c:forEach>
                                        </select>                                        
                                    </div>
                                </div>
                                    <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="eFinal">Estado final:</label>
                                        <select id="eFinal" name="eFinal" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="T">Sí</option>
                                            <option value="F">No</option>
                                        </select>                                        
                                    </div>
                                </div>
                                <br>
                            </div>                            
                        </div>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTablaEstados()">Consultar</button>
                            <c:if test="${opcionGuardar == 'T'}">
                                <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            </c:if>
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
