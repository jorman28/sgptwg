package com.twg.controladores;

import com.twg.negocio.CargosNegocio;
import com.twg.persistencia.beans.CargosBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author erikasta07
 */
public class CargosController extends HttpServlet {

    private final CargosNegocio cargosNegocio = new CargosNegocio();

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
        String mensajeExito = "";
        String mensajeError = "";
        String mensajeAlerta = "";

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String idCargoStr = request.getParameter("idCargo");
        String descripcion = request.getParameter("descripcion");

        Integer idCargo = null;
        try {
            idCargo = Integer.valueOf(idCargoStr);
        } catch (NumberFormatException e) {
        }

        switch (accion) {
            case "consultar":
                cargarTabla(response, descripcion);
                break;
            case "editar":
                JSONObject object = cargosNegocio.consultarCargo(idCargo);
                response.getWriter().write(object.toJSONString());
                break;
            case "guardar":
                mensajeAlerta = cargosNegocio.validarCargo(idCargoStr, descripcion);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = cargosNegocio.guardarCargo(idCargoStr, descripcion);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El cargo ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("idCargo", idCargo);
                request.setAttribute("descripcion", descripcion);
                break;
            case "eliminar":
                mensajeError = cargosNegocio.eliminarCargo(idCargo);
                break;
            default:
                break;
        }

        if (!accion.equals("consultar") && !accion.equals("editar")) {
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.getRequestDispatcher("jsp/cargos.jsp").forward(request, response);
        }
    }

    private void cargarTabla(HttpServletResponse response, String nombre) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        List<CargosBean> listaCargos = cargosNegocio.consultarCargos(nombre, false);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
        out.println(            "<th>Cargo</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaCargos != null && !listaCargos.isEmpty()){
            for (CargosBean cargo : listaCargos) {
                out.println("<tr>");			
                out.println(    "<td>"+cargo.getNombre()+"</td>");
                out.println(    "<td>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarCargo("+cargo.getId()+")\">Editar</button>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idCargo').val('"+cargo.getId()+"');\">Eliminar</button>");
                out.println(    "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");			
            out.println(        "<td colspan=\"2\">No se encontraron registros</td>");
            out.println(    "</tr>");
        }
        out.println(    "</tbody>");
        out.println("</table>");
    }
    
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
