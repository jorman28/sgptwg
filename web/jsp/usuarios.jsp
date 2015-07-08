<%-- 
    Document   : usuarios
    Created on : 6/07/2015, 07:39:29 PM
    Author     : Pipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../images/tab.jpg" rel='shortcut icon' type='image/jpeg'>
        <script type="text/javascript" src="../js/jquery.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/general.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/business-styles.css">
        <title>Usuario</title>
    </head>
    <body>
        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
            Launch demo modal
        </button>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                    </div>
                    <div class="modal-body">
                        Contenido de la modal
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="contenido">
                    <form autocomplete="off" action="./Usuario" method="POST">
                        <h1>USUARIOS</h1>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="identificacion">*Documento:</label> 
                                <input class="form-control" type="text" id="identificacion" name="identificacion"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="tipoDocumento">*Tipo de documento:</label>
                                <select class="form-control" id="tipoDocumento" name="tipoDocumento" value="" >
                                    <option value="0">SELECCIONE</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="usuario">*Usuario:</label> 
                                <input class="form-control" type="text" id="usuario" name="usuario" value="" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="clave">*Clave:</label> 
                                <input class="form-control" type="password" id="clave" name="clave" value="" />
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="perfil">*Perfil:</label> 
                                <select class="form-control" id="perfil" name="perfil" value="">
                                    <option value="0" label="Seleccione">
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="consultar" value="consultar">Consultar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="eliminar" value="eliminar">Eliminar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="listar" value="listar">Listar</button>
                            <button class="btn btn-default" type="button" name="accion" id="nuevo" value="nuevo" onclick="nuevoUsuario();">Limpiar</button>
                        </div>
                    </form>
                    <br>
                </div>
            </div>
        </div>
    </body>
</html>
