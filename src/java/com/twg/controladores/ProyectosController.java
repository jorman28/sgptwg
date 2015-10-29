package com.twg.controladores;

import com.twg.negocio.ProyectosNegocio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pipe
 */
public class ProyectosController extends HttpServlet {

    ProyectosNegocio proyectosNegocio = new ProyectosNegocio();

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
        String nombre = request.getParameter("nombre");
        String fechaInicio = request.getParameter("fechaInicio");
        String idPersona = request.getParameter("idPersona");
        switch (accion) {
            case "guardar":
                mensajeAlerta = proyectosNegocio.validarDatos(nombre, fechaInicio, idPersona);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = proyectosNegocio.guardarProyecto(idProyecto, nombre, fechaInicio, idPersona);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El proyecto ha sido insertado con éxito";
                        break;
                    }
                }
                request.setAttribute("idProyecto", idProyecto);
                request.setAttribute("nombre", nombre);
                request.setAttribute("fechaInicio", fechaInicio);
                request.setAttribute("idPersona", idPersona);
                break;
            default:
                break;
        }
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.getRequestDispatcher(redireccion).forward(request, response);
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
