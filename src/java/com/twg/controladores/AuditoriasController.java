package com.twg.controladores;

import com.twg.negocio.AuditoriasNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.persistencia.beans.AuditoriasBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen sobre el módulo principal de Auditorías, para la consulta y
 * eliminación.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class AuditoriasController extends HttpServlet {

    private final AuditoriasNegocio auditoriasNegocio = new AuditoriasNegocio();
    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Auditorias
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String mensajeAlerta = "";
        String mensajeExito = "";
        String mensajeError = "";

        String busqueda = request.getParameter("busqueda");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String idStr = request.getParameter("id");
        String fecha_creacion = request.getParameter("fecha_creacion");
        String clasificacion = request.getParameter("clasificacion");
        String accionAud = request.getParameter("accionAud");
        String descripcion = request.getParameter("descripcion");
        String paginaStr = request.getParameter("pagina");

        Integer id = null;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
        }

        Integer id_personaH;
        try {
            id_personaH = Integer.valueOf(request.getParameter("id_personaH"));
        } catch (NumberFormatException e) {
            id_personaH = null;
        }

        Integer pagina;
        try {
            pagina = Integer.valueOf(paginaStr);
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.AUDITORIAS);

        Integer personaSesion = null;
        try {
            personaSesion = (Integer) request.getSession().getAttribute("personaSesion");
        } catch (Exception e) {
        }

        if (permisosPagina != null && !permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
            id_personaH = personaSesion;
        }

        switch (accion) {
            case "detalle":
                JSONObject audit = auditoriasNegocio.consultarAuditoria(id);
                response.getWriter().write(audit.toString());
                break;
            case "consultar":
                cargarTabla(response, permisosPagina, id, id_personaH, fecha_creacion, clasificacion, accionAud, descripcion, pagina);
                break;
            case "eliminar":
                String result = auditoriasNegocio.eliminarAuditoria(id, personaSesion);
                if (result != null && !result.isEmpty()) {
                    mensajeError = result;
                    enviarDatos(request, null, null, null, null, null, null);
                } else {
                    mensajeExito = "La auditoría se elminó correctamente";
                    enviarDatos(request, null, null, null, null, null, null);
                }
                break;
            case "completarPersonas":
                JSONArray arrayPersonasAC = personasNegocio.completarPersonas(busqueda);
                response.getWriter().write(arrayPersonasAC.toJSONString());
                break;
            default:
                enviarDatos(request, null, null, null, null, null, null);
                break;
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        if (!accion.equals("consultar") && !accion.equals("detalle") && !accion.equals("completarPersonas")) {
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                request.setAttribute("opcionConsultar", permisosPagina.contains(Permisos.CONSULTAR.getNombre()));
            }
            request.getRequestDispatcher("jsp/auditorias.jsp").forward(request, response);
        }
    }

    /**
     * Este método se encarga de enviar los atributos de la auditoria, al
     * cliente que realiza la petición.
     *
     * @param request
     * @param id
     * @param id_persona
     * @param fecha_creacion
     * @param clasificacion
     * @param accionAud
     * @param descripcion
     */
    private void enviarDatos(HttpServletRequest request, Integer id, String id_persona, String fecha_creacion,
        String clasificacion, String accionAud, String descripcion) {
        request.setAttribute("id", id);
        request.setAttribute("id_personaH", id_persona);
        request.setAttribute("id_persona", id_persona);
        request.setAttribute("fecha_creacion", fecha_creacion);
        request.setAttribute("clasificacion", clasificacion);
        request.setAttribute("accionAud", accionAud);
        request.setAttribute("descripcion", descripcion);
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre las auditorias
     *
     * @param response
     * @param permisos
     * @param id
     * @param id_persona
     * @param fecha_creacionStr
     * @param clasificacion
     * @param accionAud
     * @param descripcion
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer id, Integer id_persona,
            String fecha_creacionStr, String clasificacion, String accionAud, String descripcion, int pagina) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        int registros = 10;
        int paginasAdicionales = 2;
        String limite = ((pagina - 1) * registros) + "," + registros;
        Date fecha_creacion = null;
        if (fecha_creacionStr != null && !fecha_creacionStr.isEmpty()) {
            try {
                fecha_creacion = sdf.parse(fecha_creacionStr);
            } catch (ParseException e) {
            }
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        List<AuditoriasBean> listaAuditorias = auditoriasNegocio.consultarAuditorias(id, clasificacion, accionAud, descripcion, fecha_creacion, id_persona, limite);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Persona</th>");
        out.println("<th>Clasificación</th>");
        out.println("<th>Acción</th>");
        out.println("<th>Fecha creación</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaAuditorias != null && !listaAuditorias.isEmpty()) {
            for (AuditoriasBean auditoria : listaAuditorias) {
                out.println("<tr>");
                out.println("<td>" + auditoria.getNombrePersona() + "</td>");
                out.println("<td>" + auditoria.getClasificacion() + "</td>");
                out.println("<td>" + auditoria.getAccion() + "</td>");
                out.println("<td>" + sdf2.format(auditoria.getFechaCreacion()) + "</td>");
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarAuditoria(" + auditoria.getId() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + auditoria.getId() + "');\">Eliminar</button>");
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

        /* Manejo de paginación */
        int cantidadAuditorias = auditoriasNegocio.cantidadAuditorias(id, clasificacion, accionAud, descripcion, fecha_creacion, id_persona);
        int cantidadPaginas = 1;
        if (cantidadAuditorias > 0) {
            if (cantidadAuditorias % registros == 0) {
                cantidadPaginas = cantidadAuditorias / registros;
            } else {
                cantidadPaginas = (cantidadAuditorias / registros) + 1;
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
            out.println("       <li><a href=\"javascript:void(llenarTablaAuditorias(1))\"><span>&laquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTablaAuditorias(" + (pagina - 1) + "))\"><span>&lsaquo;</span></a></li>");
        }
        for (int pag = pagina - paginasPrevias; pag <= pagina + paginasPosteriores; pag++) {
            if (pagina == pag) {
                out.println("   <li class=\"active\"><a href=\"javascript:void(0)\"><span>" + pag + "</span></a></li>");
            } else {
                out.println("   <li><a href=\"javascript:void(llenarTablaAuditorias(" + pag + "))\"><span>" + pag + "</span></a></li>");
            }
        }
        if (pagina != cantidadPaginas) {
            out.println("       <li><a href=\"javascript:void(llenarTablaAuditorias(" + (pagina + 1) + "))\"><span>&rsaquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTablaAuditorias(" + cantidadPaginas + "))\"><span>&raquo;</span></a></li>");
        }
        out.println("   </ul>");
        out.println("</nav>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
