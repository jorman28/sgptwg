<%-- 
    Document   : cargos
    Created on : 2/10/2015, 12:32:47 PM
    Author     : erikasta07
--%>

<%@page import="com.twg.persistencia.daos.CargosDao"%>
<%@page import="com.twg.persistencia.beans.CargosBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/header.jsp"/>
        <title>Cargos</title>
    </head>
    <body>
        <div class="container-fluid">
            <div>${menu}</div>
            <c:import url="/jsp/general/alertas.jsp"/>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2" id="contenido">
                    <form id="formularioCargos" autocomplete="off" action="./CargosController" method="POST"> 
                        <c:import url="/jsp/general/eliminacion.jsp"/>
                        <h2>CARGOS</h2>
                        Los campos marcados con (*) son obligatorios<br/><br/>
                        <input type="hidden" id="idCargo" name="idCargo" value="${idCargo}" />
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                <label for="descripcion">* Descripción del cargo</label>
                                <input class="form-control" id="descripcion" name="descripcion" maxlength="50" value="${descripcion}" type="text" />
                            </div>
                        </div>
                        <br>
                        <button class="btn btn-default" type="submit" name="accion" id="consultar" value="consultar">Consultar</button>
                        <button class="btn btn-default" type="submit" name="accion" id="crear" value="crear">Guardar</button>
                        <button class="btn btn-default" type="submit" name="accion" id="limpiar" value="limpiar">Limpiar</button>
                        <br/><br/>
                        <table class="table table-striped table-hover table-condensed">
                            <thead>
                                <tr>
                                    <th>Descripción del cargo</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listaCargos}" var="item">
                                    <tr>
                                        <td>${item.nombre}</td>
                                        <td>
                                            <a class="btn btn-default" href="<%=request.getContextPath()%>/CargosController?accion=editar&idCargo=${item.id}">Editar</a>
                                            <button class="btn btn-default" type="button" data-toggle="modal" data-target="#confirmationMessage" onclick="jQuery('#idCargo').val('${item.id}');">Eliminar</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
