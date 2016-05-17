package com.twg.controladores;

import com.twg.negocio.CargosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.negocio.TiposDocumentoNegocio;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.beans.PersonasBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Esta clase define métodos para controlar las peticiones y respuestas que se
 * hacen sobre el módulo principal de Personas, así como guardar, consultar,
 * modificar o eliminar la información.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class PersonasController extends HttpServlet {

    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final TiposDocumentoNegocio tiposDocumentoNegocio = new TiposDocumentoNegocio();
    private final CargosNegocio cargosNegocio = new CargosNegocio();
    private final PerfilesNegocio perfilesNegocio = new PerfilesNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Personas.
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
        String paginaStr = request.getParameter("pagina");

        String usuario = request.getParameter("usuario");
        String perfil = request.getParameter("perfil");
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");

        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }
        Integer pagina;
        try {
            pagina = Integer.valueOf(paginaStr);
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        String redireccion = "jsp/consultaPersonas.jsp";

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.PERSONAS);

        switch (accion) {
            case "consultar":
                cargarTabla(response, permisosPagina, idPersona, documento, tipoDocumento, nombres, apellidos, correo, usuario, perfil, cargo, pagina);
                break;
            case "editar":
                PersonasBean persona = personasNegocio.consultarPersona(idPersona, null, null);
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
                mensajeError = personasNegocio.validarDatos(documento, tipoDocumento, nombres, apellidos, telefono, celular, correo, direccion, cargo, usuario, perfil, clave, clave2);
                if (mensajeError.isEmpty()) {
                    mensajeAlerta = personasNegocio.guardarPersona(idPersona, documento, tipoDocumento, nombres, apellidos, telefono, celular, correo, direccion, cargo, usuario, perfil, clave, clave2);
                    if (mensajeAlerta.isEmpty()) {
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
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                if (permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
                    request.setAttribute("opcionConsultar", "T");
                }
                if (permisosPagina.contains(Permisos.CREAR.getNombre())) {
                    request.setAttribute("opcionCrear", "T");
                }
                if (permisosPagina.contains(Permisos.GUARDAR.getNombre())) {
                    request.setAttribute("opcionGuardar", "T");
                }
            }
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.setAttribute("tiposDocumentos", tiposDocumentoNegocio.consultarTiposDocumentos());
            request.setAttribute("cargos", cargosNegocio.consultarCargos(null, false, null));
            request.setAttribute("perfiles", perfilesNegocio.consultarPerfiles());
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }

    /**
     * Método encargado de pintar la tabla con el listado de registros que hay
     * sobre las personas.
     *
     * @param response
     * @param permisos
     * @param documento
     * @param tipoDocumento
     * @param nombres
     * @param apellidos
     * @param correo
     * @param usuario
     * @param perfil
     * @param cargo
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, Integer idPersona, String documento, String tipoDocumento, String nombres, String apellidos, String correo, String usuario, String perfil, String cargo, int pagina) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        int registros = 10;
        int paginasAdicionales = 2;
        String limite = ((pagina - 1) * registros) + "," + registros;
        List<PersonasBean> listaPersonas = personasNegocio.consultarPersonas(idPersona, documento, tipoDocumento, nombres, apellidos, correo, usuario, perfil, cargo, null, null, limite);
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
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"editarPersona(" + persona.getId() + ")\">Detalle</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idPersona').val('" + persona.getId() + "');\">Eliminar</button>");
                }
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

        /* Manejo de paginación */
        int cantidadUsuarios = personasNegocio.cantidadPersonas(idPersona, documento, tipoDocumento, nombres, apellidos, correo, usuario, perfil, cargo, null);
        int cantidadPaginas = 1;
        if (cantidadUsuarios > 0) {
            if (cantidadUsuarios % registros == 0) {
                cantidadPaginas = cantidadUsuarios / registros;
            } else {
                cantidadPaginas = (cantidadUsuarios / registros) + 1;
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
            out.println("       <li><a href=\"javascript:void(consultarDatos(1))\"><span>&laquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(consultarDatos(" + (pagina - 1) + "))\"><span>&lsaquo;</span></a></li>");
        }
        for (int pag = pagina - paginasPrevias; pag <= pagina + paginasPosteriores; pag++) {
            if (pagina == pag) {
                out.println("   <li class=\"active\"><a href=\"javascript:void(0)\"><span>" + pag + "</span></a></li>");
            } else {
                out.println("   <li><a href=\"javascript:void(consultarDatos(" + pag + "))\"><span>" + pag + "</span></a></li>");
            }
        }
        if (pagina != cantidadPaginas) {
            out.println("       <li><a href=\"javascript:void(consultarDatos(" + (pagina + 1) + "))\"><span>&rsaquo;</span></a></li>");
            out.println("       <li><a href=\"javascript:void(consultarDatos(" + cantidadPaginas + "))\"><span>&raquo;</span></a></li>");
        }
        out.println("   </ul>");
        out.println("</nav>");
    }

    /**
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
     * @param reqeust
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }
}
