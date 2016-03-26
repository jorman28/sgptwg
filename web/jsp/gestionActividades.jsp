<%-- 
    Document   : gestionActividades
    Created on : 3/12/2015, 09:17:12 PM
    Author     : Jorman Rincón
--%>

<%@page import="com.twg.persistencia.beans.UsuariosBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/gestionActividades.js"></script>
        <style>
            #limpiarParticipante {
                position: absolute;
                right: 20px;
                bottom: 0;
                height: 6px;
                margin: auto;
                font-size: 18px;
                cursor: pointer;
                color: #ccc;
            }
        </style>
        <title>Gestionar Actividades</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ActividadesController" method="POST" id="formularioActividades">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <center>
                            <h2>GESTIÓN DE ACTIVIDADES</h2>
                            Los campos marcados con (*) son obligatorios
                        </center>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div id="modalWarning" class="modal fade">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        WARNING!
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <div id="contenidoWarning"></div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Continuar</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN DE LA ACTIVIDAD</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="proyecto">*Proyecto</label>
                                        <c:choose>
                                            <c:when test="${id == null || id == ''}">
                                                <select class="form-control" id="proyecto" name="proyecto" onchange="consultarVersiones(this.value);">
                                                    <option value="0">SELECCIONE</option>
                                                    <c:forEach items="${proyectos}" var="tipo">
                                                        <option value="${tipo.id}" <c:if test="${proyecto == tipo.id}">selected</c:if> >${tipo.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:when>
                                            <c:otherwise>
                                                <select class="form-control" id="proyecto" name="proyecto" onchange="consultarVersiones(this.value);" disabled="disabled">
                                                    <option value="0">SELECCIONE</option>
                                                    <c:forEach items="${proyectos}" var="tipo">
                                                        <option value="${tipo.id}" <c:if test="${proyecto == tipo.id}">selected</c:if> >${tipo.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" id="proyecto" name="proyecto" value="${proyecto}"/>
                                            </c:otherwise>
                                        </c:choose>                                      
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="version">*Version</label>
                                        <c:choose>
                                            <c:when test="${id == null || id == ''}">
                                                <select id="version" name="version" class="form-control">
                                                    <option value="0">SELECCIONE</option>
                                                    <c:forEach items="${versiones}" var="ver">
                                                        <option value="${ver.id}" <c:if test="${ver.id == version}">selected</c:if>>${ver.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:when>
                                            <c:otherwise>
                                                <select id="version" name="version" class="form-control" disabled="disabled">
                                                    <option value="0">SELECCIONE</option>
                                                    <c:forEach items="${versiones}" var="ver">
                                                        <option value="${ver.id}" <c:if test="${ver.id == version}">selected</c:if>>${ver.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" id="version" name="version" value="${version}"/>
                                            </c:otherwise>
                                        </c:choose>                                      
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="descripcion">*Descripción:</label>
                                        <input class="form-control" type="text" id="descripcion" name="descripcion" value="${descripcion}"/>
                                    </div> 
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="fecha_estimada_inicio">*Fecha estimada inicio:</label>
                                        <input class="form-control" type="text" id="fecha_estimada_inicio" name="fecha_estimada_inicio" value="${fecha_estimada_inicio}" readonly="true"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="fecha_estimada_terminacion">*Fecha estimada fin:</label>
                                        <input class="form-control" type="text" id="fecha_estimada_terminacionn" name="fecha_estimada_terminacionn" value="${fecha_estimada_terminacion}" disabled = "disabled"/>
                                        <input type="hidden" id="fecha_estimada_terminacion" name="fecha_estimada_terminacion" value="${fecha_estimada_terminacion}"/>
                                    </div> 

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="fecha_real_inicio">Fecha real de inicio:</label>
                                        <input class="form-control" type="text" id="fecha_real_inicio" name="fecha_real_inicio" value="${fecha_real_inicio}" readonly="true"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="fecha_real_terminacion">Fecha real de terminacion:</label>
                                        <input class="form-control" type="text" id="fecha_real_terminacion" name="fecha_real_terminacion" value="${fecha_real_terminacion}" readonly="true"/>
                                    </div>
                                    <c:choose>
                                        <c:when test="${fecha_estimada_terminacion == null || fecha_estimada_terminacion == ''}">
                                            <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                <label for="tiempo_estimado">*Tiempo estimado (horas):</label>
                                                <input class="form-control" type="number" min="0" step="0.1" pattern="[0-9]+([,\.][0-9]+)?" id="tiempo_estimado" name="tiempo_estimado" value="${tiempo_estimado}" onchange="calcularFechaFin(this.value);" disabled = "disabled"/>
                                                <input type="hidden" id="tiempo_estimado" name="tiempo_estimado" value="${tiempo_estimado}"/>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                <label for="tiempo_estimado">*Tiempo estimado (horas):</label>
                                                <input class="form-control" type="number" min="0" step="0.1" pattern="[0-9]+([,\.][0-9]+)?" id="tiempo_estimado" name="tiempo_estimado" value="${tiempo_estimado}" onchange="calcularFechaFin(this.value);"/>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="tiempo_invertido">Tiempo invertido (horas):</label>
                                        <input class="form-control" type="number" min="0" step="any" id="tiempo_invertido" name="tiempo_invertido" value="${tiempo_invertido}"/>
                                    </div>
                                </div> 
                                <div class="row">
                                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                        <label for="estado">*Estado</label>
                                        <select id="estado" name="estado" class="form-control">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${estados}" var="esta">
                                                <option value="${esta.id}" <c:if test="${esta.id == estado}">selected</c:if>>${esta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
                                        <label for="participante">Añadir Participante:</label>
                                        <c:choose>
                                            <c:when test="${proyecto == null || proyecto == 0}">
                                                <input class="form-control" type="search" id="participante" name="participante" value="${participante}" disabled="disabled"/>
                                                <span id="limpiarParticipante" class="glyphicon glyphicon-remove-circle"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="form-control" type="search" id="participante" name="participante" value="${participante}"/>
                                                <span id="limpiarParticipante" class="glyphicon glyphicon-remove-circle"></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>  
                            <br />
                            <div class="panel panel-info">
                                <div align="center" class="row form-group">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label>Participantes de la actividad</label> 
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
                                        <p><b>Clientes</b></p>
                                        <ul class="list-group" id="clientesActividad">
                                            <c:choose>
                                                <c:when test="${clientesActividad  == null || clientesActividad.size() == 0}">
                                                    No se han agregado clientes al proyecto
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${clientesActividad}" var="item">
                                                        <li class="list-group-item" id="persona${item.id}">
                                                            <div class="row">
                                                                <input type="hidden" id="idPersona${item.id}" name="idPersonas" value="${item.id}" />
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11"> ${item.nombre}</div>
                                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                    <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(${item.id}, '${item.nombreCargo}');"></span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                    <script type="text/javascript">
                                                        clientesSeleccionados = ${clientesActividad.size()};
                                                    </script>
                                                </c:otherwise>
                                            </c:choose>                                             
                                        </ul>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <p><b>Empleados</b></p>
                                        <ul class="list-group" id="empleadosActividad">
                                            <c:choose>
                                                <c:when test="${empleadosActividad  == null || empleadosActividad.size() == 0}">
                                                    No se han agregado empleados al proyecto
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${empleadosActividad}" var="item">
                                                        <li class="list-group-item" id="persona${item.id}">
                                                            <div class="row">
                                                                <input type="hidden" id="idPersona${item.id}" name="idPersonas" value="${item.id}" />
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11"> ${item.nombre}</div>
                                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                    <span class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="eliminarPersona(${item.id}, '${item.nombreCargo}');"></span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                    <script type="text/javascript">
                                                        empleadosSeleccionados = ${empleadosActividad.size()};
                                                    </script>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                    </div>
                                </div>
                            </div>                          
                        </div>
                        <div class="row">
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar" onclick="return Validar()">Guardar</button>
                            <c:choose>
                                <c:when test="${id == null || id == ''}">
                                    <button class="btn btn-default" type="submit" name="accion" id="limpiarCreacion" value="limpiarCreacion">Limpiar</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-default" type="submit" name="accion" id="limpiarGestion" value="limpiarGestion">Limpiar</button>
                                </c:otherwise>
                            </c:choose>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Volver a Actividades</button>
                        </div>
                        <br />
                        <br />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
