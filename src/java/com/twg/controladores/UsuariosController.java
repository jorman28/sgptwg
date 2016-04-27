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

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen sobre el módulo principal de Usuarios, así como guardar, consultar,
 * modificar o eliminar la información.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class UsuariosController extends HttpServlet {

    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();
    private final TiposDocumentoNegocio tiposDocumentoNegocio = new TiposDocumentoNegocio();
    private final UsuariosNegocio usuariosNegocio = new UsuariosNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Usuarios
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
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
        String paginaStr = request.getParameter("pagina");

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

        Integer pagina;
        try {
            pagina = Integer.valueOf(paginaStr);
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.USUARIOS);

        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento, pagina);
                break;
            case "editar":
                JSONObject object = usuariosNegocio.consultarUsuario(idPersona);
                response.getWriter().write(object.toString());
                break;
            case "guardar":
                Map<String, Object> result = usuariosNegocio.crearUsuario(idPersona, nombreUsuario, clave, clave2, perfil, activo, documento, tipoDocumento);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    enviarDatos(request, idPersona, nombreUsuario, perfil, activo, (String) result.get("documento"), (String) result.get("tipoDocumento"));
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

    /**
     * Este método se encarga de enviar los atributos del Usuario, al cliente
     * que realiza la petición.
     *
     * @param request
     * @param idPersona
     * @param nombreUsuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     */
    private void enviarDatos(HttpServletRequest request, Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento) {
        request.setAttribute("idPersona", idPersona);
        request.setAttribute("tipoDocumento", tipoDocumento);
        request.setAttribute("documento", documento);
        request.setAttribute("usuario", nombreUsuario);
        request.setAttribute("clave", "");
        request.setAttribute("perfil", perfil);
        request.setAttribute("activo", activo);
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre los Usuarios.
     *
     * @param response
     * @param permisos
     * @param idPersona
     * @param nombreUsuario
     * @param perfil
     * @param activo
     * @param documento
     * @param tipoDocumento
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer idPersona, String nombreUsuario, Integer perfil, String activo, String documento, String tipoDocumento, int pagina) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        int cantidadRegistros = 15;
        String limite = (pagina * cantidadRegistros - cantidadRegistros) + "," + cantidadRegistros;
        List<UsuariosBean> listaUsuarios = usuariosNegocio.consultarUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento, limite);
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
        int cantidadUsuarios = usuariosNegocio.cantidadUsuarios(idPersona, nombreUsuario, perfil, activo, documento, tipoDocumento);
        int cantidadPaginas = 1;
        if (cantidadUsuarios > 0) {
            if (cantidadUsuarios % cantidadRegistros == 0) {
                cantidadPaginas = cantidadUsuarios / cantidadRegistros;
            } else {
                cantidadPaginas = (cantidadUsuarios / cantidadRegistros) + 1;
            }
        }
        out.println("<nav>");
        out.println("   <ul class=\"pagination\">");
        if (pagina != 1) {
            out.println("       <li><a href=\"javascript:void(llenarTabla(1))\"><span>&laquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + (pagina - 1) + "))\"><span>&lsaquo;</span></a></li>");
        }
        for (int pag = 1; pag <= cantidadPaginas; pag++) {
            if (pagina == pag) {
                out.println("   <li class=\"active\"><a href=\"javascript:void(llenarTabla(" + pag + "))\"><span>" + pag + "</span></a></li>");
            } else {
                out.println("   <li><a href=\"javascript:void(llenarTabla(" + pag + "))\"><span>" + pag + "</span></a></li>");
            }
        }
        if (pagina != cantidadPaginas) {
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + (pagina + 1) + "))\"><span>&rsaquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + cantidadPaginas + "))\"><span>&raquo;</span></a></li>");
        }
        out.println("   </ul>");
        out.println("</nav>");
    }

    /**
     * @param reqeust
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    /**
     * @param reqeust
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
