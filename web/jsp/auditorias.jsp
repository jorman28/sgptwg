<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <script type="text/javascript" src="js/auditorias.js"></script>
        <title>Auditorías</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <a id="help" href="./manuales/Ayuda_Auditorias.pdf" target="_blank" title="Ayuda" class="linkAyuda"><i class="glyphicon glyphicon-question-sign"></i></a>
            <div class="row">
                <c:import url="/jsp/general/about.jsp"/>
                <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10" id="contenido">
                    <form autocomplete="off" action="./AuditoriasController" method="POST" id="formularioAuditorias">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <center>
                            <h2>AUDITORÍAS</h2>
                        </center>
                        <input type="hidden" id="id" name="id" value="${id}" />
                        <div class="panel panel-info">
                            <div class="panel-heading">FILTROS</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="id_persona">Persona:</label>
                                        <input class="form-control" id="id_persona" name="id_persona" maxlength="50"/>
                                        <input type="hidden" id="id_personaH" name="id_personaH" />
                                    </div>

                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="fecha_creacion">Fecha:</label>
                                        <input class="form-control" type="text" id="fecha_creacion" name="fecha_creacion" value="${fecha_creacion}" readonly="true"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="clasificacion">Clasificación:</label>
                                        <select id="clasificacion" name="clasificacion" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="USUARIO">USUARIO</option>
                                            <option value="PERMISO">PERMISO</option>
                                            <option value="PERSONA">PERSONA</option>
                                            <option value="CARGO">CARGO</option>
                                            <option value="ESTADO">ESTADO</option>
                                            <option value="PROYECTO">PROYECTO</option>
                                            <option value="VERSION">VERSIÓN</option>
                                            <option value="ACTIVIDAD">ACTIVIDAD</option>
                                            <option value="DOCUMENTO">DOCUMENTO</option>
                                            <option value="COMENTARIO">COMENTARIO</option>
                                        </select>                                        
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="accionAud">Acción:</label>
                                        <select id="accionAud" name="accionAud" class="form-control">
                                            <option value ="0">SELECCIONE</option>
                                            <option value="CREACION">CREACION</option>
                                            <option value="EDICION">EDICION</option>
                                            <option value="ELIMINACION">ELIMINACION</option>
                                        </select>                                        
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label for="descripcion">Descripción:</label>
                                        <textarea class="form-control" id="descripcion" name="descripcion" maxlength="1000">${descripcion}</textarea>
                                    </div>
                                </div>
                            </div>                            
                        </div>
                        <div class="row" align="center">
                            <c:if test="${opcionConsultar}">
                                <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTablaAuditorias()">Consultar</button>
                            </c:if>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="tablaAuditorias"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
