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
        <title>Usuarios</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2" id="contenido">
                    <form autocomplete="off" action="./ProyectosController" method="POST" id="formularioProyectos">
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>PROYECTOS</h2>
                        Los campos marcados con (*) son obligatorios
                        <br/>
                        <br/>
                        <input type="hidden" id="idProyecto" name="idProyecto" value="${idProyecto}" />
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="nombre">*Nombre:</label> 
                                <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="fechaInicio">*Fecha de inicio:</label> 
                                <input class="form-control" type="text" id="fechaInicio" name="fechaInicio" value="${fechaInicio}" readonly="true"/>
                                <script type="text/javascript">
                                    $(function() {
                                        $('#fechaInicio').datetimepicker({format: 'dd/mm/yyyy', language:'es', weekStart:true, todayBtn:true, autoclose:true, todayHighlight:true, startView:2, minView:2});
                                    });
                                </script>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="responsable">*Responsable:</label> 
                                <input class="form-control" type="text" id="idPersona" name="idPersona" value="${idPersona}" />
                            </div>
                        </div>
                        <br>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="consultar" onclick="llenarTabla()">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
