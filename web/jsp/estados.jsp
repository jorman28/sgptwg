<%-- 
    Document   : estados
    Created on : 08-jul-2015, 23:33:00
    Author     : Jorman
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
        <title>Estados</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
                    <form autocomplete="off" action="./EstadosController" method="POST">
                        <h1>ESTADOS</h1>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="tipoEstado">*Estado:</label>
                                <select class="form-control" id="tipoEstado" name="tipoEstado" value="" >
                                    <option value="0">SELECCIONE</option>
                                    <option value="Estado de Actividad">Estado de Actividad</option>
                                    <option value="Estado de Versión">Estado de Versión</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="nombre">*Nombre:</label> 
                                <input class="form-control" type="text" id="nombre" name="nombre" value="" />
                            </div>
                        </div>
                        <br>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="consultar" value="consultar">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        </div>
                        <br/>
                        <table class="table table-striped table-hover table-condensed">
                            <thead>
                                <tr>
                                    <th>Tipo de Estado</th>
                                    <th>Id</th>
                                    <th>Nombre</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Estado de Versión</td>
                                    <td>01</td>
                                    <td>Activo</td>
                                    <td>
                                        <button class="btn btn-default" type="submit" name="accion" id="editar" value="editar">Editar</button>
                                        <button class="btn btn-default" type="button" data-toggle="modal" data-target="#confirmationMessage">Eliminar</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>

        <div id="confirmationMessage" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- dialog body -->
                    <div class="modal-body">
                        Hello world!
                    </div>
                    <!-- dialog buttons -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" name="accion" id="eliminar" value="eliminar">OK</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $("#confirmationMessage").on("show", function() {    // wire up the OK button to dismiss the modal when shown
                $("#eliminar").on("click", function(e) {
                    console.log("button pressed");   // just as an example...
                    $("#confirmationMessage").modal('hide');     // dismiss the dialog
                });
            });

            $("#confirmationMessage").on("hide", function() {    // remove the event listeners when the dialog is dismissed
                $("#confirmationMessage button.btn").off("click");
            });

            $("#confirmationMessage").on("hidden", function() {  // remove the actual elements from the DOM when fully hidden
                $("#confirmationMessage").remove();
            });

            $("#confirmationMessage").modal({// wire up the actual modal functionality and show the dialog
                "backdrop": "static",
                "keyboard": false,
                "show": false                     // ensure the modal is shown immediately
            });
        </script>
    </body>
</html>
