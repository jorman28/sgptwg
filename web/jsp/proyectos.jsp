<%-- 
    Document   : usuarios
    Created on : 6/07/2015, 07:39:29 PM
    Author     : Pipe
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/proyectos.js"></script>
        <title>Proyectos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ProyectosController" method="POST" id="formularioProyectos">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <input type="hidden" id="tipoEliminacion" name="tipoEliminacion"/>
                        <div id="modalConsulta" class="modal fade">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        CONSULTA
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <input type="text" class="form-control" id="busquedaProyecto" name="busquedaProyecto"/>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-default" type="submit" name="accion" id="consultarProyecto" value="consultarProyecto">Consultar</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="modalProyectos" class="modal fade">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        Los campos marcados con (*) son obligatorios
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" id="idProyecto" name="idProyecto" value="${idProyecto}" />
                                        <div class="row form-group">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="nombreProyecto">*Nombre:</label> 
                                                <input class="form-control" type="text" id="nombreProyecto" name="nombreProyecto" value="${nombreProyecto}"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="fechaInicioProyecto">*Fecha de inicio:</label> 
                                                <input class="form-control" type="text" id="fechaInicioProyecto" name="fechaInicioProyecto" value="${fechaInicioProyecto}" readonly="true"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label for="participante">*Participante:</label> 
                                                <input class="form-control" type="text" id="participante" name="participante" />
                                            </div>
                                        </div>
                                        <div align="center" class="row form-group">
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label >Participantes</label> 
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
                                                <p>Clientes</p>
                                                <ul class="list-group" id="clientesProyecto"></ul>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <p>Empleados</p>
                                                <ul class="list-group" id="empleadosProyecto"></ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <c:if test="${opcionGuardarProyecto == 'T'}">
                                            <button type="submit" class="btn btn-primary" name="accion" id="guardarProyecto" value="guardarProyecto">Guardar</button>
                                        </c:if>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="modalVersiones" class="modal fade">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        Los campos marcados con (*) son obligatorios
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" id="idProyectoVersion" name="idProyectoVersion" value="${idProyectoVersion}" />
                                        <input type="hidden" id="idVersion" name="idVersion" value="${idVersion}" />
                                        <div class="row form-group">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="nombreVersion">*Nombre:</label> 
                                                <input class="form-control" type="text" id="nombreVersion" name="nombreVersion" value="${nombreVersion}"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="estado">*Estado:</label> 
                                                <select class="form-control" id="estado" name="estado">
                                                    <option value="0">SELECCIONE</option>
                                                    <c:forEach items="${estados}" var="esta">
                                                        <option value="${esta.id}" <c:if test="${esta.id == estado}">selected</c:if>>${esta.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="fechaInicioVersion">*Fecha de inicio:</label> 
                                                <input class="form-control" type="text" id="fechaInicioVersion" name="fechaInicioVersion" value="${fechaInicioVersion}" readonly="true"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="fechaFinVersion">*Fecha de fin:</label> 
                                                <input class="form-control" type="text" id="fechaFinVersion" name="fechaFinVersion" value="${fechaFinVersion}" readonly="true"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label for="alcance">*Alcance:</label> 
                                                <textarea class="form-control" type="text" id="alcance" name="alcance" >${alcance}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <c:if test="${opcionGuardarVersion == 'T'}">
                                            <button type="submit" class="btn btn-primary" name="accion" id="guardarVersion" value="guardarVersion">Guardar</button>
                                        </c:if>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <h2>PROYECTOS</h2>
                        <div id="listaProyectos">${listaProyectos}</div>
                        <div class="row form-group" align="center">
                            <c:if test="${opcionConsultar == 'T'}">
                                <button class="btn btn-default" type="button" data-toggle="modal" data-target="#modalConsulta">Consultar</button>
                            </c:if>
                            <c:if test="${opcionCrearProyecto == 'T'}">
                                <button class="btn btn-default" type="button" name="accion" id="crearProyecto" value="crearProyecto" onclick="nuevoProyecto();">Nuevo</button>
                            </c:if>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiarProyecto" value="limpiarProyecto">Limpiar</button>
                        </div>
                        <c:import url="/jsp/general/comentarios.jsp"/>
                        <input type="hidden" id="idComentario" name="idComentario"/>
                        <div id="listaComentarios">${listaComentarios}</div>
                        <button class="btn btn-default" type="button" data-toggle="modal" data-target="#modalComentarios">Comentar</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
