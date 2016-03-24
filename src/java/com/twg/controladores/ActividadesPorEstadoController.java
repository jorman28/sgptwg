package com.twg.controladores;

import com.twg.negocio.ActividadesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import com.twg.persistencia.beans.VersionesBean;
import com.twg.reportes.GeneradorReportes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
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
 * @author Andres Giraldo
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
        String tipoReporte = request.getParameter("tipoReporte");
        String busqueda = request.getParameter("busqueda");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        switch (accion) {
            case "generarReporte":
                generadorReportes.actividadesPorEstado(proyecto, version, persona, tipoReporte);
                JSONObject result = new JSONObject();
                result.put("response", "OK");
                result.writeJSONString(response.getWriter());
                break;
            case "consultar":
                actividadesPorEstado(response, proyecto, version, persona);
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
    private void actividadesPorEstado(HttpServletResponse response, Integer proyecto, Integer version, Integer persona) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<Map<String, Object>> actividadesPorEstado = actividadesNegocio.listarActividadesPorEstado(proyecto, version, persona);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Estado</th>");
        out.println("<th>Actividades</th>");
        out.println("<th>Porcentaje</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (actividadesPorEstado != null && !actividadesPorEstado.isEmpty()) {
            for (Map<String, Object> estado : actividadesPorEstado) {
                out.println("<tr>");
                out.println("<td>" + estado.get("estado") + "</td>");
                out.println("<td align=\"right\">" + estado.get("actividades") + "</td>");
                out.println("<td align=\"right\">" + estado.get("porcentaje") + "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr>");
            out.println("<td colspan=\"3\">No se encontraron registros</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
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
