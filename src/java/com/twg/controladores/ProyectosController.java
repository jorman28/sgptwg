package com.twg.controladores;

import com.twg.negocio.ComentariosNegocio;
import com.twg.negocio.EstadosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.beans.ProyectosBean;
import com.twg.persistencia.beans.VersionesBean;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen sobre el módulo principal de proyectos, donde también se incluye el
 * manejo de versiones. Las peticiones pueden ser para guardar, consultar,
 * modificar o eliminar la información.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ProyectosController extends HttpServlet {

    private final ProyectosNegocio proyectosNegocio = new ProyectosNegocio();
    private final VersionesNegocio versionesNegocio = new VersionesNegocio();
    private final EstadosNegocio estadosNegocio = new EstadosNegocio();
    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final ComentariosNegocio comentariosNegocio = new ComentariosNegocio();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Proyectos y versiones.
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
        String[] idPersonas = request.getParameterValues("idPersonas");

        String idVersionStr = request.getParameter("idVersion");
        String idProyectoVersion = request.getParameter("idProyectoVersion");
        String fechaInicioVersion = request.getParameter("fechaInicioVersion");
        String fechaFinVersion = request.getParameter("fechaFinVersion");
        String nombreVersion = request.getParameter("nombreVersion");
        String estado = request.getParameter("estado");
        String costo = request.getParameter("costo");
        String alcance = request.getParameter("alcance");
        Integer idVersion;
        try {
            idVersion = Integer.valueOf(idVersionStr);
        } catch (NumberFormatException e) {
            idVersion = null;
        }

        String tipoEliminacion = request.getParameter("tipoEliminacion");
        String busqueda = request.getParameter("search");
        String busquedaProyecto = request.getParameter("busquedaProyecto");

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.VERSIONES);

        Integer personaSesion;
        try {
            personaSesion = (Integer) request.getSession(false).getAttribute("personaSesion");
        } catch (Exception e) {
            personaSesion = null;
        }

        switch (accion) {
            case "editarProyecto":
                JSONObject proyecto = proyectosNegocio.consultarProyecto(idProyecto);
                proyecto.put("editarPersonas", permisosPagina != null && permisosPagina.contains(Permisos.GUARDAR_PROYECTO.getNombre()));
                response.getWriter().write(proyecto.toJSONString());
                break;
            case "editarVersion":
                JSONObject version = versionesNegocio.consultarVersion(idVersion);
                version.put("comentarios", comentariosNegocio.listaComentarios(permisosPagina, personaSesion, comentariosNegocio.TIPO_VERSION, idVersion));
                response.getWriter().write(version.toJSONString());
                break;
            case "guardarProyecto":
                mensajeAlerta = proyectosNegocio.validarDatos(idProyecto, nombreProyecto, fechaInicioProyecto);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = proyectosNegocio.guardarProyecto(idProyectoStr, nombreProyecto, fechaInicioProyecto, idPersonas, personaSesion);
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
                mensajeAlerta = versionesNegocio.validarDatos(idVersion, nombreVersion, fechaInicioVersion, fechaFinVersion, alcance, idProyectoVersion, estado, costo);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = versionesNegocio.guardarVersion(idVersionStr, nombreVersion, fechaInicioVersion, fechaFinVersion, alcance, idProyectoVersion, estado, costo, personaSesion);
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
                request.setAttribute("costo", costo);
                break;
            case "completarPersonas":
                JSONArray array = personasNegocio.completarPersonas(busqueda);
                response.getWriter().write(array.toJSONString());
                break;
            case "eliminar":
                if (tipoEliminacion != null && !tipoEliminacion.isEmpty()) {
                    switch (tipoEliminacion) {
                        case "VERSION":
                            mensajeError = versionesNegocio.eliminarVersion(idVersion, null, personaSesion);
                            if (mensajeError.isEmpty()) {
                                mensajeExito = "La versión ha sido eliminada con éxito";
                            }
                            break;
                        default:
                            mensajeError = proyectosNegocio.eliminarProyecto(idProyecto, personaSesion);
                            if (mensajeError.isEmpty()) {
                                mensajeExito = "El proyecto ha sido eliminado con éxito";
                            }
                            break;
                    }
                } else {
                    mensajeError = "No se especificó tipo de eliminación";
                }
                break;
            case "guardarComentario":
                String comentario = request.getParameter("comentario");
                JSONObject comentarioGuardado = new JSONObject();
                mensajeAlerta = comentariosNegocio.validarDatos(comentario);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = comentariosNegocio.guardarComentario(null, personaSesion, comentario, comentariosNegocio.TIPO_VERSION, idVersion);
                    if (mensajeError.isEmpty()) {
                        comentarioGuardado.put("comentarios", comentariosNegocio.listaComentarios(permisosPagina, personaSesion, comentariosNegocio.TIPO_VERSION, idVersion));
                    } else {
                        comentarioGuardado.put("mensajeError", mensajeError);
                    }
                } else {
                    comentarioGuardado.put("mensajeAlerta", mensajeAlerta);
                }
                response.getWriter().write(comentarioGuardado.toJSONString());
                break;
            case "eliminarComentario":
                Integer idComentario = Integer.valueOf(request.getParameter("idComentario"));
                JSONObject comentarioEliminado = new JSONObject();
                mensajeError = comentariosNegocio.eliminarComentario(idComentario, personaSesion);
                if (mensajeError.isEmpty()) {
                    comentarioEliminado.put("comentarios", comentariosNegocio.listaComentarios(permisosPagina, personaSesion, comentariosNegocio.TIPO_VERSION, idVersion));
                } else {
                    comentarioEliminado.put("mensajeError", mensajeError);
                }
                response.getWriter().write(comentarioEliminado.toJSONString());
                break;
            default:
                break;
        }

        if (!accion.equals("editarProyecto") && !accion.equals("editarVersion")
                && !accion.equals("completarPersonas") && !accion.equals("guardarComentario") && !accion.equals("eliminarComentario")) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            Integer persona = null;
            if (permisosPagina != null && !permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                persona = personaSesion;
            }
            request.setAttribute("listaProyectos", listarProyectos(busquedaProyecto, permisosPagina, persona));
            request.setAttribute("estados", estadosNegocio.consultarEstados(null, "VERSIONES", null, null, null, null, null));
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                request.setAttribute("opcionConsultar", permisosPagina.contains(Permisos.CONSULTAR.getNombre()));
                request.setAttribute("opcionCrearProyecto", permisosPagina.contains(Permisos.CREAR_PROYECTO.getNombre()));
                request.setAttribute("opcionGuardarProyecto", permisosPagina.contains(Permisos.GUARDAR_PROYECTO.getNombre()));
                request.setAttribute("opcionGuardarVersion", permisosPagina.contains(Permisos.GUARDAR_VERSION.getNombre()));
                request.setAttribute("opcionComentar", permisosPagina.contains(Permisos.COMENTAR.getNombre()));
            }
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }

    /**
     * Método encargado de pintar el listado de registros que hay sobre los
     * proyectos. Al desplegar cada uno de ellos, se consultan sus versiones y
     * se listan en su interior.
     *
     * @param nombre Parámetro utilizado para filtrar por un proyecto
     * específico.
     * @param permisos
     * @return
     */
    private String listarProyectos(String nombre, List<String> permisos, Integer personaSesion) {
        String lista = "";
        List<ProyectosBean> listaProyectos = proyectosNegocio.consultarProyectos(null, nombre, false, personaSesion);
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
                        + "                         <span class=\"glyphicon glyphicon-pencil\" onclick=\"editarProyecto(" + proyecto.getId() + ");\"></span>\n";
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR_PROYECTO.getNombre())) {
                    lista += "                         <span class=\"glyphicon glyphicon-remove\" onclick=\"eliminarProyecto(" + proyecto.getId() + ");\"></span>\n";
                }
                lista += "                     </div>\n"
                        + "                 </div>\n"
                        + "             </h4>\n"
                        + "         </div>\n"
                        + "         <div id=\"collapseProyecto" + proyecto.getId() + "\" class=\"panel-collapse collapse\" role=\"tabpanel\" aria-labelledby=\"headingProyecto" + proyecto.getId() + "\">\n"
                        + "             <ul class=\"list-group\">\n";
                List<VersionesBean> listaVersiones = versionesNegocio.consultarVersiones(null, proyecto.getId(), null, false);
                if (listaVersiones != null && !listaVersiones.isEmpty()) {
                    for (VersionesBean version : listaVersiones) {
                        lista += "                 <li class=\"list-group-item\" id=\"version" + version.getId() + "\">\n"
                                + "                     <div class=\"row\">\n"
                                + "                         <div class=\"col-xs-10 col-sm-11 col-md-11 col-lg-11\">\n"
                                + "                             " + version.getNombre() + "\n"
                                + "                         </div>\n"
                                + "                         <div class=\"col-xs-2 col-sm-1 col-md-1 col-lg-1\">\n"
                                + "                             <span class=\"glyphicon glyphicon-pencil\" onclick=\"editarVersion(" + version.getId() + ")\"></span>\n";
                        if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR_VERSION.getNombre())) {
                            lista += "                             <span class=\"glyphicon glyphicon-remove\" onclick=\"eliminarVersion(" + version.getId() + ");\"></span>\n";
                        }
                        lista += "                         </div>\n"
                                + "                     </div>\n"
                                + "                 </li>\n";
                    }
                }
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.CREAR_VERSION.getNombre())) {
                    lista += "                 <li class=\"list-group-item\">\n"
                            + "                     <div class=\"row\">\n"
                            + "                         <div class=\"col-xs-11 col-sm-11 col-md-11 col-lg-11\">\n"
                            + "                         </div>\n"
                            + "                         <div class=\"col-xs-1 col-sm-1 col-md-1 col-lg-1\">\n"
                            + "                             <span class=\"glyphicon glyphicon-plus\" onclick=\"nuevaVersion(" + proyecto.getId() + ", '" + sdf.format(proyecto.getFechaInicio()) + "');\"></span>\n"
                            + "                         </div>\n"
                            + "                     </div>\n"
                            + "                 </li>\n";
                }
                lista += "             </ul>\n"
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
