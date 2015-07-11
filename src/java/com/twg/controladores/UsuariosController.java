package com.twg.controladores;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.beans.UsuariosBean;
import com.twg.persistencia.daos.PerfilesDao;
import com.twg.persistencia.daos.TiposDocumentosDao;
import com.twg.persistencia.daos.UsuariosDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class UsuariosController extends HttpServlet {

    private final TiposDocumentosDao tiposDocumentosDao = new TiposDocumentosDao();
    private final PerfilesDao perfilesDao = new PerfilesDao();
    private final UsuariosDao usuariosDao = new UsuariosDao();
    private String mensajeInformacion;
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
        mensajeInformacion = "";
        mensajeExito = "";
        mensajeError = "";
        
        String accion = request.getParameter("accion");
        if(accion == null){
            accion = "";
        }
        
        String idPersonaStr = request.getParameter("idPersona");
        String documento = request.getParameter("documento");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String nombreUsuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String perfilStr = request.getParameter("perfil");
        
        Integer idPersona = null;
        try {
            idPersona = Integer.valueOf(idPersonaStr);
        } catch (NumberFormatException e) {
        }
        
        Integer perfil = null;
        if(perfilStr != null && !perfilStr.isEmpty()){
            try {
                perfil = Integer.valueOf(perfilStr);
            } catch (NumberFormatException e) {
            }
        }
        
        List<UsuariosBean> listaUsuarios = null;
        try {
            switch(accion){
                case "consultar":
                    listaUsuarios = usuariosDao.consultarUsuarios(idPersona, nombreUsuario, clave, perfil, documento, tipoDocumento);
                    UsuariosBean  usuario = new UsuariosBean();
                    usuario.setDocumento(documento);
                    usuario.setTipoDocumento(tipoDocumento);
                    usuario.setUsuario(nombreUsuario);
                    usuario.setClave(clave);
                    usuario.setPerfil(perfil);
                    enviarDatos(request, usuario);
                    break;
                case "editar":
                    usuario = new UsuariosBean();
                    if(idPersona != null){
                        listaUsuarios = usuariosDao.consultarUsuarios();
                        if(listaUsuarios != null && !listaUsuarios.isEmpty()){
                            usuario = listaUsuarios.get(0);
                        }
                    }
                    enviarDatos(request, usuario);
                    break;
                case "guardar":
                    if(idPersona != null){
                        usuario = new UsuariosBean();
                        usuario.setIdPersona(idPersona);
                        usuario.setUsuario(nombreUsuario);
                        usuario.setClave(clave);
                        usuario.setPerfil(perfil);
                        int actualizacion = usuariosDao.actualizarUsuario(usuario);
                        if(actualizacion > 0){
                            mensajeExito = "El usuario ha sido guardado con éxito";
                        } else {
                            mensajeError = "El usuario no pudo ser guardado";
                        }
                    }
                    enviarDatos(request, new UsuariosBean());
                    break;
                case "eliminar":
                    if(idPersona != null){
                        int eliminacion = usuariosDao.eliminarUsuario(idPersona);
                        if(eliminacion > 0){
                            mensajeExito = "El usuario fue eliminado con éxito";
                        } else {
                            mensajeError = "El usuario no pudo ser eliminado";
                        }
                    } else {
                        mensajeError = "El usuario no pudo ser eliminado";
                    }
                    enviarDatos(request, new UsuariosBean());
                    break;
                default:
                    enviarDatos(request, new UsuariosBean());
                    break;
            }
            if(listaUsuarios == null){
                listaUsuarios = usuariosDao.consultarUsuarios();
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }
        
        request.setAttribute("mensajeInformacion", mensajeInformacion);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("tiposDocumentos", obtenerTiposDocumentos());
        request.setAttribute("usuarios", listaUsuarios);
        request.setAttribute("perfiles", obtenerPerfiles());
        request.getRequestDispatcher("jsp/usuarios.jsp").forward(request, response);
    }
    
    private void enviarDatos(HttpServletRequest request, UsuariosBean usuario){
        request.setAttribute("idPersona", usuario.getIdPersona());
        request.setAttribute("tipoDocumento", usuario.getTipoDocumento());
        request.setAttribute("documento", usuario.getDocumento());
        request.setAttribute("usuario", usuario.getUsuario());
        request.setAttribute("clave", "");
        request.setAttribute("perfil", usuario.getPerfil());
    }

    private List<TiposDocumentosBean> obtenerTiposDocumentos(){
        List<TiposDocumentosBean> tiposDocumentos = new ArrayList<>();
        try {
            tiposDocumentos = tiposDocumentosDao.consultarTiposDocumentos();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiposDocumentos;
    }
    
    private List<PerfilesBean> obtenerPerfiles(){
        List<PerfilesBean> perfiles = new ArrayList<>();
        try {
            perfiles = perfilesDao.consultarPerfiles();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perfiles;
    }
    
    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException{
        processRequest(reqeust, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException{
        processRequest(reqeust, response);
    }
    
    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processRequest(request, response);
    }
}
