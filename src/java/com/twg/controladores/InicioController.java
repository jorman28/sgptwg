package com.twg.controladores;

import com.twg.negocio.ActividadesNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.beans.VersionesBean;
import com.twg.utilidades.GeneradorReportes;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen al ingresar al sistema para cargar la página principal.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class InicioController extends HttpServlet {

    private final GeneradorReportes generadorReportes = new GeneradorReportes();
    private final ActividadesNegocio actividadesNegocio = new ActividadesNegocio();
    private final VersionesNegocio versionesNegocio = new VersionesNegocio();
    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final ProyectosNegocio proyectosNegocio = new ProyectosNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Actividades por estado
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Integer proyecto;
        try {
            proyecto = Integer.valueOf(request.getParameter("proyecto"));
        } catch (NumberFormatException e) {
            proyecto = null;
        }
        Integer version;
        try {
            version = Integer.valueOf(request.getParameter("version"));
        } catch (NumberFormatException e) {
            version = null;
        }
        Integer persona;
        try {
            persona = Integer.valueOf(request.getParameter("persona"));
        } catch (NumberFormatException e) {
            persona = null;
        }
        String archivo = request.getParameter("archivo");
        String busqueda = request.getParameter("busqueda");
        String tipoReporte = request.getParameter("tipoReporte");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.ACTIVIDADES);

        Integer personaSesion;
        try {
            personaSesion = (Integer) request.getSession(false).getAttribute("personaSesion");
        } catch (Exception e) {
            personaSesion = null;
        }

        switch (accion) {
            case "generarReporte":
                JSONObject reporteObject = new JSONObject();
                String nombreArchivo;
                if (tipoReporte != null && tipoReporte.equals("estados")) {
                    nombreArchivo = generadorReportes.actividadesPorEstado(proyecto, version, persona);
                } else {
                    nombreArchivo = generadorReportes.consolidadoActividades(proyecto, version, persona);
                }
                if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                    reporteObject.put("archivo", nombreArchivo);
                }
                response.getWriter().write(reporteObject.toJSONString());
                break;
            case "obtenerArchivo":
                obtenerArchivo(response, archivo);
                break;
            case "consultar":
                JSONObject resultado = graficasPantallaInicio(request.getContextPath(), proyecto, version, persona);
                response.getWriter().write(resultado.toString());
                break;
            case "consultarVersiones":
                JSONArray array = new JSONArray();
                List<VersionesBean> listaVersiones = versionesNegocio.consultarVersiones(null, proyecto, null, false);
                if (listaVersiones != null && !listaVersiones.isEmpty()) {
                    for (VersionesBean versionBean : listaVersiones) {
                        JSONObject object = new JSONObject();
                        object.put("id", versionBean.getId());
                        object.put("nombre", versionBean.getNombre());
                        array.add(object);
                    }
                }
                response.getWriter().write(array.toString());
                break;
            case "completarPersonas":
                array = personasNegocio.completarPersonas(busqueda);
                response.getWriter().write(array.toJSONString());
                break;
            default:
                break;
        }
        if (accion.equals("")) {
            Integer idPersona = null;
            if (permisosPagina != null && permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                idPersona = personaSesion;
            }
            request.setAttribute("opcionConsultar", permisosPagina != null && permisosPagina.contains(Permisos.CONSULTAR.getNombre()));
            request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false, idPersona));
            request.getRequestDispatcher("jsp/inicio.jsp").forward(request, response);
        }
    }

    /**
     * Método encargado de pintar los datos de la tabla en pantalla
     *
     * @param contexto
     * @param proyecto
     * @param version
     * @param persona
     * @throws ServletException
     * @throws IOException
     */
    private JSONObject graficasPantallaInicio(String contexto, Integer proyecto, Integer version, Integer persona) throws ServletException, IOException {
        JSONObject resultado = new JSONObject();
        JSONArray arrayEstados = new JSONArray();
        JSONArray titulos = new JSONArray();
        titulos.add("Estado");
        titulos.add("Actividades");
        arrayEstados.add(titulos);

        List<Map<String, Object>> actividadesPorEstado = actividadesNegocio.listarActividadesPorEstado(proyecto, version, persona);
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"table-responsive\">");
        html.append("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>Estado</th>");
        html.append("<th>Actividades</th>");
        html.append("<th>Porcentaje</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");
        if (actividadesPorEstado != null && !actividadesPorEstado.isEmpty()) {
            for (Map<String, Object> estado : actividadesPorEstado) {
                html.append("<tr>");
                html.append("<td>").append(estado.get("estado")).append("</td>");
                html.append("<td align=\"right\">");
                html.append("<a href=\"")
                        .append(contexto)
                        .append("/ActividadesController?proyecto=").append(proyecto != null ? proyecto : "")
                        .append("&version=").append(version != null ? version : "")
                        .append("&estado=").append(estado.get("id_estado") != null ? estado.get("id_estado") : "")
                        .append("&responsable=").append(persona != null ? persona : "")
                        .append("\">");
                html.append(estado.get("actividades"));
                html.append("</a>");
                html.append("</td>");
                html.append("<td align=\"right\">").append(estado.get("porcentaje")).append("</td>");
                html.append("</tr>");
                JSONArray arrayEstado = new JSONArray();
                arrayEstado.add(estado.get("estado"));
                arrayEstado.add(estado.get("actividades"));
                arrayEstados.add(arrayEstado);
            }
        } else {
            html.append("<tr>");
            html.append("<td colspan=\"3\">No se encontraron registros</td>");
            html.append("</tr>");
        }
        html.append("</tbody>");
        html.append("</table>");
        html.append("</div>");
        resultado.put("html", html.toString());
        resultado.put("estados", arrayEstados);
        List<ActividadesBean> listaActividades = actividadesNegocio.consolidadoActividades(proyecto, version, persona);
        JSONArray avanceActividades = new JSONArray();
        JSONArray idElementos = new JSONArray();
        if (listaActividades != null && !listaActividades.isEmpty()) {
            JSONArray avance = new JSONArray();
            if (proyecto != null && proyecto != 0) {
                avance.add("Versión");
            } else {
                avance.add("Proyecto");
            }
            avance.add("Estimado");
            avance.add("Invertido");
            avanceActividades.add(avance);
            for (ActividadesBean actividad : listaActividades) {
                avance = new JSONArray();
                if (proyecto != null && proyecto != 0) {
                    avance.add(actividad.getNombreVersion());
                } else {
                    avance.add(actividad.getNombreProyecto());
                }
                avance.add(actividad.getTiempoEstimado());
                avance.add(actividad.getTiempoInvertido());
                if (proyecto != null && proyecto != 0) {
                    idElementos.add(actividad.getVersion());
                } else {
                    idElementos.add(actividad.getProyecto());
                }
                avanceActividades.add(avance);
            }
        }
        resultado.put("avance", avanceActividades);
        resultado.put("idElementos", idElementos);
        return resultado;
    }

    /**
     * Método encargado de descargar el archivo generado para el reporte de
     * actividades por estado
     *
     * @param response
     * @param nombreArchivo
     * @throws IOException
     */
    private void obtenerArchivo(HttpServletResponse response, String nombreArchivo) throws IOException {
        ServletOutputStream respuesta = response.getOutputStream();
        String mimetype = "application/x-download";
        FileInputStream archivo;
        archivo = new FileInputStream(generadorReportes.rutaReportes + nombreArchivo);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = archivo.read(buffer)) > 0) {
            respuesta.write(buffer, 0, length);
        }
        response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);
        response.setHeader("Content-Length", Integer.toString(length));
        response.setContentType(mimetype);
        archivo.close();
        respuesta.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
