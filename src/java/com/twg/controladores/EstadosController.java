package com.twg.controladores;

import com.twg.negocio.EstadosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
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
        String tipoEstado = request.getParameter("tipoEstado");
        String nombre = request.getParameter("nombre");

        Integer id = null;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.ESTADOS);

        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, id, tipoEstado, nombre);
                break;
            case "editar":
                JSONObject object = estadosNegocio.consultarEstado(id);
                response.getWriter().write(object.toString());
                break;
            case "guardar":
                Map<String, Object> result = estadosNegocio.crearEstado(id, tipoEstado, nombre);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    enviarDatos(request, id, tipoEstado, nombre);
                }
                if (result.get("mensajeExito") != null) {
                    mensajeExito = (String) result.get("mensajeExito");
                    enviarDatos(request, null, null, null);
                }
                break;
            case "eliminar":
                result = estadosNegocio.eliminarEstado(id);
                if (result.get("mensajeError") != null) {
                    enviarDatos(request, id, tipoEstado, nombre);
                }
                if (result.get("mensajeExito") != null) {
                    enviarDatos(request, null, null, null);
                }
                break;
            default:
                enviarDatos(request, null, null, null);
                break;
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        if (!accion.equals("consultar") && !accion.equals("editar")) {
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                    request.setAttribute("opcionConsultar", "T");
                }
                if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                    request.setAttribute("opcionGuardar", "T");
                }
            }
            request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
        }
    }

    private void enviarDatos(HttpServletRequest request, Integer id, String tipoEstado, String nombre) {
        //.setAttribute("id", id);
        request.setAttribute("tipoEstado", tipoEstado);
        request.setAttribute("nombre", nombre);
    }

    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer id, String tipoEstado, String nombre) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<EstadosBean> listaEstados = estadosNegocio.consultarEstados(id, tipoEstado, nombre);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Tipo Estado</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaEstados != null && !listaEstados.isEmpty()) {
            for (EstadosBean estado : listaEstados) {
                out.println("<tr>");
//                out.println(    "<td>"+estado.getId()+"</td>");                
                out.println("<td>" + estado.getTipo_estado() + "</td>");
                out.println("<td>" + estado.getNombre() + "</td>");
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarEstado(" + estado.getId() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + estado.getId() + "');\">Eliminar</button>");
                }
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
