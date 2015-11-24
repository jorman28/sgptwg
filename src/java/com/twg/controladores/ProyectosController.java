package com.twg.controladores;

import com.twg.negocio.EstadosNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.ProyectosBean;
import com.twg.persistencia.beans.VersionesBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Pipe
 */
public class ProyectosController extends HttpServlet {

    private final ProyectosNegocio proyectosNegocio = new ProyectosNegocio();
    private final VersionesNegocio versionesNegocio = new VersionesNegocio();
    private final EstadosNegocio estadosNegocio = new EstadosNegocio();
    private final PersonasNegocio personasNegocio = new PersonasNegocio();

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

        String mensajeError = "";
        String mensajeExito = "";
        String mensajeAlerta = "";
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        String redireccion = "jsp/proyectos.jsp";
        String idProyectoStr = request.getParameter("idProyecto");
        String nombreProyecto = request.getParameter("nombreProyecto");
        String fechaInicioProyecto = request.getParameter("fechaInicioProyecto");
        String idPersona = request.getParameter("idPersona");
        Integer idProyecto;
        try {
            idProyecto = Integer.valueOf(idProyectoStr);
        } catch (NumberFormatException e) {
            idProyecto = null;
        }

        String idVersionStr = request.getParameter("idVersion");
        String idProyectoVersion = request.getParameter("idProyectoVersion");
        String fechaInicioVersion = request.getParameter("fechaInicioVersion");
        String fechaFinVersion = request.getParameter("fechaFinVersion");
        String nombreVersion = request.getParameter("nombreVersion");
        String estado = request.getParameter("estado");
        String alcance = request.getParameter("alcance");
        Integer idVersion;
        try {
            idVersion = Integer.valueOf(idVersionStr);
        } catch (NumberFormatException e) {
            idVersion = null;
        }

        String tipoEliminacion = request.getParameter("tipoEliminacion");
        
        String busqueda = request.getParameter("search");

