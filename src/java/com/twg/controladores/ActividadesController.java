package com.twg.controladores;

import com.twg.negocio.ActividadesNegocio;
import com.twg.negocio.ComentariosNegocio;
import com.twg.negocio.EstadosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.VersionesBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen sobre el módulo principal de Actividades como listar información,
 * filtrar o gestionar.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesController extends HttpServlet {

    private final ActividadesNegocio actividadesNegocio = new ActividadesNegocio();
    private final ProyectosNegocio proyectosNegocio = new ProyectosNegocio();
    private final VersionesNegocio versionesNegocio = new VersionesNegocio();
    private final EstadosNegocio estadosNegocio = new EstadosNegocio();
    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final ComentariosNegocio comentariosNegocio = new ComentariosNegocio();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final String gestionActividades = "jsp/gestionActividades.jsp";
    private final String consultaActividades = "jsp/actividades.jsp";

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Actividades
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String redireccion = consultaActividades;
        try {
            String mensajeAlerta = "";
            String mensajeExito = "";
            String mensajeError = "";

            String accion = request.getParameter("accion");
            String actividad = request.getParameter("id");
            if (accion == null) {
                accion = "";
            }

            String proyecto = request.getParameter("proyecto");
            String version = request.getParameter("version");
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String estado = request.getParameter("estado");
            String fecha = request.getParameter("fecha");
            String responsable = request.getParameter("responsable");
            String busqueda = request.getParameter("busqueda");
            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin = request.getParameter("fechaFin");
            String tiempo = request.getParameter("tiempo");
            String estimacion = request.getParameter("estimacion");

            Integer idActividad = null;
            try {
                idActividad = Integer.valueOf(actividad);
            } catch (NumberFormatException e) {
            }
            Integer idProyecto = null;
            try {
                idProyecto = Integer.valueOf(proyecto);
            } catch (NumberFormatException e) {
            }
            Integer idVersion = null;
            try {
                idVersion = Integer.valueOf(version);
            } catch (NumberFormatException e) {
            }
            Integer idResponsable;
            try {
                idResponsable = Integer.valueOf(responsable);
            } catch (NumberFormatException e) {
                idResponsable = null;
            }
            Integer idEstado;
            try {
                idEstado = Integer.valueOf(estado);
            } catch (NumberFormatException e) {
                idEstado = null;
            }
            List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.ACTIVIDADES);

            switch (accion) {
                case "consultar":
                    cargarTabla(response, permisosPagina, idProyecto, idVersion, descripcion, idEstado, fecha, idResponsable);
                    break;
                case "crearActividad":
                case "limpiarCreacion":
                    request.setAttribute("clientesActividad", null);
                    request.setAttribute("empleadosActividad", null);
                    redireccion = gestionActividades;
                    break;
                case "limpiarGestion":
                case "duplicarActividad":
                case "gestionarActividad":
                    if (idActividad != null) {
                        ActividadesBean actividadExistente = actividadesNegocio.consultarActividadPorId(idActividad);
                        if (actividadExistente != null) {
                            JSONArray personas = actividadesNegocio.consultarActividadesEmpleados(idActividad);
                            JSONArray empleados = new JSONArray();
                            JSONArray clientes = new JSONArray();
                            for (Object persona : personas) {
                                JSONObject personaJSON = (JSONObject) persona;
                                if (personaJSON.get("cargo") != null && personaJSON.get("cargo").toString().equalsIgnoreCase("cliente")) {
                                    clientes.add(persona);
                                } else {
                                    empleados.add(persona);
                                }
                            }
                            request.setAttribute("clientesActividad", clientes.isEmpty() ? "" : clientes);
                            request.setAttribute("empleadosActividad", empleados.isEmpty() ? "" : empleados);
                            request.setAttribute("nombre", actividadExistente.getNombre());
                            request.setAttribute("descripcion", actividadExistente.getDescripcion());
                            idProyecto = actividadExistente.getProyecto();
                            idVersion = actividadExistente.getVersion();
                            idEstado = actividadExistente.getEstado();
                            if (!accion.equals("duplicarActividad")) {
                                request.setAttribute("id", idActividad);
                                request.setAttribute("listaComentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, actividadExistente.getId()));
                            }
                        }
                    }
                    redireccion = gestionActividades;
                    break;
                case "guardar":
                    Map<String, Object> guardado = actividadesNegocio.guardarActividad(idActividad, idProyecto, idVersion, nombre, descripcion, idEstado);
                    if (guardado.get("mensajeError") != null) {
                        mensajeError = (String) guardado.get("mensajeError");
                    }
                    if (guardado.get("mensajeExito") != null) {
                        mensajeExito = (String) guardado.get("mensajeExito");
                    }
                    idActividad = (Integer) guardado.get("idActividad");
                    if (idActividad != null) {
                        JSONArray personas = actividadesNegocio.consultarActividadesEmpleados(idActividad);
                        JSONArray empleados = new JSONArray();
                        JSONArray clientes = new JSONArray();
                        for (Object persona : personas) {
                            JSONObject personaJSON = (JSONObject) persona;
                            if (personaJSON.get("cargo") != null && personaJSON.get("cargo").toString().equalsIgnoreCase("cliente")) {
                                clientes.add(persona);
                            } else {
                                empleados.add(persona);
                            }
                        }
                        request.setAttribute("clientesActividad", clientes.isEmpty() ? "" : clientes);
                        request.setAttribute("empleadosActividad", empleados.isEmpty() ? "" : empleados);
                        request.setAttribute("id", idActividad);
                        request.setAttribute("listaComentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, idActividad));
                    }
                    request.setAttribute("nombre", nombre);
                    request.setAttribute("descripcion", descripcion);
                    redireccion = gestionActividades;
                    break;
                case "guardarPersonaActividad":
                    mensajeError = actividadesNegocio.guardarActividadPersona(idActividad, idResponsable, fechaInicio, fechaFin, tiempo, estimacion != null && estimacion.equals("true"));
                    JSONObject resultado = new JSONObject();
                    if (mensajeError.isEmpty()) {
                        resultado.put("resultado", "ok");
                        JSONArray personas = actividadesNegocio.consultarActividadesEmpleados(idActividad);
                        JSONArray empleados = new JSONArray();
                        JSONArray clientes = new JSONArray();
                        for (Object persona : personas) {
                            JSONObject personaJSON = (JSONObject) persona;
                            if (personaJSON.get("cargo") != null && personaJSON.get("cargo").toString().equalsIgnoreCase("cliente")) {
                                clientes.add(persona);
                            } else {
                                empleados.add(persona);
                            }
                        }
                        resultado.put("clientesActividad", clientes);
                        resultado.put("empleadosActividad", empleados);
                    } else {
                        resultado.put("resultado", "falla");
                        resultado.put("mensaje", mensajeError);
                    }
                    response.getWriter().write(resultado.toJSONString());
                    break;
                case "eliminar":
                    mensajeError = actividadesNegocio.eliminarActividad(idActividad);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "La actividad ha sido eliminada con éxito";
                    }
                    break;
                case "consultarVersiones":
                    JSONArray array = new JSONArray();
                    List<VersionesBean> listaVersiones = versionesNegocio.consultarVersiones(null, idProyecto, null, false);
                    if (listaVersiones != null && !listaVersiones.isEmpty()) {
                        for (VersionesBean versionBean : listaVersiones) {
                            JSONObject object = new JSONObject();
                            object.put("id", versionBean.getId());
                            object.put("nombre", versionBean.getNombre());
                            object.put("fechaInicio", versionBean.getFechaInicio() != null ? sdf.format(versionBean.getFechaInicio()) : "");
                            object.put("fechaTerminacion", versionBean.getFechaTerminacion() != null ? sdf.format(versionBean.getFechaTerminacion()) : "");
                            array.add(object);
                        }
                    }
                    response.getWriter().write(array.toJSONString());
                    break;
                case "consultarPersonasProyecto":
                    array = personasNegocio.completarPersonas(busqueda, idProyecto);
                    response.getWriter().write(array.toJSONString());
                    break;
                case "consultarFechasActividades":
                    Date dateFechaEstimadaInicial = sdf.parse(fechaInicio);
                    Date dateFechaEstimadaFin = sdf.parse(fechaFin);
                    JSONArray arrayPersonasAsignadasActividad = actividadesNegocio.consultarActividadesAsociadas(idActividad, idResponsable, dateFechaEstimadaInicial, dateFechaEstimadaFin);
                    response.getWriter().write(arrayPersonasAsignadasActividad.toString());
                    break;
                case "guardarComentario":
                    String comentario = request.getParameter("comentario");
                    Integer persona;
                    try {
                        persona = (Integer) request.getSession(false).getAttribute("personaSesion");
                    } catch (Exception e) {
                        persona = null;
                    }
                    JSONObject comentarioGuardado = new JSONObject();
                    mensajeAlerta = comentariosNegocio.validarDatos(comentario);
                    if (mensajeAlerta.isEmpty()) {
                        mensajeError = comentariosNegocio.guardarComentario(null, persona, comentario, comentariosNegocio.TIPO_ACTIVIDAD, idActividad);
                        if (mensajeError.isEmpty()) {
                            comentarioGuardado.put("comentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, idActividad));
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
                    mensajeError = comentariosNegocio.eliminarComentario(idComentario);
                    if (mensajeError.isEmpty()) {
                        comentarioEliminado.put("comentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, idActividad));
                    } else {
                        comentarioEliminado.put("mensajeError", mensajeError);
                    }
                    response.getWriter().write(comentarioEliminado.toJSONString());
                    break;
                case "completarPersonas":
                    array = personasNegocio.completarPersonas(busqueda);
                    response.getWriter().write(array.toJSONString());
                    break;
                default:
                    break;
            }
            if (accion.isEmpty() || accion.equals("limpiar") || accion.equals("crearActividad") || accion.equals("limpiarCreacion") || accion.equals("gestionarActividad")
                    || accion.equals("limpiarGestion") || accion.equals("duplicarActividad") || accion.equals("guardar") || accion.equals("eliminar")) {
                request.setAttribute("mensajeAlerta", mensajeAlerta);
                request.setAttribute("mensajeExito", mensajeExito);
                request.setAttribute("mensajeError", mensajeError);
                request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
                request.setAttribute("estados", estadosNegocio.consultarEstados(null, "ACTIVIDADES", null, null, null, null, null));
                if (permisosPagina != null && !permisosPagina.isEmpty()) {
                    if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                        request.setAttribute("opcionConsultar", "T");
                    }
                    if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                        request.setAttribute("opcionGuardar", "T");
                    }
                }
                if (idProyecto != null) {
                    request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, idProyecto, null, false));
                }
                if (idResponsable != null) {
                    PersonasBean persona = personasNegocio.consultarPersona(idResponsable, null, null);
                    if (persona != null) {
                        request.setAttribute("nombreResponsable", persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
                    }
                }
                if (!accion.equals("crearActividad") && !accion.equals("limpiar") && !accion.equals("limpiarCreacion")) {
                    request.setAttribute("proyecto", idProyecto);
                    request.setAttribute("version", idVersion);
                    request.setAttribute("estado", idEstado);
                }
                request.setAttribute("responsable", idResponsable);
                request.getRequestDispatcher(redireccion).forward(request, response);
            }
        } catch (ParseException ex) {
            Logger.getLogger(ActividadesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre las Actividades.
     *
     * @param response
     * @param permisos
     * @param id
     * @param idProyecto
     * @param idVersion
     * @param contiene
     * @param idEstado
     * @param fecha
     * @param idResponsable
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer idProyecto, Integer idVersion, String contiene, Integer idEstado, String fecha, Integer idResponsable) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<ActividadesBean> listaActividades = actividadesNegocio.consultarActividades(null, idProyecto, idVersion, contiene, fecha, idEstado, idResponsable);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Proyecto</th>");
        out.println("<th>Versión</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Inicio estimado</th>");
        out.println("<th>Terminacion estimada</th>");
        out.println("<th>Duración estimada (h)</th>");
        out.println("<th>Estado</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaActividades != null && !listaActividades.isEmpty()) {
            for (ActividadesBean actividad : listaActividades) {
                out.println("<input type=\"hidden\" id=\"id\" name=\"id\" value=" + actividad.getId() + " />");
                out.println("<tr>");
                out.println("<td>" + actividad.getNombreProyecto() + "</td>");
                out.println("<td>" + actividad.getNombreVersion() + "</td>");
                out.println("<td>" + actividad.getNombre() + "</td>");
                out.println("<td>" + (actividad.getFechaInicio() != null ? sdf.format(actividad.getFechaInicio()) : "") + "</td>");
                out.println("<td>" + (actividad.getFechaFin() != null ? sdf.format(actividad.getFechaFin()) : "") + "</td>");
                out.println("<td>" + (actividad.getTiempo() != null ? actividad.getTiempo() : "") + "</td>");
                out.println("<td>" + actividad.getNombreEstado() + "</td>");
                out.println("<td>");
                out.println("<div class=\"btn-group\" role=\"group\">");
                out.println("   <button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">");
                out.println("       <span class=\"caret\"></span>");
                out.println("   </button>");
                out.println("   <ul class=\"dropdown-menu dropdown-menu-right\">");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.GUARDAR.getNombre())) {
                    out.println("   <li><a onclick=\"jQuery('#id').val('" + actividad.getId() + "'); jQuery('#gestionarActividad').click();\">Gestionar</a></li>");
                    out.println("   <li><a onclick=\"jQuery('#id').val('" + actividad.getId() + "'); jQuery('#duplicarActividad').click();\">Duplicar</a></li>");
                }
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.GUARDAR.getNombre())) {
                    out.println("   <button hidden type=\"button\" >Eliminar</button>");
                    out.println("   <li><a data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + actividad.getId() + "');\">Eliminar</a></li>");
                }
                out.println("   </ul>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr>");
            out.println("<td colspan=\"8\">No se encontraron registros</td>");
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
