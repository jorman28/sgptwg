package com.twg.controladores;

import com.twg.negocio.UsuariosNegocio;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Esta clase define métodos para controlar las peticiones y respuestas 
 * que se hacen sobre el sistema, al tratar de iniciar sesión. Cuando esto 
 * ocurre, se cargan todos los aatributos de sesión.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class InicioSesionController extends HttpServlet {

    private final UsuariosNegocio usuariosNegocio = new UsuariosNegocio();
    
    /**
     * Método encargado de procesar las peticiones que ingresan por métodos get
     * y post al controlador de Inicio de Sesion
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
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
                request.getSession().setAttribute("personaSesion", inicio.get("personaSesion"));
                request.getSession().setAttribute("usuarioSesion", inicio.get("usuarioSesion"));
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
