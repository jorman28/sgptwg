package com.twg.controladores;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PerfilesDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        mensajeInformacion = "";
        mensajeExito = "";
        mensajeError = "";
        mensajeAlerta = "";
        
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
        String Id_Cargo = request.getParameter("Id_Cargo");
        String fechaInicio = request.getParameter("fechaInicio");
        
        String usuario = request.getParameter("usuario");
        String perfil = request.getParameter("perfil");
        String clave = request.getParameter("clave");
        String clave2 = request.getParameter("clave2");
            
        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }
        
        try {
            switch(accion){
                case "consultar":
                    
                    break;
                case "editar":
                    break;
                case "crearPersona":
                    break;
                case "eliminar":
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }
        
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.getRequestDispatcher("jsp/personas.jsp").forward(request, response);
    }
    
    public boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    private void enviarDatos(HttpServletRequest request, PersonasBean persona, UsuariosBean usuario){
        request.setAttribute("idPersona", persona.getId());
        request.setAttribute("tipoDocumento", persona.getTipo_documento());
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
