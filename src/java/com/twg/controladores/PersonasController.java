package com.twg.controladores;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PerfilesDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Erika Jhoana
 */
public class PersonasController extends HttpServlet {
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
        if(accion == null){
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
        
        String tipoPersona = request.getParameter("tipoPersona");
        String cargoStr = request.getParameter("idCargo");
        String fechaInicio = request.getParameter("fechaInicio");
        
        String usuario = request.getParameter("usuario");
        String perfilStr = request.getParameter("perfil");
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");
            
        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }
        
        Integer cargo;
        try {
            cargo = Integer.valueOf(cargoStr);
        } catch (NumberFormatException e) {
            cargo = null;
        }
        
        Integer perfil;
        try {
            perfil = Integer.valueOf(perfilStr);
        } catch (NumberFormatException e) {
            perfil = null;
        }
        
        switch(accion){
            case "consultar":
                break;
            case "editar":
                break;
            case "guardar":
                break;
            case "eliminar":
                break;
            default:
                break;
        }
            
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.getRequestDispatcher("jsp/personas.jsp").forward(request, response);
    }
    
    private void enviarDatos(HttpServletRequest request, PersonasBean persona, UsuariosBean usuario){
        request.setAttribute("idPersona", persona.getId());
        request.setAttribute("tipoDocumento", persona.getTipoDocumento());
        request.setAttribute("documento", persona.getDocumento());
        request.setAttribute("nombres", persona.getNombres());
        request.setAttribute("apellidos", persona.getApellidos());
        request.setAttribute("telefono", persona.getTelefono());
        request.setAttribute("celular", persona.getCelular());
        request.setAttribute("correo", persona.getCorreo());
        request.setAttribute("direccion", persona.getDireccion());
        
        if(usuario!=null){
            request.setAttribute("usuario", usuario.getUsuario());
            PerfilesDao perfilDao = new PerfilesDao();
            try {
                PerfilesBean perfil = perfilDao.consultarPerfil(usuario.getPerfil());
                if(perfil!=null){
                    request.setAttribute("perfil", perfil.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException{
        processRequest(reqeust, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException{
        processRequest(reqeust, response);
    }
}
