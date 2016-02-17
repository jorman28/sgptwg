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
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
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
                                        <div class="panel-group">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <div class="row">
                                                            <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                Inicio
                                                            </div>
                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <input type="checkbox" name="permisos" id="permiso_9" value="9" />
                                                            </div>
                                                        </div>
                                                    </h4>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_seguridad" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_seguridad">
                                                    <h4 class="panel-title">
                                                        <div class="row">
                                                            <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                <a role="button" data-toggle="collapse" data-parent="#pagina_seguridad" href="#collapse_seguridad" aria-expanded="true" aria-controls="collapse_seguridad">
                                                                    Seguridad
                                                                </a>
                                                            </div>
                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <input type="checkbox" name="permisos" id="permiso_14" value="14" />
                                                            </div>
                                                        </div>
                                                    </h4>
                                                </div>
                                                <div id="collapse_seguridad" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_seguridad">
                                                    <div class="panel-body">
                                                        <div class="panel-group" id="pagina_usuarios" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_usuarios">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_usuarios" href="#collapse_usuarios" aria-expanded="true" aria-controls="collapse_usuarios">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Usuarios
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_15" value="15" onclick="encenderPermisosHijos(this.id);" />
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_usuarios" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_usuarios">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_15_1" value="17" onclick="encenderPermisoPadre('permiso_15');" /> Consultar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_15_2" value="18" onclick="encenderPermisoPadre('permiso_15');" /> Eliminar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_15_3" value="19" onclick="encenderPermisoPadre('permiso_15');" /> Guardar
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_permisos" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_permisos">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_permisos" href="#collapse_permisos" aria-expanded="true" aria-controls="collapse_permisos">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Permisos
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_10" value="10" onclick="encenderPermisosHijos(this.id);" /> 
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_permisos" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_permisos">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_10_1" value="20" onclick="encenderPermisoPadre('permiso_10');" /> Consultar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_10_2" value="21" onclick="encenderPermisoPadre('permiso_10');" /> Eliminar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_10_3" value="22" onclick="encenderPermisoPadre('permiso_10');" /> Permisos
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_10_4" value="23" onclick="encenderPermisoPadre('permiso_10');" /> Guardar
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
                                                    <h4 class="panel-title">
                                                        <div class="row">
                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_configuracion" href="#collapse_configuracion" aria-expanded="true" aria-controls="collapse_configuracion">
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                    Configuración
                                                                </div>
                                                            </a>
                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <input type="checkbox" name="permisos" id="permiso_5" value="5" onclick="encenderPermisosHijos(this.id);" /> 
                                                            </div>
                                                        </div>
                                                    </h4>
                                                </div>
                                                <div id="collapse_configuracion" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_configuracion">
                                                    <div class="panel-body">
                                                        <div class="panel-group" id="pagina_personas" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_personas">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_personas" href="#collapse_personas" aria-expanded="true" aria-controls="collapse_personas">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Personas
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_11" value="11" onclick="encenderPermisosHijos(this.id);" /> 
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_personas" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_personas">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_11_1" value="24" onclick="encenderPermisoPadre('permiso_11');" /> Consultar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_11_2" value="25" onclick="encenderPermisoPadre('permiso_11');" /> Crear
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_11_3" value="26" onclick="encenderPermisoPadre('permiso_11');" /> Eliminar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_11_4" value="27" onclick="encenderPermisoPadre('permiso_11');" /> Guardar
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_cargos" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_cargos">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_cargos" href="#collapse_cargos" aria-expanded="true" aria-controls="collapse_cargos">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Cargos
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_3" value="3" onclick="encenderPermisosHijos(this.id);" /> 
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_cargos" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_cargos">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_3_1" value="28" onclick="encenderPermisoPadre('permiso_3');" /> Eliminar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_3_2" value="29" onclick="encenderPermisoPadre('permiso_3');" /> Guardar
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_estados" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_estados">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_estados" href="#collapse_estados" aria-expanded="true" aria-controls="collapse_estados">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Estados
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_8" value="8" onclick="encenderPermisosHijos(this.id);" /> 
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_estados" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_estados">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_8_1" value="30" onclick="encenderPermisoPadre('permiso_8');" /> Eliminar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_8_2" value="31" onclick="encenderPermisoPadre('permiso_8');" /> Guardar
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
                                                    <h4 class="panel-title">
                                                        <div class="row">
                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_proyectos" href="#collapse_proyectos" aria-expanded="true" aria-controls="collapse_proyectos">
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                    Proyectos
                                                                </div>
                                                            </a>
                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <input type="checkbox" name="permisos" id="permiso_12" value="12" /> 
                                                            </div>
                                                        </div>
                                                    </h4>
                                                </div>
                                                <div id="collapse_proyectos" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_proyectos">
                                                    <div class="panel-body">
                                                        <div class="panel-group" id="pagina_versiones" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_versiones">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_versiones" href="#collapse_versiones" aria-expanded="true" aria-controls="collapse_versiones">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Versiones
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_16" value="16" onclick="encenderPermisosHijos(this.id);" /> 
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_versiones" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_versiones">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16_1" value="32" onclick="encenderPermisoPadre('permiso_16');" /> Consultar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16_2" value="33" onclick="encenderPermisoPadre('permiso_16');" /> Crear proyecto
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16_3" value="34" onclick="encenderPermisoPadre('permiso_16');" /> Eliminar proyecto
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16_4" value="35" onclick="encenderPermisoPadre('permiso_16');" /> Crear versión
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16_5" value="36" onclick="encenderPermisoPadre('permiso_16');" /> Eliminar versión
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_16_6" value="37" onclick="encenderPermisoPadre('permiso_16');" /> Comentar
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-group" id="pagina_actividades" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="heading_actividades">
                                                                    <h4 class="panel-title">
                                                                        <div class="row">
                                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_actividades" href="#collapse_actividades" aria-expanded="true" aria-controls="collapse_actividades">
                                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                                    Actividades
                                                                                </div>
                                                                            </a>
                                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                                <input type="checkbox" name="permisos" id="permiso_1" value="1" onclick="encenderPermisosHijos(this.id);" /> 
                                                                            </div>
                                                                        </div>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapse_actividades" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_actividades">
                                                                    <div class="panel-body">
                                                                        <div class="checkbox">
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_1_1" value="38" onclick="encenderPermisoPadre('permiso_1');" /> Consultar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_1_2" value="39" onclick="encenderPermisoPadre('permiso_1');" /> Crear
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_1_3" value="40" onclick="encenderPermisoPadre('permiso_1');" /> Eliminar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_1_4" value="41" onclick="encenderPermisoPadre('permiso_1');" /> Guardar
                                                                            </label>
                                                                            &nbsp;
                                                                            <label>
                                                                                <input type="checkbox" name="permisos" id="permiso_1_5" value="42" onclick="encenderPermisoPadre('permiso_1');" /> Comentar
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
                                                    <h4 class="panel-title">
                                                        <div class="row">
                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_documentacion" href="#collapse_documentacion" aria-expanded="true" aria-controls="collapse_documentacion">
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                    Documentación
                                                                </div>
                                                            </a>
                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <input type="checkbox" name="permisos" id="permiso_7" value="7" /> 
                                                            </div>
                                                        </div>
                                                    </h4>
                                                </div>
                                                <div id="collapse_documentacion" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_documentacion">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-group" id="pagina_reportes" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="heading_reportes">
                                                    <h4 class="panel-title">
                                                        <div class="row">
                                                            <a role="button" data-toggle="collapse" data-parent="#pagina_reportes" href="#collapse_reportes" aria-expanded="true" aria-controls="collapse_reportes">
                                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                                    Reportes
                                                                </div>
                                                            </a>
                                                            <div align="right" class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                                <input type="checkbox" name="permisos" id="permiso_13" value="13" /> 
                                                            </div>
                                                        </div>
                                                    </h4>
                                                </div>
                                                <div id="collapse_reportes" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading_reportes">
                                                    <div class="panel-body">
                                                        <div class="checkbox">
                                                            <label>
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
