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
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header" style="color:#fff;
                                         padding:9px 15px;
                                         border-bottom:1px solid #eee;
                                         background-color: #FFCC00;
                                         -webkit-border-top-left-radius: 5px;
                                         -webkit-border-top-right-radius: 5px;
                                         -moz-border-radius-topleft: 5px;
                                         -moz-border-radius-topright: 5px;
                                         border-top-left-radius: 5px;
                                         border-top-right-radius: 5px;">
                                        <span class="glyphicon glyphicon-warning-sign"></span> <b>WARNING!</b>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <div id="contenidoWarning"></div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <!--<button class="btn btn-primary" type="submit" name="accion" id="guardar" value="guardar">Continuar</button>-->
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="modalFechas">
                            <c:choose>
                                <c:when test="${listaActividadesEmpleados  != null || listaActividadesEmpleados.size() != 0}">
                                    <c:forEach items="${listaActividadesEmpleados}" var="actividadEmpleado">
                                        <div id="modal_${actividadEmpleado.empleado}" class="modal fade bs-example-modal-lg">
                                            <div class="modal-dialog modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <span class="glyphicon glyphicon-calendar"></span> <b> Programar actividad para ${actividadEmpleado.strNombrePersona}</b>
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                    <label for="fecha_estimada_inicio_${actividadEmpleado.empleado}">*Fecha estimada inicio:</label>
                                                                    <input class="form-control" type="text" id="fecha_estimada_inicio_${actividadEmpleado.empleado}" name="fecha_estimada_inicio_${actividadEmpleado.empleado}" value="${actividadEmpleado.strFecha_estimada_inicio}" onchange="fecha_estimada_inicio_Change(${actividadEmpleado.empleado})" readonly="true"/>
                                                                    <script type="text/javascript">
                                                                        $(function () {
                                                                            /*seleccionar la fecha estimada de inicio de una actividad para una persona*/
                                                                            $("#fecha_estimada_inicio_${actividadEmpleado.empleado}")
                                                                                    .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
                                                                                    .on("changeDate", function () {
                                                                                        $("#fecha_estimada_terminacion_${actividadEmpleado.empleado}").datetimepicker("setStartDate", $("#fecha_estimada_inicio_${actividadEmpleado.empleado}").val());
                                                                                    });
                                                                        });
                                                                    </script>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                    <label for="fecha_estimada_terminacion_${actividadEmpleado.empleado}">*Fecha estimada fin:</label>
                                                                    <input class="form-control" type="text" id="fecha_estimada_terminacionn_${actividadEmpleado.empleado}" name="fecha_estimada_terminacionn_${actividadEmpleado.empleado}" value="${actividadEmpleado.strFecha_estimada_terminacion}" disabled = "disabled"/><!--se pone el id del type text fecha_estimada_terminacionn porque a pesar de estar disabled al tener el mismo nombre del hidden el dato no llegaba a controlador-->
                                                                    <input type="hidden" id="fecha_estimada_terminacion_${actividadEmpleado.empleado}" name="fecha_estimada_terminacion_${actividadEmpleado.empleado}" value="${actividadEmpleado.strFecha_estimada_terminacion}" />
                                                                    <script type="text/javascript">
                                                                        $(function () {
                                                                            /*Función que permite seleccionar la fecha estimada de terminacion de una actividad para una persona*/
                                                                            $("#fecha_estimada_terminacion_${actividadEmpleado.empleado}")
                                                                                    .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
                                                                                    .on("changeDate", function () {
                                                                                        $("#fecha_estimada_inicio_${actividadEmpleado.empleado}").datetimepicker("setEndDate", $("#fecha_estimada_terminacion_${actividadEmpleado.empleado}").val());
                                                                                    });
                                                                        });
                                                                    </script>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                    <label for="fecha_real_inicio_${actividadEmpleado.empleado}">Fecha real de inicio:</label>
                                                                    <input class="form-control" type="text" id="fecha_real_inicio_${actividadEmpleado.empleado}" name="fecha_real_inicio_${actividadEmpleado.empleado}" value="${actividadEmpleado.strFecha_real_inicio}" readonly="true"/>
                                                                    <script type="text/javascript">
                                                                        /*Función que permite seleccionar la fecha real de inicio de una actividad para una persona*/
                                                                        $(function () {
                                                                            $("#fecha_real_inicio_${actividadEmpleado.empleado}")
                                                                                    .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
                                                                                    .on("changeDate", function () {
                                                                                        $("#fecha_real_terminacion_${actividadEmpleado.empleado}").datetimepicker("setStartDate", $("#fecha_real_inicio_${actividadEmpleado.empleado}").val());
                                                                                    });
                                                                        });
                                                                    </script>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                    <label for="fecha_real_terminacion_${actividadEmpleado.empleado}">Fecha real de terminacion:</label>
                                                                    <input class="form-control" type="text" id="fecha_real_terminacion_${actividadEmpleado.empleado}" name="fecha_real_terminacion_${actividadEmpleado.empleado}" value="${actividadEmpleado.strFecha_real_terminacion}" readonly="true"/>
                                                                    <script type="text/javascript">
                                                                        $(function () {
                                                                            /*Función que permite seleccionar la fecha real de terminacion de una actividad para una persona*/
                                                                            $("#fecha_real_terminacion_${actividadEmpleado.empleado}")
                                                                                    .datetimepicker({format: "dd/mm/yyyy", language: "es", weekStart: true, todayBtn: true, autoclose: true, todayHighlight: true, startView: 2, minView: 2})
                                                                                    .on("changeDate", function () {
                                                                                        $("#fecha_real_inicio_${actividadEmpleado.empleado}").datetimepicker("setEndDate", $("#fecha_real_terminacion_${actividadEmpleado.empleado}").val());
                                                                                    });
                                                                        });
                                                                    </script>
                                                                </div>
                                                                <c:choose>
                                                                    <c:when test="${actividadEmpleado.strTiempo_estimado == null || actividadEmpleado.strTiempo_estimado == ''}">
                                                                        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                            <label for="tiempo_estimado_${actividadEmpleado.empleado}">*Tiempo estimado (horas):</label>
                                                                            <input class="form-control" type="number" min="0" step="0.1" pattern="[0-9]+([,\.][0-9]+)?" id="tiempo_estimado_${actividadEmpleado.empleado}" name="tiempo_estimado_${actividadEmpleado.empleado}" onchange="calcularFechaFin(this.value, ${actividadEmpleado.empleado});" value="${actividadEmpleado.strTiempo_estimado}" disabled = "disabled"/>
                                                                            <input type="hidden" id="tiempo_estimado_${actividadEmpleado.empleado}" name="tiempo_estimado_${actividadEmpleado.empleado}" value="${actividadEmpleado.strTiempo_estimado}"/>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                            <label for="tiempo_estimado_${actividadEmpleado.empleado}">*Tiempo estimado (horas):</label>
                                                                            <input class="form-control" type="number" min="0" step="0.1" pattern="[0-9]+([,\.][0-9]+)?" id="tiempo_estimado_${actividadEmpleado.empleado}" name="tiempo_estimado_${actividadEmpleado.empleado}" value="${actividadEmpleado.strTiempo_estimado}" onchange="calcularFechaFin(this.value, ${actividadEmpleado.empleado});"/>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                                                                    <label for="tiempo_invertido_${actividadEmpleado.empleado}">Tiempo invertido (horas):</label>
                                                                    <input class="form-control" type="number" min="0" step="any" id="tiempo_invertido_${actividadEmpleado.empleado}" name="tiempo_invertido_${actividadEmpleado.empleado}" value="${actividadEmpleado.strTiempo_invertido}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="glyphicon glyphicon-ok"></i> Aceptar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                            </c:choose>

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
                                                <select id="version" name="version" class="form-control" onchange="actualizarFechas(this.value);">
                                                    <option value="0">SELECCIONE</option>
                                                    <c:forEach items="${versiones}" var="ver">
                                                        <option value="${ver.id}" <c:if test="${ver.id == version}">selected</c:if>>${ver.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:when>
                                            <c:otherwise>
                                                <select id="version" name="version" class="form-control" disabled="disabled" onchange="actualizarFechas(this.value);">
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
                                        <label for="estado">*Estado</label>
                                        <select id="estado" name="estado" class="form-control">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${estados}" var="esta">
                                                <option value="${esta.id}" <c:if test="${esta.id == estado}">selected</c:if>>${esta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div> 
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label for="descripcion">*Descripción:</label>
                                        <textarea class="form-control" id="descripcion" name="descripcion" maxlength="1000">${descripcion}</textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
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
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11"> ${item.nombre} - <a href="#" data-toggle="modal" onclick="mostrarModal(${item.id});"> Añadir fechas y tiempos </a></div>
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
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11"> ${item.nombre} - <a href="#" data-toggle="modal" onclick="mostrarModal(${item.id});"> Añadir fechas y tiempos </a></div>
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
                        <div class="row form-group" align="center">
                        <!--<button class="btn btn-default" type="button" name="accion" id="crear" value="guardar" <%/*onclick="ValidarActividades()"*/%>>Guardar</button> Boton que funciona con el modal de warning-->
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar" <%/*onclick="ValidarActividades()"*/%>>Guardar</button>
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
                        <c:if test="${not empty listaComentarios}">
                            <div class="row">
                                <div id="divComentarios" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <c:import url="/jsp/general/comentarios.jsp"/>
                                </div>
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
