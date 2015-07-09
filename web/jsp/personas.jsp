<%-- 
    Document   : personas
    Created on : 8/07/2015, 09:33:56 PM
    Author     : Erika Jhoana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="images/tab.jpg" rel='shortcut icon' type='image/jpeg'>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/general.js"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/business-styles.css">
        <title>Personas</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
                    <form autocomplete="off" action="./PersonasController" method="POST">
                        <br>
                        <div>
                            <center>
                                <h4>REGISTRO DE PERSONAS</h4>
                                Los campos marcados con asterisco (*) son obligatorios.
                            </center>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORACIÓN PERSONAL</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="Id_persona">* Documento</label>
                                        <input class="form-control" id="Id_persona" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="tipoDocumento">* Tipo de documento</label>
                                        <select class="form-control" id="tipoDocumento" name="tipoDocumento" value="${tipoDocumento}" >
                                            <option value="0">Seleccione</option>
                                            <c:forEach items="${tiposDocumento}" var="tipo">
                                                <option value="${tipo.tipo}">${tipo.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="nombres">* Nombres</label>
                                        <input class="form-control" id="nombres" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="apellidos">* Apellidos</label>
                                        <input class="form-control" id="apellidos" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="telefono">* Teléfono</label>
                                        <input class="form-control" id="telefono" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="celular">Celular</label>
                                        <input class="form-control" id="celular" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="correo">Correo</label>
                                        <input class="form-control" id="correo" type="text" />
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="direccion">* Dirección</label>
                                        <input class="form-control" id="direccion" type="text" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">INFORMACIÓN EXTENDIDA</div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="tipoPersona">* Tipo de persona</label>
                                        <select class="form-control" id="tipoPersona" name="tipoPersona" value="${tipoPersona}" >
                                            <option value="0">Seleccione</option>
                                            <option value="1">Empleado</option>
                                            <option value="2">Cliente</option>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="tipoCargo">* Cargo</label>
                                        <select class="form-control" id="tipoCargo" name="tipoCargo" value="${tipoCargo}" >
                                            <option value="0">Seleccione</option>
                                            <c:forEach items="${tiposCargo}" var="tipo">
                                                <option value="${tipo.id}">${tipo.nombre}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <label for="fechaInicio">* Fecha de inicio (yyyy-mm-dd)</label>
                                        <input class="form-control" id="fechaInicio" type="text" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
