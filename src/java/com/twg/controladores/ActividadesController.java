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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesController extends HttpServlet {

    private final ActividadesNegocio actividadesNegocio = new ActividadesNegocio();
    private final ProyectosNegocio proyectos = new ProyectosNegocio();
    private final VersionesNegocio versiones = new VersionesNegocio();
    private final EstadosNegocio estados = new EstadosNegocio();
    
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
            case "guardar":
                //REDIRIGIR A LA PANTALLA DE JORMAN
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
        request.setAttribute("proyectos", proyectos.consultarProyectos(null, null, false));
        //pendiente enviar el Id del proyecto para consultar las versiones
        request.setAttribute("versiones", versiones.consultarVersiones(null, null, null, false));
        request.setAttribute("estados", estados.consultarEstados(null, null, null));
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
        
        List<ActividadesBean> listaActividades = actividadesNegocio.consultarActividades2(id, version, descripcion, fecha, estado, responsable);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
        out.println(            "<th>Descripción</th>");
        out.println(            "<th>Versión</th>");
        out.println(            "<th>Fecha estimada inicio</th>");
        out.println(            "<th>Fecha estimada fin</th>");
        out.println(            "<th>Tiempo estimado (h)</th>");
        out.println(            "<th>Estado</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaActividades != null && !listaActividades.isEmpty()){
            for (ActividadesBean actividad : listaActividades) {
                out.println("<tr>");			
                out.println(    "<td>"+actividad.getDescripcion()+"</td>");
                out.println(    "<td>"+actividad.getNombreV()+"</td>");
                out.println(    "<td>"+actividad.getFecha_estimada_inicio()+"</td>");
                out.println(    "<td>"+actividad.getFecha_estimada_terminacion()+"</td>");
                out.println(    "<td>"+actividad.getTiempo_estimado()+"</td>");
                out.println(    "<td>"+actividad.getNombreE()+"</td>");
                out.println(    "<td>");
              out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('"+actividad.getId()+"');\">Eliminar</button>");
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
