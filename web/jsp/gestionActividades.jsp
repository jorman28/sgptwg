<%-- 
    Document   : gestionActividades
    Created on : 3/12/2015, 09:17:12 PM
    Author     : Jorman RIncón
--%>

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
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN DE LA ACTIVIDAD</div>
                            <div class="panel-body">
                                <div class="row">
                                    
                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="empleado:">*Empleado:</label>
                                        <select id="empleado" name="empleado" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="1">Persona1</option>
                                            <option value="2">Persona2</option>
                                        </select>      
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3"></div>
                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3"></div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="version">*Version</label>
                                        <select id="version" name="version" class="form-control">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${versiones}" var="ver">
                                                <option value="${ver.id}" <c:if test="${ver.id == version}">selected</c:if>>${ver.nombre}</option>
                                            </c:forEach>
                                        </select>                                      
                                    </div>

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="descripcion">*Descripción:</label>
                                        <input class="form-control" type="text" id="descripcion" name="descripcion" value="${descripcion}"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="fecha_estimada_inicio">*Fecha estimada inicio:</label>
                                        <input class="form-control" type="text" id="fecha_estimada_inicio" name="fecha_estimada_inicio" value="${fecha_estimada_inicio}" readonly="true"/>
                                    </div> 
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="fecha_estimada_terminacion">*Fecha estimada fin:</label>
                                        <input class="form-control" type="text" id="fecha_estimada_terminacion" name="fecha_estimada_terminacion" value="${fecha_estimada_terminacion}" readonly="true"/>
                                    </div> 

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="fecha_real_inicio">Fecha real de inicio:</label>
                                        <input class="form-control" type="text" id="fecha_real_inicio" name="fecha_real_inicio" value="${fecha_real_inicio}" readonly="true"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="fecha_real_terminacion">Fecha real de terminacion:</label>
                                        <input class="form-control" type="text" id="fecha_real_terminacion" name="fecha_real_terminacion" value="${fecha_real_terminacion}" readonly="true"/>
                                    </div>
                                </div>
                                <div class="row">

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="tiempo_estimado">*Tiempo estimado (horas):</label>
                                        <input class="form-control" type="number" min="0" step="0.1" id="tiempo_estimado" name="tiempo_estimado" value="${tiempo_estimado}"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="tiempo_invertido">Tiempo invertido (horas):</label>
                                        <input class="form-control" type="number" min="0" step="0.1" id="tiempo_invertido" name="tiempo_invertido" value="${tiempo_invertido}"/>
                                    </div> 

                                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                        <label for="estado">*Estado</label>
                                        <select id="estado" name="estado" class="form-control">
                                            <option value="0">SELECCIONE</option>
                                            <c:forEach items="${estados}" var="esta">
                                                <option value="${esta.id}" <c:if test="${esta.id == estado}">selected</c:if>>${esta.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <br>
                            </div>                            
                        </div>
                        <div class="row">
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiarGestion" value="limpiarGestion">Limpiar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Volver a Actividades</button>
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
