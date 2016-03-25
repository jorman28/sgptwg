package com.twg.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase encargada de gestionar las peticiones en torno al almacenamiento de
 * archivos en el servidor por medio de interfaz gráfica
 *
 * @author Andrés Giraldo
 */
public class ArchivosController extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        switch (accion) {
            default:
                break;
        }
        request.getRequestDispatcher("jsp/archivos.jsp").forward(request, response);
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
