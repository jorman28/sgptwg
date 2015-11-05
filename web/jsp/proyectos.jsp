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
                <div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./ProyectosController" method="POST" id="formularioProyectos">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
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
                                                <label for="nombre">*Nombre:</label> 
                                                <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="fechaInicio">*Fecha de inicio:</label> 
                                                <input class="form-control" type="text" id="fechaInicio" name="fechaInicio" value="${fechaInicio}" readonly="true"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
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
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <p>Clientes</p>
                                                <ul class="list-group">
                                                    <li class="list-group-item" id="item3">
                                                        <div class="row">
                                                            <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                Martin Moreno
                                                            </div>
                                                            <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <span class="glyphicon glyphicon-remove" onclick="$('#item3').remove();"></span>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <li class="list-group-item" id="item4">
                                                        <div class="row">
                                                            <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                Maria Sanchez
                                                            </div>
                                                            <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <span class="glyphicon glyphicon-remove" onclick="$('#item4').remove();"></span>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <p>Empleados</p>
                                                <ul class="list-group">
                                                    <li class="list-group-item" id="item1">
                                                        <div class="row">
                                                            <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                Marcos Suarez
                                                            </div>
                                                            <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <span class="glyphicon glyphicon-remove" onclick="$('#item1').remove();"></span>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <li class="list-group-item" id="item2">
                                                        <div class="row">
                                                            <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                Carlos Restrepo
                                                            </div>
                                                            <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <span class="glyphicon glyphicon-remove" onclick="$('#item2').remove();"></span>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary" name="accion" id="guardarProyecto" value="guardarProyecto">Guardar</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <h2>PROYECTOS</h2>
                        <div class="panel-group" id="proyectoUno" role="tablist" aria-multiselectable="true">
                            <div class="panel panel-default">
                                <div class="panel-heading" id="headingProyectoUno">
                                    <h4 class="panel-title">
                                        <div class="row">
                                            <a role="button" data-toggle="collapse" data-parent="#proyectoUno" href="#collapseProyectoUno" aria-expanded="true" aria-controls="collapse_seguridad">
                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                    Proyecto uno
                                                </div>
                                            </a>
                                            <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                <span class="glyphicon glyphicon-pencil" data-toggle="modal" data-target="#modalProyectos"></span>
                                                <span class="glyphicon glyphicon-remove" onclick="$('#proyectoUno').remove();"></span>
                                            </div>
                                        </div>
                                    </h4>
                                </div>
                                <div id="collapseProyectoUno" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingProyectoUno">
                                    <ul class="list-group">
                                        <li class="list-group-item" id="version1">
                                            <div class="row">
                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                    Marcos Suarez
                                                </div>
                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                    <span class="glyphicon glyphicon-pencil"></span>
                                                    <span class="glyphicon glyphicon-remove" onclick="$('#version1').remove();"></span>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item" id="version2">
                                            <div class="row">
                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                    Carlos Restrepo
                                                </div>
                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                    <span class="glyphicon glyphicon-pencil"></span>
                                                    <span class="glyphicon glyphicon-remove" onclick="$('#version2').remove();"></span>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultarProyecto" value="consultarProyecto" onclick="llenarProyectos()">Consultar</button>
                            <button class="btn btn-default" type="button" name="accion" id="nuevoProyecto" value="nuevoProyecto" data-toggle="modal" data-target="#modalProyectos">Nuevo</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiarProyecto" value="limpiarProyecto">Limpiar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
