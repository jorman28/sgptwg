package com.twg.controladores;

import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.TiposDocumentoNegocio;
import com.twg.negocio.UsuariosNegocio;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.beans.UsuariosBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class UsuariosController extends HttpServlet {

    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();
    private final TiposDocumentoNegocio tiposDocumentoNegocio = new TiposDocumentoNegocio();
    private final UsuariosNegocio usuariosNegocio = new UsuariosNegocio();

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

        String idPersonaStr = request.getParameter("idPersona");
        String documento = request.getParameter("documento");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String nombreUsuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");
        String perfilStr = request.getParameter("perfil");
        String activo = request.getParameter("activo");

        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }

        Integer perfil = null;
        try {
            perfil = Integer.valueOf(perfilStr);
        } catch (NumberFormatException e) {
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.USUARIOS);

        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
                break;
            case "editar":
                JSONObject object = usuariosNegocio.consultarUsuario(idPersona);
                response.getWriter().write(object.toString());
                break;
            case "guardar":
                Map<String, Object> result = usuariosNegocio.crearUsuario(idPersona, nombreUsuario, clave, clave2, perfil, activo, documento, tipoDocumento);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    enviarDatos(request, idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
                }
                if (result.get("mensajeExito") != null) {
                    mensajeExito = (String) result.get("mensajeExito");
                    enviarDatos(request, null, null, null, null, null, null);
                }
                break;
            case "eliminar":
                result = usuariosNegocio.eliminarUsuario(idPersona);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    enviarDatos(request, idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
                }
                if (result.get("mensajeExito") != null) {
                    mensajeExito = (String) result.get("mensajeExito");
                    enviarDatos(request, null, null, null, null, null, null);
                }
                break;
            default:
                enviarDatos(request, null, null, null, null, null, null);
                break;
        }

        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("tiposDocumentos", tiposDocumentoNegocio.consultarTiposDocumentos());
        request.setAttribute("perfiles", perfilesNegocio.consultarPerfiles());
        if (!accion.equals("consultar") && !accion.equals("editar")) {
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                    request.setAttribute("opcionConsultar", "T");
                }
                if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                    request.setAttribute("opcionGuardar", "T");
                }
            }
            request.getRequestDispatcher("jsp/usuarios.jsp").forward(request, response);
        }
    }

    private void enviarDatos(HttpServletRequest request, Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento) {
        request.setAttribute("idPersona", idPersona);
        request.setAttribute("tipoDocumento", tipoDocumento);
        request.setAttribute("documento", documento);
        request.setAttribute("usuario", nombreUsuario);
        request.setAttribute("clave", "");
        request.setAttribute("perfil", perfil);
        request.setAttribute("activo", activo);
    }

    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<UsuariosBean> listaUsuarios = usuariosNegocio.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Tipo documento</th>");
        out.println("<th>Documento</th>");
        out.println("<th>Usuario</th>");
        out.println("<th>Perfil</th>");
        out.println("<th>Estado</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
            for (UsuariosBean usuario : listaUsuarios) {
                out.println("<tr>");
                out.println("<td>" + usuario.getDescripcionTipoDocumento() + "</td>");
                out.println("<td>" + usuario.getDocumento() + "</td>");
                out.println("<td>" + usuario.getUsuario() + "</td>");
                out.println("<td>" + usuario.getDescripcionPerfil() + "</td>");
                if (usuario.getActivo().equals("T")) {
                    out.println("<td>Activo</td>");
                } else {
                    out.println("<td>Inactivo</td>");
                }
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarUsuario(" + usuario.getIdPersona() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idPersona').val('" + usuario.getIdPersona() + "');\">Eliminar</button>");
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
