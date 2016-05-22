package com.twg.controladores;

import com.twg.negocio.PerfilesNegocio;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.PerfilesBean;
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

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen sobre el módulo principal de Permisos, así como guardar, consultar,
 * modificar o eliminar la información.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PermisosController extends HttpServlet {

    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de permisos
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String mensajeError = "";
        String mensajeExito = "";
        String nombrePerfil = request.getParameter("nombrePerfil");
        String paginaStr = request.getParameter("pagina");

        Integer idPerfil = null;
        try {
            idPerfil = Integer.valueOf(request.getParameter("idPerfil"));
        } catch (NumberFormatException e) {
        }

        Integer pagina;
        try {
            pagina = Integer.valueOf(paginaStr);
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        String[] permisosSeleccionados;
        permisosSeleccionados = request.getParameterValues("permisos");

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.PERMISOS);

        String personaSesion = "";
        try {
            personaSesion = String.valueOf(request.getSession().getAttribute("personaSesion"));
        } catch (Exception e) {
            System.err.print("Error obteniendo la persona en sesion");
        }
        
        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, nombrePerfil, pagina);
                break;
            case "editar":
                JSONObject perfil = perfilesNegocio.consultarPerfil(idPerfil, null);
                response.getWriter().write(perfil.toString());
                break;
            case "guardar":
                Map<String, Object> resultado = perfilesNegocio.guardarPerfil(idPerfil, nombrePerfil, personaSesion);
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
                resultado = perfilesNegocio.guardarPermisos(idPerfilPermiso, permisosSeleccionados, personaSesion);
                if (resultado.get("mensajeError") != null) {
                    mensajeError = (String) resultado.get("mensajeError");
                }
                if (resultado.get("mensajeExito") != null) {
                    mensajeExito = (String) resultado.get("mensajeExito");
                }
                break;
            case "eliminar":
                resultado = perfilesNegocio.eliminarPerfil(idPerfil, personaSesion);
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
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                    request.setAttribute("opcionConsultar", "T");
                }
                if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                    request.setAttribute("opcionGuardar", "T");
                }
            }
            request.getRequestDispatcher("jsp/permisos.jsp").forward(request, response);
        }
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre los Permisos.
     *
     * @param response
     * @param permisos
     * @param nombrePerfil
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, String nombrePerfil, int pagina) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        int registros = 10;
        int paginasAdicionales = 2;
        String limite = ((pagina - 1) * registros) + "," + registros;
        List<PerfilesBean> listaPerfiles = perfilesNegocio.consultarPerfiles(null, nombrePerfil, limite);
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
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarPerfil(" + perfil.getId() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idPerfil').val('" + perfil.getId() + "');\">Eliminar</button>");
                }
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.PERMISOS.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"obtenerPermisos(" + perfil.getId() + ");\">Permisos</button>");
                }
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

        /* Manejo de paginación */
        int cantidadPerfiles = perfilesNegocio.cantidadPerfiles(null, nombrePerfil);
        int cantidadPaginas = 1;
        if (cantidadPerfiles > 0) {
            if (cantidadPerfiles % registros == 0) {
                cantidadPaginas = cantidadPerfiles / registros;
            } else {
                cantidadPaginas = (cantidadPerfiles / registros) + 1;
            }
        }
        int paginasPrevias = paginasAdicionales;
        if (pagina <= paginasAdicionales) {
            paginasPrevias = (1 - pagina) * -1;
        }
        int paginasPosteriores = paginasAdicionales;
        if (paginasPrevias < paginasAdicionales) {
            paginasPosteriores += paginasAdicionales - paginasPrevias;
        }
        if (pagina + paginasPosteriores > cantidadPaginas) {
            paginasPosteriores = cantidadPaginas - pagina;
        }
        if (paginasPosteriores < paginasAdicionales && pagina - (paginasPrevias + paginasAdicionales - paginasPosteriores) > 0) {
            paginasPrevias += paginasAdicionales - paginasPosteriores;
        }
        out.println("<nav>");
        out.println("   <ul class=\"pagination\">");
        if (pagina != 1) {
            out.println("       <li><a href=\"javascript:void(llenarTabla(1))\"><span>&laquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + (pagina - 1) + "))\"><span>&lsaquo;</span></a></li>");
        }
        for (int pag = pagina - paginasPrevias; pag <= pagina + paginasPosteriores; pag++) {
            if (pagina == pag) {
                out.println("   <li class=\"active\"><a href=\"javascript:void(0)\"><span>" + pag + "</span></a></li>");
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
