package com.twg.controladores;

import com.twg.negocio.PerfilesNegocio;
import com.twg.persistencia.beans.PerfilesBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Pipe
 */
public class PermisosController extends HttpServlet {

    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensajeError = "";
        String mensajeExito = "";
        String nombrePerfil = request.getParameter("nombrePerfil");
        Integer idPerfil = null;
        try {
            idPerfil = Integer.valueOf(request.getParameter("idPerfil"));
        } catch (NumberFormatException e) {
        }

        String[] permisosSeleccionados;
        permisosSeleccionados = request.getParameterValues("permisos");

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "consultar":
                cargarTabla(response, nombrePerfil);
                break;
            case "editar":
                JSONObject perfil = perfilesNegocio.consultarPerfil(idPerfil, null);
                response.getWriter().write(perfil.toString());
                break;
            case "guardar":
                Map<String, Object> resultado = perfilesNegocio.guardarPerfil(idPerfil, nombrePerfil);
                if (resultado.get("mensajeError") != null) {
                    mensajeError = (String) resultado.get("mensajeError");
                    request.setAttribute("idPerfil", idPerfil);
                    request.setAttribute("nombrePerfil", nombrePerfil);
                }
                if (resultado.get("mensajeExito") != null) {
                    mensajeExito = (String) resultado.get("mensajeExito");
                }
                break;
            case "guardarPermisos":
                Integer idPerfilPermiso = null;
                try {
                    idPerfilPermiso = Integer.valueOf(request.getParameter("perfilPermiso"));
                } catch (NumberFormatException e) {
                }
                resultado = perfilesNegocio.guardarPermisos(idPerfilPermiso, permisosSeleccionados);
                if (resultado.get("mensajeError") != null) {
                    mensajeError = (String) resultado.get("mensajeError");
                }
                if (resultado.get("mensajeExito") != null) {
                    mensajeExito = (String) resultado.get("mensajeExito");
                }
                break;
            case "eliminar":
                resultado = perfilesNegocio.eliminarPerfil(idPerfil);
                if (resultado.get("mensajeError") != null) {
                    mensajeError = (String) resultado.get("mensajeError");
                }
                if (resultado.get("mensajeExito") != null) {
                    mensajeExito = (String) resultado.get("mensajeExito");
                }
                break;
            case "consultarPermisos":
                JSONObject permisos = perfilesNegocio.obtenerPermisosPerfil(idPerfil);
                response.getWriter().write(permisos.toString());
                break;
            default:
                break;
        }
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        if (!accion.equals("consultar") && !accion.equals("editar") && !accion.equals("consultarPermisos")) {
            request.getRequestDispatcher("jsp/permisos.jsp").forward(request, response);
        }
    }

    private void cargarTabla(HttpServletResponse response, String nombrePerfil) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<PerfilesBean> listaPerfiles = perfilesNegocio.consultarPerfiles(null, nombrePerfil);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Perfil</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaPerfiles != null && !listaPerfiles.isEmpty()) {
            for (PerfilesBean perfil : listaPerfiles) {
                out.println("<tr>");
                out.println("<td>" + perfil.getNombre() + "</td>");
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarPerfil(" + perfil.getId() + ")\">Editar</button>");
                out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idPerfil').val('" + perfil.getId() + "');\">Eliminar</button>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"obtenerPermisos(" + perfil.getId() + ");\">Permisos</button>");
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");
            out.println("<td colspan=\"2\">No se encontraron registros</td>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
