/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.controladores;

import com.twg.negocio.ActividadesNegocio;
import com.twg.negocio.EstadosNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.Actividades_EmpleadosBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesController extends HttpServlet {

    private final ActividadesNegocio actividadesNegocio = new ActividadesNegocio();
    private final ProyectosNegocio proyectosNegocio = new ProyectosNegocio();
    private final VersionesNegocio versionesNegocio = new VersionesNegocio();
    private final EstadosNegocio estadosNegocio = new EstadosNegocio();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static final String INSERTAR_O_EDITAR = "./jsp/gestionActividades.jsp";
    private static final String LISTAR_ACTIVIDADES = "./jsp/actividades.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String mensajeAlerta = "";
        String mensajeExito = "";
        String mensajeError = "";

        String accion = request.getParameter("accion");

        String idStr = request.getParameter("id"); //consulta y creacion Er
        if (accion == null) {
            accion = "";
        } else if (accion.indexOf("_") != -1) { //esto se hace por tema de modificación, para poder extraer el id del registro
            Integer index = accion.indexOf("_");
            String[] arrayGetId = accion.split("_");
            if (arrayGetId[1] != null) {
                idStr = arrayGetId[1];
                accion = arrayGetId[0];
            }
        }

        //String idStr = request.getParameter("id"); //consulta y creacion Er
        String proyectoStr = request.getParameter("proyecto"); //consulta y creacion Er
        String versionStr = request.getParameter("version"); //consulta y creacion Er
        String descripcion = request.getParameter("descripcion"); //consulta y creacion Er
        String estadoStr = request.getParameter("estado"); //consulta y creacion Er
        String fechaStr = request.getParameter("fecha"); //consulta Er
        String responsableStr = request.getParameter("responsable"); //consulta y creacion Er en creación se recibe el objeto hidden
        String participanteStr = request.getParameter("participante"); //consulta y creacion Er en creación se recibe el valor que hay en el campo participante

        String fecha_estimada_inicioStr = request.getParameter("fecha_estimada_inicio");//creacion
        String fecha_estimada_terminacionStr = request.getParameter("fecha_estimada_terminacion");//creacion
        String fecha_real_inicioStr = request.getParameter("fecha_real_inicio");//creacion
        String fecha_real_terminacionStr = request.getParameter("fecha_real_terminacion");//creacion
        String tiempo_estimadoStr = request.getParameter("tiempo_estimado");//creacion
        String tiempo_invertidoStr = request.getParameter("tiempo_invertido");//creacion

        Integer id = null;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
        }

        Integer proyecto = null;
        try {
            proyecto = Integer.valueOf(proyectoStr);
        } catch (NumberFormatException e) {
        }

        Integer responsable = null;
        try {
            responsable = Integer.valueOf(responsableStr);
        } catch (NumberFormatException e) {
        }

        Integer version = null;
        try {
            version = Integer.valueOf(versionStr);
        } catch (NumberFormatException e) {
        }

        Integer estado = null;
        try {
            estado = Integer.valueOf(estadoStr);
        } catch (NumberFormatException e) {
        }

        Date fecha_estimada_inicio = null;
        try {
            fecha_estimada_inicio = sdf.parse(fecha_estimada_inicioStr);
        } catch (Exception e) {
        }

        Date fecha_estimada_terminacion = null;
        try {
            fecha_estimada_terminacion = sdf.parse(fecha_estimada_terminacionStr);
        } catch (Exception e) {
        }

        Date fecha_real_inicio = null;
        try {
            fecha_real_inicio = sdf.parse(fecha_real_inicioStr);
        } catch (Exception e) {
        }

        Date fecha_real_terminacion = null;
        try {
            fecha_real_terminacion = sdf.parse(fecha_real_terminacionStr);
        } catch (Exception e) {
        }

        Double tiempo_estimado = null;
        if (tiempo_estimadoStr != null) {
            try {
                tiempo_estimado = Double.valueOf(tiempo_estimadoStr);
            } catch (NumberFormatException e) {
            }
        }

        Double tiempo_invertido = null;
        if (tiempo_invertidoStr != null) {
            try {
                tiempo_invertido = Double.valueOf(tiempo_invertidoStr);
            } catch (NumberFormatException e) {
            }
        }

        String tipoEliminacion = request.getParameter("tipoEliminacion");

        switch (accion) {
            case "consultar":
                cargarTabla(response, id, proyectoStr, versionStr, descripcion, estadoStr, fechaStr, responsableStr);
                break;
            case "gestionarActividad":
            case "limpiarGestion":
                request.setAttribute("estados", estadosNegocio.consultarEstados(null, null, null));
                request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, null, null, false));
                if (idStr != null && !idStr.isEmpty()) {
                    ActividadesBean actividad = new ActividadesBean();
                    actividad = actividadesNegocio.consultarActividadI(id);
//                    Actividades_EmpleadosBean actividad_empleado = new Actividades_EmpleadosBean();
//                    actividad_empleado = actividadesNegocio.consultarActividad_Empleado();
                    request.setAttribute("id", actividad.getId().toString());
                    request.setAttribute("responsable", responsable);
                    request.setAttribute("participante", participanteStr);
                    request.setAttribute("version", actividad.getVersion().toString());
                    request.setAttribute("descripcion", actividad.getDescripcion());
                    request.setAttribute("fecha_estimada_inicio", actividad.getFecha_estimada_inicio() != null ? sdf.format(actividad.getFecha_estimada_inicio()) : "");
                    request.setAttribute("fecha_estimada_terminacion", actividad.getFecha_estimada_terminacion() != null ? sdf.format(actividad.getFecha_estimada_terminacion()) : "");
                    request.setAttribute("fecha_real_inicio", actividad.getFecha_real_inicio() != null ? sdf.format(actividad.getFecha_real_inicio()) : "");
                    request.setAttribute("fecha_real_terminacion", actividad.getFecha_real_terminacion() != null ? sdf.format(actividad.getFecha_real_terminacion()) : "");
                    request.setAttribute("tiempo_estimado", actividad.getTiempo_estimado());
                    request.setAttribute("tiempo_invertido", actividad.getTiempo_invertido());
                    request.setAttribute("estado", actividad.getEstado());
                }
                request.getRequestDispatcher(INSERTAR_O_EDITAR).forward(request, response);
                break;

            case "guardar":
                Map<String, Object> result = actividadesNegocio.guardarActividad(idStr, versionStr, participanteStr, responsableStr, descripcion, fecha_estimada_inicioStr, fecha_estimada_terminacionStr, fecha_real_inicioStr, fecha_real_terminacionStr, tiempo_estimadoStr, tiempo_invertidoStr, estadoStr);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    request.setAttribute("mensajeError", mensajeError);
                    request.setAttribute("estados", estadosNegocio.consultarEstados(null, null, null));
                    request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, null, null, false));
                    enviarDatosCreacionEdicion(request, idStr, responsableStr, participanteStr, versionStr, descripcion, fecha_estimada_inicioStr, fecha_estimada_terminacionStr, fecha_real_inicioStr, fecha_real_terminacionStr, tiempo_estimadoStr, tiempo_invertidoStr, estadoStr);
                    request.getRequestDispatcher(INSERTAR_O_EDITAR).forward(request, response);
                    break;
                }
                if (result.get("mensajeExito") != null) {
                    mensajeExito = (String) result.get("mensajeExito");
                    enviarDatosCreacionEdicion(request, null, null, null, null, null, null, null, null, null, null, null, null);
                }
                break;
            case "eliminar":
                mensajeError = actividadesNegocio.eliminarActividad(id);
                if (mensajeError.isEmpty()) {
                    mensajeExito = "La actividad ha sido eliminada con éxito";
                }
                break;
            default:
                break;
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
        //pendiente enviar el Id del proyecto para consultar las versiones
        request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, null, null, false));
        request.setAttribute("estados", estadosNegocio.consultarEstados(null, null, null));
        if (!accion.equals("consultar") && !accion.equals("editar")) {
            request.getRequestDispatcher(LISTAR_ACTIVIDADES).forward(request, response);
        }
    }

    private void enviarDatos(HttpServletRequest request, Integer id, String proyecto, String version, String descripcion,
        String estado, String fecha, String responsable) {
        request.setAttribute("id", id);
        request.setAttribute("proyecto", proyecto);
        request.setAttribute("version", version);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("estado", estado);
        request.setAttribute("fecha", fecha);
        request.setAttribute("responsable", responsable);
    }

    private void enviarDatosCreacionEdicion(HttpServletRequest request, String id, String responsable, String participante, String version, String descripcion, String fecha_estimada_inicio, String fecha_estimada_terminacion, String fecha_real_inicio, String fecha_real_terminacion, String tiempo_estimado, String tiempo_invertido, String estado) {
        request.setAttribute("id", id);
        request.setAttribute("responsable", responsable);
        request.setAttribute("participante", participante);
        request.setAttribute("version", version);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("fecha_estimada_inicio", fecha_estimada_inicio);
        request.setAttribute("fecha_estimada_terminacion", fecha_estimada_terminacion);
        request.setAttribute("fecha_real_inicio", fecha_real_inicio);
        request.setAttribute("fecha_real_terminacion", fecha_real_terminacion);
        request.setAttribute("tiempo_estimado", tiempo_estimado);
        request.setAttribute("tiempo_invertido", tiempo_invertido);
        request.setAttribute("estado", estado);
    }

    private void cargarTabla(HttpServletResponse response, Integer id, String proyecto, String version, String descripcion,
            String estado, String fecha, String responsable) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<ActividadesBean> listaActividades = actividadesNegocio.consultarActividades2(id, version, descripcion, fecha, estado, responsable);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Descripción</th>");
        out.println("<th>Versión</th>");
        out.println("<th>Fecha estimada inicio</th>");
        out.println("<th>Fecha estimada fin</th>");
        out.println("<th>Tiempo estimado (h)</th>");
        out.println("<th>Estado</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaActividades != null && !listaActividades.isEmpty()) {
            for (ActividadesBean actividad : listaActividades) {
                out.println("<input type=\"hidden\" id=\"id\" name=\"id\" value=" + actividad.getId() + " />");
                out.println("<tr>");
                out.println("<td>" + actividad.getDescripcion() + "</td>");
                out.println("<td>" + actividad.getNombreV() + "</td>");
                out.println("<td>" + actividad.getFecha_estimada_inicio() + "</td>");
                out.println("<td>" + actividad.getFecha_estimada_terminacion() + "</td>");
                out.println("<td>" + actividad.getTiempo_estimado() + "</td>");
                out.println("<td>" + actividad.getNombreE() + "</td>");
                out.println("<td>");

                //out.println("<button class=\"btn btn-default\" type=\"submit\" name=\"accion\" id= '"+ actividad.getId() + "' value=\"gestionarActividad\"> Gestionar2</button>");
                out.println("<button class=\"btn btn-default\" type=\"submit\" name=\"accion\" id=\"gestionarActividad\" value='gestionarActividad_" + actividad.getId() + "'> Gestionar</button>");
                //out.println("<button class=\"btn btn-default\" type=\"button\" name=\"accion\" id="+ actividad.getId() +">Gestionar</button>");
                out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + actividad.getId() + "');\">Eliminar</button>");
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");
            out.println("<td colspan=\"6\">No se encontraron registros</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
