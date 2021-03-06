package com.twg.controladores;

import com.twg.negocio.ArchivosNegocio;
import com.twg.negocio.ComentariosNegocio;
import com.twg.negocio.PerfilesNegocio;
import com.twg.negocio.PersonasNegocio;
import com.twg.persistencia.beans.ArchivosBean;
import com.twg.persistencia.beans.Paginas;
import com.twg.persistencia.beans.Permisos;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.utilidades.AlmacenamientoArchivos;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Clase encargada de gestionar las peticiones en torno al almacenamiento de
 * archivos en el servidor por medio de interfaz gráfica
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
@MultipartConfig
public class ArchivosController extends HttpServlet {

    private final ArchivosNegocio archivosNegocio = new ArchivosNegocio();
    private final PersonasNegocio personasNegocio = new PersonasNegocio();
    private final AlmacenamientoArchivos almacenamientoArchivos = new AlmacenamientoArchivos();
    private final ComentariosNegocio comentariosNegocio = new ComentariosNegocio();

    /**
     * Método encargado de procesar las peticiones que ingresan tanto por método
     * GET como POST
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String contiene = request.getParameter("filtroContiene");
        Date filtroFecha;
        try {
            filtroFecha = archivosNegocio.sdf.parse(request.getParameter("filtroFecha"));
        } catch (ParseException | NullPointerException e) {
            filtroFecha = null;
        }
        Integer idPersona;
        try {
            idPersona = Integer.valueOf(request.getParameter("idPersona"));
        } catch (NumberFormatException e) {
            idPersona = null;
        }
        Integer idArchivo;
        try {
            idArchivo = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            idArchivo = null;
        }
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String nombreArchivo = request.getParameter("nombreArchivo");
        String paginaStr = request.getParameter("pagina");

        Integer pagina;
        try {
            pagina = Integer.valueOf(paginaStr);
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        String mensajeAlerta = "";
        String mensajeError = "";
        String mensajeExito = "";
        String busqueda = request.getParameter("busqueda");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        List<String> permisosPagina = PerfilesNegocio.permisosPorPagina(request, Paginas.DOCUMENTACION);

        Integer personaSesion = null;
        try {
            personaSesion = (Integer) request.getSession(false).getAttribute("personaSesion");
        } catch (Exception e) {
        }

        if (permisosPagina != null && !permisosPagina.isEmpty() && 
                !permisosPagina.contains(Permisos.CONSULTAR.getNombre())) {
            idPersona = personaSesion;
        }

        switch (accion) {
            case "editar":
                JSONObject archivoConsultado = archivosNegocio.consultarArchivo(idArchivo);
                archivoConsultado.put("comentarios", comentariosNegocio.listaComentarios(permisosPagina, personaSesion, comentariosNegocio.TIPO_ARCHIVO, idArchivo));
                response.getWriter().write(archivoConsultado.toJSONString());
                break;
            case "consultar":
                cargarTabla(response, permisosPagina, contiene, filtroFecha, idPersona, pagina);
                break;
            case "eliminar":
                mensajeError = archivosNegocio.eliminarArchivo(idArchivo);
                if (mensajeError.isEmpty()) {
                    mensajeExito = "El archivo fue eliminado con éxito";
                }
                break;
            case "guardar":
                mensajeAlerta = archivosNegocio.validarDatos(idArchivo, nombre, descripcion, nombreArchivo);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = archivosNegocio.guardarArchivo(idArchivo, nombre, descripcion, personaSesion, nombreArchivo);
                    if (mensajeError.isEmpty()) {
                        Part archivoPart = request.getPart("archivo");
                        almacenamientoArchivos.cargarArchivo(archivoPart, nombreArchivo);
                        mensajeExito = "El archivo ha sido guardado con éxito";
                    }
                }
                break;
            case "completarPersonas":
                JSONArray arrayPersonas = personasNegocio.completarPersonas(busqueda);
                response.getWriter().write(arrayPersonas.toJSONString());
                break;
            case "obtenerArchivo":
                obtenerArchivo(response, nombreArchivo);
                break;
            case "guardarComentario":
                String comentario = request.getParameter("comentario");
                JSONObject comentarioGuardado = new JSONObject();
                mensajeAlerta = comentariosNegocio.validarDatos(comentario);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = comentariosNegocio.guardarComentario(null, personaSesion, comentario, comentariosNegocio.TIPO_ARCHIVO, idArchivo);
                    if (mensajeError.isEmpty()) {
                        comentarioGuardado.put("comentarios", comentariosNegocio.listaComentarios(permisosPagina, personaSesion, comentariosNegocio.TIPO_ARCHIVO, idArchivo));
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
                mensajeError = comentariosNegocio.eliminarComentario(idComentario, personaSesion);
                if (mensajeError.isEmpty()) {
                    comentarioEliminado.put("comentarios", comentariosNegocio.listaComentarios(permisosPagina, personaSesion, comentariosNegocio.TIPO_ARCHIVO, idArchivo));
                } else {
                    comentarioEliminado.put("mensajeError", mensajeError);
                }
                response.getWriter().write(comentarioEliminado.toJSONString());
                break;
            default:
                break;
        }
        if (accion.isEmpty() || accion.equals("guardar") || accion.equals("eliminar")) {
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            PersonasBean objetoPersona = personasNegocio.consultarPersona(personaSesion, null, null);
            if (objetoPersona != null) {
                request.setAttribute("hiddenCreador", objetoPersona.getNombres() + " " + objetoPersona.getApellidos());
                request.setAttribute("hiddenFecha", archivosNegocio.sdf.format(new Date()));
            }
            if (permisosPagina != null && !permisosPagina.isEmpty()) {
                request.setAttribute("opcionConsultar", permisosPagina.contains(Permisos.CONSULTAR.getNombre()));
                request.setAttribute("opcionGuardar", permisosPagina.contains(Permisos.GUARDAR.getNombre()));
                request.setAttribute("opcionCrear", permisosPagina.contains(Permisos.CREAR.getNombre()));
                request.setAttribute("opcionComentar", permisosPagina.contains(Permisos.COMENTAR.getNombre()));
            }
            request.getRequestDispatcher("jsp/archivos.jsp").forward(request, response);
        }
    }

    /**
     * Método encargado de construir la tabla que se mostrará en pantalla de
     * acuerdo a los filtros especificados
     *
     * @param response
     * @param contiene
     * @param fecha
     * @param persona
     * @throws ServletException
     * @throws IOException
     */
    private void cargarTabla(HttpServletResponse response, List<String> permisos, String contiene, Date fecha, Integer persona, int pagina) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        int registros = 10;
        int paginasAdicionales = 2;
        String limite = ((pagina - 1) * registros) + "," + registros;
        List<ArchivosBean> listaArchivos = archivosNegocio.consultarArchivos(null, contiene, fecha, persona, limite);

        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Nombre</th>");
        out.println("<th>Fecha creación</th>");
        out.println("<th>Creador</th>");
        out.println("<th>Archivo</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (listaArchivos != null && !listaArchivos.isEmpty()) {
            for (ArchivosBean archivo : listaArchivos) {
                out.println("<input type=\"hidden\" id=\"id\" name=\"id\" value=" + archivo.getId() + " />");
                out.println("<tr>");
                out.println("<td>" + archivo.getNombre() + "</td>");
                out.println("<td>" + archivosNegocio.sdf.format(archivo.getFechaCreacion()) + "</td>");
                out.println("<td>" + archivo.getNombrePersona() + "</td>");
                out.println("<td>" + archivo.getRuta() + "</td>");
                out.println("<td>");
                out.println("<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarArchivo(" + archivo.getId() + ");\">Gestionar</button>");
                if (permisos != null && !permisos.isEmpty() && permisos.contains(Permisos.ELIMINAR.getNombre())) {
                    out.println("<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#id').val('" + archivo.getId() + "');\">Eliminar</button>");
                }
                out.println("</td>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr>");
            out.println("<td colspan=\"5\">No se encontraron registros</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");

        /* Manejo de paginación */
        int cantidadArchivos = archivosNegocio.cantidadArchivos(null, contiene, fecha, persona);
        int cantidadPaginas = 1;
        if (cantidadArchivos > 0) {
            if (cantidadArchivos % registros == 0) {
                cantidadPaginas = cantidadArchivos / registros;
            } else {
                cantidadPaginas = (cantidadArchivos / registros) + 1;
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

    /**
     * Método encargado de descargar los archivos que han sido cargados al
     * sistema por la funcionalidad de archivos o asociados a un comentario
     *
     * @param response
     * @param nombreArchivo
     * @throws IOException
     */
    private void obtenerArchivo(HttpServletResponse response, String nombreArchivo) throws IOException {
        ServletOutputStream respuesta = response.getOutputStream();
        String mimetype = "application/x-download";
        FileInputStream archivo;
        archivo = new FileInputStream(almacenamientoArchivos.rutaCargas + nombreArchivo);
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
