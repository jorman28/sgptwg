<%-- 
    Document   : archivos
    Created on : 25/03/2016, 02:20:04 PM
    Author     : Andres Giraldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/archivos.js"></script>
        <title>Archivos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./ArchivosController" method="POST" id="formularioArchivos" enctype="multipart/form-data">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>ARCHIVOS</h2>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <input type="hidden" id="hiddenCreador" name="hiddenCreador" value="${hiddenCreador}" />
                        <input type="hidden" id="hiddenFecha" name="hiddenFecha" value="${hiddenFecha}" />
                        <div id="modalArchivos" class="modal fade">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        Los campos marcados con (*) son obligatorios
                                    </div>
                                    <div class="modal-body">
                                        <div class="row form-group">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="fechaCreacion">*Fecha creación:</label> 
                                                <input class="form-control" type="text" id="fechaCreacion" name="fechaCreacion" value="${fechaCreacion}" readonly="true"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                <label for="creador">*Creador:</label> 
                                                <input class="form-control" type="text" id="creador" name="creador" value="${creador}" readonly="true"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label for="nombre">*Nombre:</label> 
                                                <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}" maxlength="50"/>
                                            </div>
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" maxlength="1000">
                                                <label for="descripcion">*Descripción:</label> 
                                                <textarea class="form-control" id="descripcion" name="descripcion">
                                                    ${descripcion}
                                                </textarea>
                                            </div>
                                            <div id="divArchivo" class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
                                                <input type="file" id="archivo" name="archivo" onchange="cargarArchivo(this);"/>
                                                <input type="hidden" id="nombreArchivo" name="nombreArchivo"/>
                                            </div>
                                            <div id="divDescarga" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group" align="center">
                                            </div>
                                            <div id="divComentarios" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <c:import url="/jsp/general/comentarios.jsp"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary" name="accion" id="guardar" value="guardar">Guardar</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">FILTROS</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                        <label for="filtroContiene">Contiene</label>
                                        <input class="form-control" type="text" id="filtroContiene" name="filtroContiene"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                        <label for="filtroFecha">Fecha creación</label>
                                        <input class="form-control" type="text" id="filtroFecha" name="filtroFecha"/>
                                    </div>

                                    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                        <label for="filtroCreador">Creador</label>
                                        <input class="form-control" type="text" id="filtroCreador" name="filtroCreador"/>
                                        <input type="hidden" id="idPersona" name="idPersona"/>
                                    </div>
                                </div>
                                <div class="row" align="center">
                                    <button class="btn btn-default" type="button" name="accion" id="crear" value="crear" onclick="nuevoArchivo();">Nuevo</button>
                                    <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla();">Consultar</button>
                                    <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="limpiar();">Limpiar</button>
                                </div>
                            </div>                            
                        </div>
                        <div id="tablaArchivos"></div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
