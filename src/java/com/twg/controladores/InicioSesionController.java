package com.twg.controladores;

import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.UsuariosDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pipe
 */
public class InicioSesionController extends HttpServlet {

    private final UsuariosDao usuariosDao = new UsuariosDao();
    private String mensajeAlerta;
    private String mensajeExito;
    private String mensajeError;
    
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
        mensajeAlerta = "";
        mensajeExito = "";
        mensajeError = "";
        String redireccion = "jsp/inicioSesion.jsp";
        String accion = request.getParameter("accion");

        if(accion != null && accion.equals("ingresar")){
            String usuario = request.getParameter("usuario");
            String clave = request.getParameter("clave");
            if(usuario != null && !usuario.isEmpty()){
                if(clave != null && !clave.isEmpty()){
                    try {
                        List<UsuariosBean> listaUsuarios = usuariosDao.consultarUsuarios(usuario);
                        if(listaUsuarios != null && !listaUsuarios.isEmpty()){
                            if(listaUsuarios.get(0).getClave().equals(clave)){
                                redireccion = "jsp/paginaInicio.jsp";
                            } else {
                                mensajeError = "La contraseña es incorrecta para el usuario ingresado";
                            }
                        } else {
                            mensajeError = "El usuario ingresado no está registrado en el sistema";
                        }
                    } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                        Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    mensajeAlerta = "Debe ingresar la clave";
                }
                request.setAttribute("usuario", usuario);
            } else {
                mensajeAlerta = "Debe ingresar el usuario";
            }
        
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