        switch (accion) {
            case "editarProyecto":
                JSONObject proyecto = proyectosNegocio.consultarProyecto(idProyecto);
                response.getWriter().write(proyecto.toJSONString());
                break;
            case "editarVersion":
                JSONObject version = versionesNegocio.consultarVersion(idVersion);
                response.getWriter().write(version.toJSONString());
                break;
            case "guardarProyecto":
                mensajeAlerta = proyectosNegocio.validarDatos(nombreProyecto, fechaInicioProyecto);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = proyectosNegocio.guardarProyecto(idProyectoStr, nombreProyecto, fechaInicioProyecto);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El proyecto ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("idProyecto", idProyectoStr);
                request.setAttribute("nombreProyecto", nombreProyecto);
                request.setAttribute("fechaInicioProyecto", fechaInicioProyecto);
                request.setAttribute("idPersona", idPersona);
                break;
            case "guardarVersion":
                mensajeAlerta = versionesNegocio.validarDatos(nombreVersion, fechaInicioVersion, fechaFinVersion, alcance, idProyectoVersion, estado);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = versionesNegocio.guardarVersion(idVersionStr, nombreVersion, fechaInicioVersion, fechaFinVersion, alcance, idProyectoVersion, estado);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "La versión ha sido guardada con éxito";
                        break;
                    }
                }
                request.setAttribute("idProyectoVersion", idProyectoStr);
                request.setAttribute("idVersion", idVersionStr);
                request.setAttribute("nombreVersion", nombreVersion);
                request.setAttribute("fechaInicioVersion", fechaInicioVersion);
                request.setAttribute("fechaFinVersion", fechaFinVersion);
                request.setAttribute("alcance", alcance);
                request.setAttribute("estado", estado);
                break;
            case "completarPersonas":
                JSONArray array = personasNegocio.completarPersonas(busqueda);
                response.getWriter().write(array.toJSONString());
                break;
            case "eliminar":
                if (tipoEliminacion != null && !tipoEliminacion.isEmpty()) {
                    if (tipoEliminacion.equals("VERSION")) {
                        mensajeError = versionesNegocio.eliminarVersion(idVersion, null);
                        if(mensajeError.isEmpty()){
                            mensajeExito = "La versión ha sido eliminada con éxito";
                        }
                    } else {
                        mensajeError = proyectosNegocio.eliminarProyecto(idProyecto);
                        if(mensajeError.isEmpty()){
                            mensajeExito = "El proyecto ha sido eliminado con éxito";
                        }
                    }
                } else {
                    mensajeError = "No se especificó tipo de eliminación";
                }
                break;
            default:
                break;
        }

        if (!accion.equals("editarProyecto") && !accion.equals("editarVersion") && !accion.equals("completarPersonas")) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.setAttribute("listaProyectos", listarProyectos());
            request.setAttribute("estados", estadosNegocio.consultarEstados(null, null, null));
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }

    private String listarProyectos() {
        String lista = "";
        List<ProyectosBean> listaProyectos = proyectosNegocio.consultarProyectos(null);
        if (listaProyectos != null && !listaProyectos.isEmpty()) {
            for (ProyectosBean proyecto : listaProyectos) {
                lista += "  <div class=\"panel-group\" id=\"proyecto" + proyecto.getId() + "\" role=\"tablist\" aria-multiselectable=\"true\">\n"
                        + "     <div class=\"panel panel-default\">\n"
                        + "         <div class=\"panel-heading\" id=\"headingProyecto" + proyecto.getId() + "\">\n"
                        + "             <h4 class=\"panel-title\">\n"
                        + "                 <div class=\"row\">\n"
                        + "                     <a role=\"button\" data-toggle=\"collapse\" data-parent=\"#proyecto" + proyecto.getId() + "\" href=\"#collapseProyecto" + proyecto.getId() + "\" aria-expanded=\"true\" aria-controls=\"collapseProyecto" + proyecto.getId() + "\">\n"
                        + "                         <div class=\"col-xs-10 col-sm-11 col-md-11 col-lg-11\">\n"
                        + "                             " + proyecto.getNombre() + "\n"
                        + "                         </div>\n"
                        + "                     </a>\n"
                        + "                     <div class=\"col-xs-2 col-sm-1 col-md-1 col-lg-1\">\n"
                        + "                         <span class=\"glyphicon glyphicon-pencil\" onclick=\"editarProyecto(" + proyecto.getId() + ");\"></span>\n"
                        + "                         <span class=\"glyphicon glyphicon-remove\" onclick=\"eliminarProyecto(" + proyecto.getId() + ");\"></span>\n"
                        + "                     </div>\n"
                        + "                 </div>\n"
                        + "             </h4>\n"
                        + "         </div>\n"
                        + "         <div id=\"collapseProyecto" + proyecto.getId() + "\" class=\"panel-collapse collapse\" role=\"tabpanel\" aria-labelledby=\"headingProyecto" + proyecto.getId() + "\">\n"
                        + "             <ul class=\"list-group\">\n";
                List<VersionesBean> listaVersiones = versionesNegocio.consultarVersiones(null, proyecto.getId());
                if (listaVersiones != null && !listaVersiones.isEmpty()) {
                    for (VersionesBean version : listaVersiones) {
                        lista += "                 <li class=\"list-group-item\" id=\"version" + version.getId() + "\">\n"
                                + "                     <div class=\"row\">\n"
                                + "                         <div class=\"col-xs-10 col-sm-11 col-md-11 col-lg-11\">\n"
                                + "                             " + version.getNombre() + "\n"
                                + "                         </div>\n"
                                + "                         <div class=\"col-xs-2 col-sm-1 col-md-1 col-lg-1\">\n"
                                + "                             <span class=\"glyphicon glyphicon-pencil\" onclick=\"editarVersion(" + version.getId() + ")\"></span>\n"
                                + "                             <span class=\"glyphicon glyphicon-remove\" onclick=\"eliminarVersion(" + version.getId() + ");\"></span>\n"
                                + "                         </div>\n"
                                + "                     </div>\n"
                                + "                 </li>\n";
                    }
                }
                lista += "                 <li class=\"list-group-item\">\n"
                        + "                     <div class=\"row\">\n"
                        + "                         <div class=\"col-xs-11 col-sm-11 col-md-11 col-lg-11\">\n"
                        + "                         </div>\n"
                        + "                         <div class=\"col-xs-1 col-sm-1 col-md-1 col-lg-1\">\n"
                        + "                             <span class=\"glyphicon glyphicon-plus\" onclick=\"nuevaVersion(" + proyecto.getId() + ");\"></span>\n"
                        + "                         </div>\n"
                        + "                     </div>\n"
                        + "                 </li>\n"
                        + "             </ul>\n"
                        + "         </div>\n"
                        + "     </div>\n"
                        + " </div>";
            }
        } else {
            lista = "<h3>No se encontraron proyectos en el sistema</h3>";
        }
        return lista;
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
