package com.twg.controladores;

import com.twg.negocio.ProyectosNegocio;
import com.twg.negocio.VersionesNegocio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Pipe
 */
public class ProyectosController extends HttpServlet {

    ProyectosNegocio proyectosNegocio = new ProyectosNegocio();
    VersionesNegocio versionesNegocio = new VersionesNegocio();

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

        String mensajeError = "";
        String mensajeExito = "";
        String mensajeAlerta = "";
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        String redireccion = "jsp/proyectos.jsp";
        String idProyecto = request.getParameter("idProyecto");
        String nombreProyecto = request.getParameter("nombreProyecto");
        String fechaInicioProyecto = request.getParameter("fechaInicioProyecto");
        String idPersona = request.getParameter("idPersona");

        String idVersion = request.getParameter("idVersion");
        String idProyectoVersion = request.getParameter("idProyectoVersion");
        String fechaInicioVersion = request.getParameter("fechaInicioVersion");
        String fechaFinVersion = request.getParameter("fechaFinVersion");
        String nombreVersion = request.getParameter("nombreVersion");
        String estado = request.getParameter("estado");
        String alcance = request.getParameter("alcance");

        switch (accion) {
            case "guardarProyecto":
                mensajeAlerta = proyectosNegocio.validarDatos(nombreProyecto, fechaInicioProyecto, idPersona);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = proyectosNegocio.guardarProyecto(idProyecto, nombreVersion, fechaInicioProyecto, idPersona);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El proyecto ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("idProyecto", idProyecto);
                request.setAttribute("nombreProyecto", nombreProyecto);
                request.setAttribute("fechaInicioProyecto", fechaInicioProyecto);
                request.setAttribute("idPersona", idPersona);
                break;
            case "guardarVersion":
                mensajeAlerta = versionesNegocio.validarDatos(nombreVersion, fechaInicioVersion, fechaFinVersion, alcance, idProyectoVersion, estado);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = versionesNegocio.guardarVersion(idVersion, nombreVersion, fechaInicioVersion, fechaFinVersion, alcance, idProyectoVersion, estado);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "La versión ha sido guardada con éxito";
                        break;
                    }
                }
                request.setAttribute("idProyectoVersion", idProyecto);
                request.setAttribute("idVersion", idVersion);
                request.setAttribute("nombreVersion", nombreVersion);
                request.setAttribute("fechaInicioVersion", fechaInicioVersion);
                request.setAttribute("fechaFinVersion", fechaFinVersion);
                request.setAttribute("alcance", alcance);
                request.setAttribute("estado", estado);
                break;
            case "completarPersonas":
                JSONArray array = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("value", 1);
                object.put("label", "Toronto");
                array.add(object);
                object = new JSONObject();
                object.put("value", 2);
                object.put("label", "Montreal");
                array.add(object);
                object = new JSONObject();
                object.put("value", 3);
                object.put("label", "Buffalo");
                array.add(object);
                response.getWriter().write(array.toJSONString());
                break;
            default:
                break;
        }

        if (!accion.equals("hola")) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
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
