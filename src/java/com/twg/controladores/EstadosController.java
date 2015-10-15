package com.twg.controladores;

import com.twg.negocio.EstadosNegocio;
import com.twg.persistencia.beans.EstadosActividadesBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class EstadosController extends HttpServlet {

    private final EstadosNegocio estadosNegocio = new EstadosNegocio();
    

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
        String nombre = request.getParameter("nombre");

        Integer id = null;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
        }

        switch (accion) {
            case "consultar":
                cargarTabla(response, id, nombre);
                break;
            case "editar":
                JSONObject object = estadosNegocio.consultarEstado(id);
                response.getWriter().write(object.toString());
                break;
            case "guardar":
                Map<String, Object> result = estadosNegocio.crearEstado(id, nombre);
                if(result.get("mensajeError") != null){
                    mensajeError = (String)result.get("mensajeError");
                    enviarDatos(request, id, nombre);
                }
                if(result.get("mensajeExito") != null){
                    mensajeExito = (String)result.get("mensajeExito");
                    enviarDatos(request, null, null);
                }
                break;
            case "eliminar":
                result = estadosNegocio.eliminarEstado(id);
                if(result.get("mensajeError") != null){
                    enviarDatos(request, id, nombre);
                }
                if(result.get("mensajeExito") != null){
                    enviarDatos(request, null, null);
                }
                break;
            default:
                enviarDatos(request, null, null);
                break;
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        if(!accion.equals("consultar") && !accion.equals("editar")){
            request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
        }
    }

    private void enviarDatos(HttpServletRequest request, Integer id, String nombre) {
        //.setAttribute("id", id);
        request.setAttribute("nombre", nombre);
    }

    private void cargarTabla(HttpServletResponse response, Integer id, String nombre) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        
        List<EstadosActividadesBean> listaEstadosActividades = estadosNegocio.consultarEstados(id, nombre);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
//        out.println(            "<th>Tipo Estado</th>");
        out.println(            "<th>Nombre</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaEstadosActividades != null && !listaEstadosActividades.isEmpty()){
            for (EstadosActividadesBean estadoActividad : listaEstadosActividades) {
                out.println("<tr>");			
//                out.println(    "<td>"+estadoActividad.getId()+"</td>");                
                out.println(    "<td>"+estadoActividad.getNombre()+"</td>");

                out.println(    "<td>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarEstado("+estadoActividad.getId()+")\">Editar</button>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('"+estadoActividad.getId()+"');\">Eliminar</button>");
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

    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
