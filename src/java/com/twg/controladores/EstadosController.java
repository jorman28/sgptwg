package com.twg.controladores;

import com.twg.negocio.EstadosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
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
 * Esta clase define métodos para controlar las peticiones y respuestas 
 * que se hacen sobre el módulo principal de Estados, así como guardar, consultar,
 * modificar o eliminar la información.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class EstadosController extends HttpServlet {

    private final EstadosNegocio estadosNegocio = new EstadosNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Estados
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

        String idStr = request.getParameter("id");
        String tipoEstado = request.getParameter("tipoEstado");
        String nombre = request.getParameter("nombre");
        String estadoPrevStr = request.getParameter("estadoPrev");
        String estadoSigStr = request.getParameter("estadoSig");
        String eFinal = request.getParameter("eFinal");
        
        String busquedaAsincronica = request.getParameter("busquedaAsincronica");//variable que me permite realizar la busqueda de los estados desde una funcion ajax

        Integer id = null;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
        }
        
        Integer estadoPrev = null;
        try {
            estadoPrev = Integer.valueOf(estadoPrevStr);
        } catch (NumberFormatException e) {
        }
        
        Integer estadoSig = null;
        try {
            estadoSig = Integer.valueOf(estadoSigStr);
        } catch (NumberFormatException e) {
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.ESTADOS);

        String personaSesion = "";
        try {
            personaSesion = String.valueOf(request.getSession().getAttribute("personaSesion"));
        } catch (Exception e) {
            System.err.print("Error obteniendo la persona en sesion");
        }
        
        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal);
                break;
            case "editar":
                JSONObject obj = estadosNegocio.consultarEstado(id);
                response.getWriter().write(obj.toString());
                
                break;
            case "guardar":
                Map<String, Object> result = estadosNegocio.crearEstado(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal, personaSesion);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    enviarDatos(request, id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal);
                }
                if (result.get("mensajeExito") != null) {
                    mensajeExito = (String) result.get("mensajeExito");
                    enviarDatos(request, null, null, null, null, null, null);
                }
                break;
            case "eliminar":
                result = estadosNegocio.eliminarEstado(id, personaSesion);
                if (result.get("mensajeError") != null) {
                    mensajeError = (String) result.get("mensajeError");
                    enviarDatos(request, null, null, null, null, null, null);
                }
                if (result.get("mensajeExito") != null) {
                    mensajeExito = (String) result.get("mensajeExito");
                    enviarDatos(request, null, null, null, null, null, null);
                }
                break;
            case "ConsultarEstados":
                JSONArray array = new JSONArray();
                List<EstadosBean> listaEstados = estadosNegocio.consultarEstados(null, tipoEstado, null, null, null, null);
                if (listaEstados != null && !listaEstados.isEmpty()) {
                    for (EstadosBean estadoBean : listaEstados) {
                        JSONObject object = new JSONObject();
                        object.put("id", estadoBean.getId());
                        object.put("nombre", estadoBean.getNombre());
                        array.add(object);
                    }
                }
                response.getWriter().write(array.toString());
                break;
            default:
                enviarDatos(request, null, null, null, null, null, null);
                break;
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        //request.setAttribute("estadosPrev", estadosNegocio.consultarEstados(null, null, null, null, null, null));
        //request.setAttribute("estadosSig", estadosNegocio.consultarEstados(null, null, null, null, null, null));
        if (!accion.equals("consultar") && !accion.equals("editar") && busquedaAsincronica == null){
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                    request.setAttribute("opcionConsultar", "T");
                }
                if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                    request.setAttribute("opcionGuardar", "T");
                }
            }
            request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
        }
    }

    /**
     * Este método se encarga de enviar los atributos del Estado, al cliente que
     * realiza la petición.
     * 
     * @param request
     * @param id
     * @param tipoEstado
     * @param nombre
     * @param estadoPrev
     * @param estadoSig
     * @param eFinal 
     */
    private void enviarDatos(HttpServletRequest request, Integer id, String tipoEstado, String nombre, Integer estadoPrev,
            Integer estadoSig, String eFinal) {
        request.setAttribute("id", id);
        request.setAttribute("tipoEstado", tipoEstado);
        request.setAttribute("nombre", nombre);
        request.setAttribute("estadoPrev", estadoPrev);
        request.setAttribute("estadoSig", estadoSig);
        request.setAttribute("eFinal", eFinal);
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros 
     * que hay sobre los Estados
     * 
     * @param response
     * @param permisos
     * @param id
     * @param tipoEstado
     * @param nombre
     * @param estadoPrev
     * @param estadoSig
     * @param eFinal
     * @throws ServletException
     * @throws IOException 
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer id, String tipoEstado, String nombre, 
            Integer estadoPrev, Integer estadoSig, String eFinal) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");

        List<EstadosBean> listaEstados = estadosNegocio.consultarEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Tipo Estado</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Previo</th>");
        out.println("<th>Siguiente</th>");
        out.println("<th>Final</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaEstados != null && !listaEstados.isEmpty()) {
            for (EstadosBean estado : listaEstados) {
                out.println("<tr>");
//                out.println(    "<td>"+estado.getId()+"</td>");                
                out.println("<td>" + estado.getTipoEstado() + "</td>");
                out.println("<td>" + estado.getNombre() + "</td>");
                List<EstadosBean> estadoP = estadosNegocio.consultarEstados(estado.getEstadoPrevio(), null, null, null, null, null);
                if(estadoP!=null&&estadoP.size()>0){
                    out.println("<td>" + estadoP.get(0).getNombre() + "</td>");
                }else{
                    out.println("<td>" + "" + "</td>");
                }
                List<EstadosBean> estadoS = estadosNegocio.consultarEstados(estado.getEstadoSiguiente(), null, null, null, null, null);
                if(estadoS!=null&&estadoS.size()>0){
                    out.println("<td>" + estadoS.get(0).getNombre() + "</td>");
                }else{
                    out.println("<td>" + "" + "</td>");
                }
                if(estado.getEstadoFinal()!=null && !estado.getEstadoFinal().isEmpty() && estado.getEstadoFinal().equals("T")){
                    out.println("<td>Sí</td>");
                }else{
                    out.println("<td>No</td>");
                }
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarEstado(" + estado.getId() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + estado.getId() + "');\">Eliminar</button>");
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
    }

    /**
     * 
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
     * 
     * @param reqeust
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
