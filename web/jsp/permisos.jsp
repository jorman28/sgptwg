<%-- 
    Document   : permisos
    Created on : 2/10/2015, 09:58:04 PM
    Author     : Pipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/permisos.js"></script>
        <title>Permisos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./PermisosController" method="POST" id="formularioPermisos">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <div id="modalPermisos" class="modal fade">
                            <input type="hidden" name="perfilPermiso" id="perfilPermiso"/>
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Seleccione los permisos por página que desea configurar</h4>
                                    </div>

                                    <!-- dialog body -->
                                    <div class="modal-body">
                                        <div class="panel-group" id="pagina_inicio" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_inicio">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_inicio" href="#collapse_inicio" aria-expanded="true" aria-controls="collapse_inicio">
                                                        <h4 class="panel-title">
                                                            Inicio
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_inicio" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_inicio">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_9" value="9" /> TODOS
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_seguridad" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_seguridad">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_seguridad" href="#collapse_seguridad" aria-expanded="true" aria-controls="collapse_seguridad">
                                                        <h4 class="panel-title">
                                                            Seguridad
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_seguridad" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_seguridad">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_14" value="14" /> TODOS
                                                            </label>
                                                        </div>
                                                        <div class="panel-group" id="pagina_usuarios" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_usuarios">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_usuarios" href="#collapse_usuarios" aria-expanded="true" aria-controls="collapse_usuarios">
                                                                        <h4 class="panel-title">
                                                                            Usuarios
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_usuarios" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_usuarios">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_15" value="15" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_permisos" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_permisos">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_permisos" href="#collapse_permisos" aria-expanded="true" aria-controls="collapse_permisos">
                                                                        <h4 class="panel-title">
                                                                            Permisos
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_permisos" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_permisos">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_10" value="10" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_configuracion" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_configuracion">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_configuracion" href="#collapse_configuracion" aria-expanded="true" aria-controls="collapse_configuracion">
                                                        <h4 class="panel-title">
                                                            Configuración
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_configuracion" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_configuracion">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_5" value="5" /> TODOS
                                                            </label>
                                                        </div>
                                                        <div class="panel-group" id="pagina_personas" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_personas">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_personas" href="#collapse_personas" aria-expanded="true" aria-controls="collapse_personas">
                                                                        <h4 class="panel-title">
                                                                            Personas
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_personas" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_personas">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_11" value="11" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_cargos" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_cargos">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_cargos" href="#collapse_cargos" aria-expanded="true" aria-controls="collapse_cargos">
                                                                        <h4 class="panel-title">
                                                                            Cargos
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_cargos" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_cargos">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_3" value="3" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_estados" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_estados">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_estados" href="#collapse_estados" aria-expanded="true" aria-controls="collapse_estados">
                                                                        <h4 class="panel-title">
                                                                            Estados
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_estados" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_estados">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_8" value="8" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_proyectos" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_proyectos">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_proyectos" href="#collapse_proyectos" aria-expanded="true" aria-controls="collapse_proyectos">
                                                        <h4 class="panel-title">
                                                            Proyectos
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_proyectos" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_proyectos">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_12" value="12" /> TODOS
                                                            </label>
                                                        </div>
                                                        <div class="panel-group" id="pagina_versiones" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_versiones">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_versiones" href="#collapse_versiones" aria-expanded="true" aria-controls="collapse_versiones">
                                                                        <h4 class="panel-title">
                                                                            Versiones
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_versiones" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_versiones">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16" value="16" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_actividades" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_actividades">
                                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_actividades" href="#collapse_actividades" aria-expanded="true" aria-controls="collapse_actividades">
                                                                        <h4 class="panel-title">
                                                                            Actividades
                                                                        </h4>
                                                                    </a>
                                                                </div>
                                                                <div id="collapse_actividades" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_actividades">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_1" value="1" /> TODOS
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_documentacion" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_documentacion">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_documentacion" href="#collapse_documentacion" aria-expanded="true" aria-controls="collapse_documentacion">
                                                        <h4 class="panel-title">
                                                            Documentación
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_documentacion" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_documentacion">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_7" value="7" /> TODOS
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_reportes" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_reportes">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_reportes" href="#collapse_reportes" aria-expanded="true" aria-controls="collapse_reportes">
                                                        <h4 class="panel-title">
                                                            Reportes
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_reportes" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_reportes">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_13" value="13" /> TODOS
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_ayuda" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_ayuda">
                                                    <a role="button" data-toggle="collapse" data-parent="#pagina_ayuda" href="#collapse_ayuda" aria-expanded="true" aria-controls="collapse_ayuda">
                                                        <h4 class="panel-title">
                                                            Ayuda
                                                        </h4>
                                                    </a>
                                                </div>
                                                <div id="collapse_ayuda" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_ayuda">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" name="permisos" id="permiso_2" value="2" /> TODOS
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- dialog buttons -->
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary" name="accion" id="guardarPermisos" value="guardarPermisos">Guardar</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <h2>PERMISOS</h2>
                        <br/>
                        <input type="hidden" id="idPerfil" name="idPerfil" value="${idPerfil}" />
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="nombrePerfil">Perfil:</label> 
                                <input class="form-control" type="text" id="nombrePerfil" name="nombrePerfil" value="${nombrePerfil}"/>
                            </div>
                        </div>
                        <br>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla();">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="nuevoPerfil();">Limpiar</button>
                        </div>
                        <br/>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaPerfiles"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
