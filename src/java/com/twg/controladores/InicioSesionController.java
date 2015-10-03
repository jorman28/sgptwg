package com.twg.controladores;

import com.twg.negocio.UsuariosNegocio;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pipe
 */
public class InicioSesionController extends HttpServlet {

    private final UsuariosNegocio usuariosNegocio = new UsuariosNegocio();
    
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
        String redireccion = "jsp/inicioSesion.jsp";
        String accion = request.getParameter("accion");

        if(accion != null && accion.equals("ingresar")){
            String usuario = request.getParameter("usuario");
            String clave = request.getParameter("clave");
            Map<String, Object> inicio = usuariosNegocio.validarInicioSession(request.getContextPath(), usuario, clave);
            if(inicio.get("mensajeAlerta") != null){
                request.setAttribute("mensajeAlerta", inicio.get("mensajeAlerta"));
                request.setAttribute("usuario", usuario);
            } else if(inicio.get("mensajeError") != null) {
                request.setAttribute("mensajeError", inicio.get("mensajeError"));
                request.setAttribute("usuario", usuario);
            } else {
                request.getSession().setAttribute("menu", inicio.get("menu"));
                request.getSession().setAttribute("permisos", inicio.get("permisos"));
                redireccion = "jsp/paginaInicio.jsp";
            }
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
