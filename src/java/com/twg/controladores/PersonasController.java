package com.twg.controladores;

import com.twg.negocio.CargosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.TiposDocumentoNegocio;
import com.twg.persistencia.beans.PersonasBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Erika Jhoana
 */
public class PersonasController extends HttpServlet {

    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final TiposDocumentoNegocio tiposDocumentoNegocio = new TiposDocumentoNegocio();
    private final CargosNegocio cargosNegocio = new CargosNegocio();
    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();

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

        String idPersonaStr = request.getParameter("idPersona");
        String documento = request.getParameter("documento");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String cargo = request.getParameter("cargo");

        String usuario = request.getParameter("usuario");
        String perfil = request.getParameter("perfil");
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");

        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }

        String redireccion = "jsp/consultaPersonas.jsp";

        switch (accion) {
            case "consultar":
                cargarTabla(response, documento, tipoDocumento, nombres, apellidos, correo, usuario, perfil, cargo);
                break;
            case "editar":
                PersonasBean persona = personasNegocio.consultarPersona(idPersonaStr, null, null);
                if (persona != null) {
                    request.setAttribute("formulario", "EDICION");
                    request.setAttribute("idPersona", persona.getId());
                    request.setAttribute("documento", persona.getDocumento());
                    request.setAttribute("tipoDocumento", persona.getTipoDocumento());
                    request.setAttribute("nombres", persona.getNombres());
                    request.setAttribute("apellidos", persona.getApellidos());
                    request.setAttribute("telefono", persona.getTelefono());
                    request.setAttribute("celular", persona.getCelular());
                    request.setAttribute("correo", persona.getCorreo());
                    request.setAttribute("direccion", persona.getDireccion());
                    request.setAttribute("cargo", persona.getCargo());
                    request.setAttribute("usuario", persona.getUsuario());
                    request.setAttribute("perfil", persona.getPerfil());
                    redireccion = "jsp/personas.jsp";
                }
                break;
            case "guardar":
                mensajeAlerta = personasNegocio.validarDatos(documento, tipoDocumento, nombres, apellidos, correo, direccion, cargo, usuario, perfil, clave, clave2);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = personasNegocio.guardarPersona(idPersona, documento, tipoDocumento, nombres, apellidos, telefono, celular, correo, direccion, cargo, usuario, perfil, clave, clave2);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "La persona ha sido guardada con éxito";
                        break;
                    }
                }
                request.setAttribute("idPersona", idPersonaStr);
                request.setAttribute("documento", documento);
                request.setAttribute("tipoDocumento", tipoDocumento);
                request.setAttribute("nombres", nombres);
                request.setAttribute("apellidos", apellidos);
                request.setAttribute("telefono", telefono);
                request.setAttribute("celular", celular);
                request.setAttribute("correo", correo);
                request.setAttribute("direccion", direccion);
                request.setAttribute("cargo", cargo);
                request.setAttribute("usuario", usuario);
                request.setAttribute("perfil", perfil);
                redireccion = "jsp/personas.jsp";
                break;
            case "eliminar":
                mensajeError = personasNegocio.eliminarPersona(idPersona);
                if (mensajeError.isEmpty()) {
                    mensajeExito = "La persona ha sido eliminada con éxito";
                }
                break;
            case "nuevo":
                redireccion = "jsp/personas.jsp";
                request.setAttribute("formulario", "CREACION");
                break;
            default:
                break;
        }

        if (!accion.equals("consultar")) {
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.setAttribute("tiposDocumentos", tiposDocumentoNegocio.consultarTiposDocumentos());
            request.setAttribute("cargos", cargosNegocio.consultarCargos(null));
            request.setAttribute("perfiles", perfilesNegocio.consultarPerfiles());
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }

    private void cargarTabla(HttpServletResponse response, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil, String cargo) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        List<PersonasBean> listaPersonas = personasNegocio.consultarPersonas(documento, tipoDocumento, nombres, apellidos, correo, usuario, perfil, cargo);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Tipo documento</th>");
        out.println("<th>Documento</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Dirección</th>");
        out.println("<th>Correo</th>");
        out.println("<th>Cargo</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaPersonas != null && !listaPersonas.isEmpty()) {
            for (PersonasBean persona : listaPersonas) {
                out.println("<tr>");
                out.println("<td>" + persona.getNombreTipoDocumento() + "</td>");
                out.println("<td>" + persona.getDocumento() + "</td>");
                out.println("<td>" + persona.getNombres() + " " + persona.getApellidos() + "</td>");
                out.println("<td>" + persona.getDireccion() + "</td>");
                out.println("<td>" + persona.getCorreo() + "</td>");
                out.println("<td>" + persona.getNombreCargo() + "</td>");
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"editarPersona(" + persona.getId() + ")\">Editar</button>");
                out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idPersona').val('" + persona.getId() + "');\">Eliminar</button>");
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr>");
            out.println("<td colspan=\"9\">No se encontraron registros</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
    }

    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }
}
