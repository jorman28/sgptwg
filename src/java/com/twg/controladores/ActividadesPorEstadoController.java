package com.twg.controladores;

import com.twg.negocio.ActividadesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.VersionesBean;
import com.twg.reportes.GeneradorReportes;
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
 * Este controlador es el encargado de gestionar todas las peticiones
 * relacionadas con la consulta, visualización y/o exportación de las
 * actividades que se encuentran en los distintos estados.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesPorEstadoController extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        switch (accion) {
            case "generarReporte":
                JSONObject reporteObject = new JSONObject();
                String nombreArchivo = generadorReportes.actividadesPorEstado(proyecto, version, persona);
                if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                    reporteObject.put("archivo", nombreArchivo);
                }
                response.getWriter().write(reporteObject.toJSONString());
                break;
            case "obtenerArchivo":
                obtenerArchivo(response, archivo);
                break;
            case "consultar":
                JSONObject estadosObject = actividadesPorEstado(proyecto, version, persona);
                response.getWriter().write(estadosObject.toString());
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
            request.setAttribute("proyectos", proyectosNegocio.consultarProyectos(null, null, false));
            request.getRequestDispatcher("jsp/actividadesPorEstado.jsp").forward(request, response);
        }
    }

    /**
     * Método encargado de pintar los datos de la tabla en pantalla
     *
     * @param response
     * @param proyecto
     * @param version
     * @param persona
     * @throws ServletException
     * @throws IOException
     */
    private JSONObject actividadesPorEstado(Integer proyecto, Integer version, Integer persona) throws ServletException, IOException {
        JSONObject resultado = new JSONObject();
        JSONArray arrayEstados = new JSONArray();
        JSONArray titulos = new JSONArray();
        titulos.add("Estado");
        titulos.add("Actividades");
        arrayEstados.add(titulos);

        List<Map<String, Object>> actividadesPorEstado = actividadesNegocio.listarActividadesPorEstado(proyecto, version, persona);
        StringBuilder html = new StringBuilder();
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
                html.append("<td align=\"right\">").append(estado.get("actividades")).append("</td>");
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
        resultado.put("html", html.toString());
        resultado.put("estados", arrayEstados);
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
