/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twg.controladores;

import com.twg.negocio.ActividadesNegocio;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.VersionesBean;
import com.twg.persistencia.beans.ProyectosBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static javax.print.attribute.Size2DSyntax.MM;
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
        if (accion == null) {
            accion = "";
        }

        String idStr = request.getParameter("id");
        String proyecto = request.getParameter("proyecto");
        String versionStr = request.getParameter("version");
        String descripcion = request.getParameter("descripcion");
        String estadoStr = request.getParameter("estado");
        String fechaStr = request.getParameter("fecha");
        String responsable = request.getParameter("responsable");

        Integer id = null;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
        }

        switch (accion) {
            case "consultar":
                cargarTabla(response, id, proyecto, versionStr, descripcion, estadoStr, fechaStr, responsable);
                break;
//            case "guardar":
//                //REDIRIGIR A LA PANTALLA DE JORMAN
//                break;
//            case "eliminar":
//                Map<String, Object> result = actividadesNegocio.eliminarEstado(id);
//                if(result.get("mensajeError") != null){
//                    enviarDatos(request, id, proyecto, version, descripcion, estado, fecha, responsable);
//                }
//                if(result.get("mensajeExito") != null){
//                    enviarDatos(request, id, proyecto, version, descripcion, estado, fecha, responsable);
//                }
//                break;
            default:
//                enviarDatos(request, id, proyecto, versionStr, descripcion, estado, fecha, responsable);
                break;
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        if(!accion.equals("consultar") && !accion.equals("editar")){
            request.getRequestDispatcher("jsp/actividades.jsp").forward(request, response);
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

    private void cargarTabla(HttpServletResponse response, Integer id, String proyecto, String version, String descripcion, 
            String estado, String fecha, String responsable) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        
        List<ActividadesBean> listaActividades = actividadesNegocio.consultarActividades2(id, version, descripcion, fecha, fecha, fecha, fecha, estado, responsable);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
        out.println(            "<th>Descripción</th>");
        out.println(            "<th>Versión</th>");
        out.println(            "<th>Fecha estimada</th>");
        out.println(            "<th>Tiempo estimado</th>");
        out.println(            "<th>Estado</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaActividades != null && !listaActividades.isEmpty()){
            for (ActividadesBean actividad : listaActividades) {
                out.println("<tr>");			
                out.println(    "<td>"+actividad.getDescripcion()+"</td>");
                out.println(    "<td>"+actividad.getVersion()+"</td>");
                out.println(    "<td>"+actividad.getFecha_estimada_inicio()+"</td>");
                out.println(    "<td>"+actividad.getTiempo_estimado()+"</td>");
                out.println(    "<td>"+actividad.getEstado()+"</td>");
                out.println(    "<td>");
//              out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('"+actividad.getId()+"');\">Eliminar</button>");
                //AGREAR BOTÓN PARA IR A LA PANTALLA DE JORMAN:
                out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\";\">Gestionar</button>");
                out.println(    "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");			
            out.println(        "<td colspan=\"6\">No se encontraron registros</td>");
            out.println(    "</tr>");
        }
        out.println(    "</tbody>");
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
