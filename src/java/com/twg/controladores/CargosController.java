package com.twg.controladores;

import com.twg.negocio.CargosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.persistencia.beans.CargosBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 * Esta clase es la encargada de controlar las peticiones que se hacen sobre el
 * administrador de cargos, como crear, consultar, modificar, eliminar y listar
 * la información.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class CargosController extends HttpServlet {

    private final CargosNegocio cargosNegocio = new CargosNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de cargos
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
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
        String paginaStr = request.getParameter("pagina");

        Integer idCargo = null;
        try {
            idCargo = Integer.valueOf(idCargoStr);
        } catch (NumberFormatException e) {
        }

        Integer pagina;
        try {
            pagina = Integer.valueOf(paginaStr);
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.CARGOS);

        String personaSesion = "";
        try {
            personaSesion = String.valueOf(request.getSession().getAttribute("personaSesion"));
        } catch (Exception e) {
            System.err.print("Error obteniendo la persona en sesion");
        }
        
        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, descripcion, pagina);
                break;
            case "editar":
                JSONObject object = cargosNegocio.consultarCargo(idCargo);
                response.getWriter().write(object.toJSONString());
                break;
            case "guardar":
                mensajeAlerta = cargosNegocio.validarCargo(idCargoStr, descripcion);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = cargosNegocio.guardarCargo(idCargoStr, descripcion, personaSesion);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El cargo ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("idCargo", idCargo);
                request.setAttribute("descripcion", descripcion);
                break;
            case "eliminar":
                mensajeError = cargosNegocio.eliminarCargo(idCargo, personaSesion);
                if(mensajeError.equals("")){
                    mensajeExito = "El cargo se eliminó correctamente.";
                }
                break;
            default:
                break;
        }

        if (!accion.equals("consultar") && !accion.equals("editar")) {
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);

            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                    request.setAttribute("opcionConsultar", "T");
                }
                if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                    request.setAttribute("opcionGuardar", "T");
                }
            }

            request.getRequestDispatcher("jsp/cargos.jsp").forward(request, response);
        }
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre los cargos
     *
     * @param response
     * @param permisos
     * @param nombre Este parámetro se utiliza para filtrar la información de la
     * tabla por nombre.
     *
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, String nombre, int pagina) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        int registros = 10;
        int paginasAdicionales = 2;
        String limite = ((pagina - 1) * registros) + "," + registros;
        List<CargosBean> listaCargos = cargosNegocio.consultarCargos(nombre, false, limite);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Cargo</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaCargos != null && !listaCargos.isEmpty()) {
            for (CargosBean cargo : listaCargos) {
                out.println("<tr>");
                out.println("<td>" + cargo.getNombre() + "</td>");
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarCargo(" + cargo.getId() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idCargo').val('" + cargo.getId() + "');\">Eliminar</button>");
                }
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");
            out.println("<td colspan=\"2\">No se encontraron registros</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        
        /* Manejo de paginación */
        int cantidadCargos = cargosNegocio.cantidadCargos(nombre, false);
        int cantidadPaginas = 1;
        if (cantidadCargos > 0) {
            if (cantidadCargos % registros == 0) {
                cantidadPaginas = cantidadCargos / registros;
            } else {
                cantidadPaginas = (cantidadCargos / registros) + 1;
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
            out.println("       <li><a href=\"javascript:void(llenarTabla(1))\"><span>&laquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + (pagina - 1) + "))\"><span>&lsaquo;</span></a></li>");
        }
        for (int pag = pagina - paginasPrevias; pag <= pagina + paginasPosteriores; pag++) {
            if (pagina == pag) {
                out.println("   <li class=\"active\"><a href=\"javascript:void(0)\"><span>" + pag + "</span></a></li>");
            } else {
                out.println("   <li><a href=\"javascript:void(llenarTabla(" + pag + "))\"><span>" + pag + "</span></a></li>");
            }
        }
        if (pagina != cantidadPaginas) {
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + (pagina + 1) + "))\"><span>&rsaquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(llenarTabla(" + cantidadPaginas + "))\"><span>&raquo;</span></a></li>");
        }
        out.println("   </ul>");
        out.println("</nav>");
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
