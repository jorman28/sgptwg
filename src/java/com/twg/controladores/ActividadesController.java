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
import java.util.ArrayList;
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

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static final String INSERTAR_O_EDITAR = "jsp/gestionActividades.jsp";
    private static final String LISTAR_ACTIVIDADES = "jsp/actividades.jsp";

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
        String redireccion = LISTAR_ACTIVIDADES;
        try {
            String mensajeAlerta = "";
            String mensajeExito = "";
            String mensajeError = "";

            String accion = request.getParameter("accion");
            String idStr = request.getParameter("id"); //consulta y creacion Er
            if (accion == null) {
                accion = "";
            } else if (accion.contains("_")) { //esto se hace por tema de modificación, para poder extraer el id del registro
                String[] arrayGetId = accion.split("_");
                if (arrayGetId[1] != null) {
                    idStr = arrayGetId[1];
                    accion = arrayGetId[0];
                }
            }

            String[] idPersonasActividad = request.getParameterValues("idPersonas");// Personas que estan en la lista 2 Participantes en la actividad
            String proyectoStr = request.getParameter("proyecto"); //consulta y creacion Er
            String versionStr = request.getParameter("version"); //consulta y creacion Er
            String descripcion = request.getParameter("descripcion"); //consulta y creacion Er
            String estadoStr = request.getParameter("estado"); //consulta y creacion Er
            String fechaStr = request.getParameter("fecha"); //consulta Er
            String responsable = request.getParameter("responsable"); //consulta y creacion Er en creación se recibe el objeto hidden

            String fecha_estimada_inicioStr = request.getParameter("fecha_estimada_inicio");//creacion
            String fecha_estimada_terminacionStr = request.getParameter("fecha_estimada_terminacion");//creacion
            String fecha_real_inicioStr = request.getParameter("fecha_real_inicio");//creacion
            String fecha_real_terminacionStr = request.getParameter("fecha_real_terminacion");//creacion
            String tiempo_estimadoStr = request.getParameter("tiempo_estimado");//creacion
            String tiempo_invertidoStr = request.getParameter("tiempo_invertido");//creacion
            String busqueda = request.getParameter("busqueda");

            Integer id = null;
            try {
                id = Integer.valueOf(idStr);
            } catch (NumberFormatException e) {
            }

            Integer proyecto = null;
            try {
                proyecto = Integer.valueOf(proyectoStr);
            } catch (NumberFormatException e) {
            }

            Integer version = null;
            try {
                version = Integer.valueOf(versionStr);
            } catch (NumberFormatException e) {
            }

            Integer filtroPersona;
            try {
                filtroPersona = Integer.valueOf(responsable);
            } catch (NumberFormatException e) {
                filtroPersona = null;
            }

            List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.ACTIVIDADES);

            switch (accion) {
                case "consultar":
                    cargarTabla(response, permisosPagina, proyecto, version, descripcion, estadoStr, fechaStr, filtroPersona);
                    break;
                case "crearActividad":
                case "limpiarCreacion":
                    request.setAttribute("estados", estadosNegocio.consultarEstados(null, "ACTIVIDADES", null, null, null, null));
                    request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
                    request.setAttribute("clientesActividad", null);
                    request.setAttribute("empleadosActividad", null);
                    redireccion = INSERTAR_O_EDITAR;
                    break;
                case "gestionarActividad":
                case "limpiarGestion":
                    if (idStr != null && !idStr.isEmpty()) {
                        ActividadesBean actividad = actividadesNegocio.consultarActividadI(id);
                        request.setAttribute("estados", estadosNegocio.consultarEstados(null, "ACTIVIDADES", null, null, null, null));
                        request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
                        request.setAttribute("clientesActividad", null);
                        request.setAttribute("empleadosActividad", null);
                        proyecto = proyectosNegocio.consultarProyectoPorVersion(actividad.getVersion()).getId();
                        List<PersonasBean> personas = personasNegocio.consultarPersonasActividad(idStr);
                        List<PersonasBean> empleados = new ArrayList<>();
                        List<PersonasBean> clientes = new ArrayList<>();
                        for (PersonasBean persona : personas) {
                            if (persona.getNombreCargo().toLowerCase().equalsIgnoreCase("cliente")) {
                                clientes.add(persona);
                            } else {
                                empleados.add(persona);
                            }
                        }
                        request.setAttribute("clientesActividad", clientes);
                        request.setAttribute("empleadosActividad", empleados);
                        request.setAttribute("id", actividad.getId().toString());
                        request.setAttribute("proyecto", proyecto.toString());
                        request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, proyecto, null, false));
                        version = actividad.getVersion();
                        request.setAttribute("version", version.toString());
                        request.setAttribute("descripcion", actividad.getDescripcion());
                        estadoStr = actividad.getEstado().toString();
                        request.setAttribute("estado", Integer.parseInt(estadoStr));
                        request.setAttribute("listaComentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, actividad.getId()));
                    }
                    redireccion = INSERTAR_O_EDITAR;
                    break;
                case "duplicarActividad":
                    if (idStr != null && !idStr.isEmpty()) {
                        ActividadesBean actividad = actividadesNegocio.consultarActividadI(id);
                        request.setAttribute("estados", estadosNegocio.consultarEstados(null, "ACTIVIDADES", null, null, null, null));
                        request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
                        request.setAttribute("clientesActividad", null);
                        request.setAttribute("empleadosActividad", null);
                        proyecto = proyectosNegocio.consultarProyectoPorVersion(actividad.getVersion()).getId();
                        List<PersonasBean> personas = personasNegocio.consultarPersonasActividad(idStr);
                        List<PersonasBean> empleados = new ArrayList<>();
                        List<PersonasBean> clientes = new ArrayList<>();
                        for (PersonasBean persona : personas) {
                            if (persona.getNombreCargo().toLowerCase().equalsIgnoreCase("cliente")) {
                                clientes.add(persona);
                            } else {
                                empleados.add(persona);
                            }
                        }
                        request.setAttribute("clientesActividad", clientes);
                        request.setAttribute("empleadosActividad", empleados);
                        request.setAttribute("proyecto", proyecto.toString());
                        request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, proyecto, null, false));
                        version = actividad.getVersion();
                        request.setAttribute("version", version.toString());
                        request.setAttribute("descripcion", actividad.getDescripcion());
                        estadoStr = actividad.getEstado().toString();
                        request.setAttribute("estado", Integer.parseInt(estadoStr));
                    }
                    redireccion = INSERTAR_O_EDITAR;
                    break;
                case "guardar":
                    Map<String, Object> result = actividadesNegocio.guardarActividad(idStr, proyectoStr, versionStr, idPersonasActividad, descripcion, fecha_estimada_inicioStr, fecha_estimada_terminacionStr, fecha_real_inicioStr, fecha_real_terminacionStr, tiempo_estimadoStr, tiempo_invertidoStr, estadoStr);
                    if (result.get("mensajeError") != null) {
                        mensajeError = (String) result.get("mensajeError");
                        request.setAttribute("mensajeError", mensajeError);
                        request.setAttribute("estados", estadosNegocio.consultarEstados(null, "ACTIVIDADES", null, null, null, null));
                        request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
                        request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, Integer.parseInt(proyectoStr), null, false));
                        if (idPersonasActividad != null) {
                            List<PersonasBean> personas = new ArrayList<>();
                            for (String idPersona : idPersonasActividad) {
                                personas.add(personasNegocio.consultarPersona(idPersona, null, null));
                            }
                            List<PersonasBean> empleados = new ArrayList<>();
                            List<PersonasBean> clientes = new ArrayList<>();

                            for (PersonasBean persona : personas) {
                                if (persona.getNombreCargo().toLowerCase().equalsIgnoreCase("cliente")) {
                                    clientes.add(persona);
                                } else {
                                    empleados.add(persona);
                                }
                            }
                            request.setAttribute("clientesActividad", clientes);
                            request.setAttribute("empleadosActividad", empleados);
                        } else {
                            request.setAttribute("clientesActividad", null);
                            request.setAttribute("empleadosActividad", null);
                        }
                        enviarDatosCreacionEdicion(request, idStr, proyectoStr, versionStr, descripcion, fecha_estimada_inicioStr, fecha_estimada_terminacionStr, fecha_real_inicioStr, fecha_real_terminacionStr, tiempo_estimadoStr, tiempo_invertidoStr, estadoStr);
                        accion = "gestionarActividad";
                        redireccion = INSERTAR_O_EDITAR;
                        break;
                    }
                    if (result.get("mensajeExito") != null) {
                        mensajeExito = (String) result.get("mensajeExito");
                        enviarDatosCreacionEdicion(request, null, null, null, null, null, null, null, null, null, null, null);
                    }
                    break;
                case "eliminar":
                    mensajeError = actividadesNegocio.eliminarActividad(id);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "La actividad ha sido eliminada con éxito";
                    }
                    break;
                case "consultarVersiones":
                    JSONArray array = new JSONArray();
                    List<VersionesBean> listaVersiones = versionesNegocio.consultarVersiones(null, proyecto, null, false);
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
                    response.getWriter().write(array.toString());
                    break;
                case "consultarPersonasProyecto":
                    String strParticipante = request.getParameter("search");
                    String strProyecto = request.getParameter("search1");
                    JSONArray arrayPersonas = personasNegocio.consultarPersonasProyecto(strProyecto, strParticipante);
                    response.getWriter().write(arrayPersonas.toString());
                    break;
                case "consultarFechasActividades":
                    String strParticipantes = request.getParameter("empleadosSeleccionados");
                    String strFechaEstimadaInicial = request.getParameter("strFechaEstimadaInicial");
                    String strFechaEstimadaFin = request.getParameter("strFechaEstimadaFin");
                    String strIdActividad = request.getParameter("strIdActividad");
                    java.util.Date dateFechaEstimadaInicial = sdf.parse(strFechaEstimadaInicial);
                    java.util.Date dateFechaEstimadaFin = sdf.parse(strFechaEstimadaFin);
                    JSONArray arrayPersonasAsignadasActividad = personasNegocio.consultarPersonasAsignadasActividad(strParticipantes, dateFechaEstimadaInicial, dateFechaEstimadaFin, strIdActividad);
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
                        mensajeError = comentariosNegocio.guardarComentario(null, persona, comentario, comentariosNegocio.TIPO_ACTIVIDAD, id);
                        if (mensajeError.isEmpty()) {
                            comentarioGuardado.put("comentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, id));
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
                        comentarioEliminado.put("comentarios", comentariosNegocio.listaComentarios(comentariosNegocio.TIPO_ACTIVIDAD, id));
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
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
            request.setAttribute("estados", estadosNegocio.consultarEstados(null, "ACTIVIDADES", null, null, null, null));
            if (accion.isEmpty() || accion.equals("limpiar") || accion.equals("crearActividad") || accion.equals("limpiarCreacion") || accion.equals("gestionarActividad")
                    || accion.equals("limpiarGestion") || accion.equals("duplicarActividad") || accion.equals("guardar") || accion.equals("eliminar")) {
                if (permisosPagina != null && !permisosPagina.isEmpty()) {
                    if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                        request.setAttribute("opcionConsultar", "T");
                    }
                    if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                        request.setAttribute("opcionGuardar", "T");
                    }
                }

                /* Filtros en pantalla de actividades */
                if (proyecto != null) {
                    request.setAttribute("versiones", versionesNegocio.consultarVersiones(null, proyecto, null, false));
                }
                if (filtroPersona != null) {
                    PersonasBean persona = personasNegocio.consultarPersona(filtroPersona.toString(), null, null);
                    if (persona != null) {
                        request.setAttribute("nombreResponsable", persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
                    }
                }
                if (!accion.equals("crearActividad") && !accion.equals("limpiarCreacion")) {
                    request.setAttribute("proyecto", proyecto);
                    request.setAttribute("version", version);
                    if (estadoStr != null && !"".equals(estadoStr)) {
                        request.setAttribute("estado", Integer.parseInt(estadoStr));
                    } else {
                        request.setAttribute("estado", request.getParameter("estado"));
                    }
                }
                request.setAttribute("responsable", filtroPersona);
                request.getRequestDispatcher(redireccion).forward(request, response);
            }
        } catch (ParseException ex) {
            Logger.getLogger(ActividadesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método se encarga de enviar los atributos de las Actividades, al
     * cliente que realiza la petición.
     *
     * @param request
     * @param id
     * @param proyecto
     * @param version
     * @param descripcion
     * @param fecha_estimada_inicio
     * @param fecha_estimada_terminacion
     * @param fecha_real_inicio
     * @param fecha_real_terminacion
     * @param tiempo_estimado
     * @param tiempo_invertido
     * @param estado
     */
    private void enviarDatosCreacionEdicion(HttpServletRequest request, String id, String proyecto, String version, String descripcion, String fecha_estimada_inicio, String fecha_estimada_terminacion, String fecha_real_inicio, String fecha_real_terminacion, String tiempo_estimado, String tiempo_invertido, String estado) {
        request.setAttribute("id", id);
        request.setAttribute("proyecto", proyecto);
        request.setAttribute("version", version);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("fecha_estimada_inicio", fecha_estimada_inicio);
        request.setAttribute("fecha_estimada_terminacion", fecha_estimada_terminacion);
        request.setAttribute("fecha_real_inicio", fecha_real_inicio);
        request.setAttribute("fecha_real_terminacion", fecha_real_terminacion);
        request.setAttribute("tiempo_estimado", tiempo_estimado);
        request.setAttribute("tiempo_invertido", tiempo_invertido);
        request.setAttribute("estado", estado);
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre las Actividades.
     *
     * @param response
     * @param permisos
     * @param id
     * @param proyecto
     * @param version
     * @param descripcion
     * @param estado
     * @param fecha
     * @param responsable
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer proyecto, Integer version, String descripcion, String estado, String fecha, Integer responsable) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<ActividadesBean> listaActividades = actividadesNegocio.consultarActividades(proyecto, version, descripcion, fecha, estado, responsable);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Descripción</th>");
        out.println("<th>Versión</th>");
        out.println("<th>Fecha estimada inicio</th>");
        out.println("<th>Fecha estimada fin</th>");
        out.println("<th>Tiempo estimado (h)</th>");
        out.println("<th>Estado</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaActividades != null && !listaActividades.isEmpty()) {
            for (ActividadesBean actividad : listaActividades) {
                out.println("<input type=\"hidden\" id=\"id\" name=\"id\" value=" + actividad.getId() + " />");
                out.println("<tr>");
                out.println("<td>" + actividad.getDescripcion() + "</td>");
                out.println("<td>" + actividad.getNombreVersion() + "</td>");
                out.println("<td>" + actividad.getNombreEstado() + "</td>");
                out.println("<td>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.GUARDAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"submit\" name=\"accion\" id=\"gestionarActividad\" value='gestionarActividad_" + actividad.getId() + "'> Gestionar</button>");
                    out.println("<button class=\"btn btn-default\" type=\"submit\" name=\"accion\" id=\"duplicarActividad\" value='duplicarActividad_" + actividad.getId() + "'> Duplicar</button>");
                }
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + actividad.getId() + "');\">Eliminar</button>");
                }
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr>");
            out.println("<td colspan=\"7\">No se encontraron registros</td>");
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
