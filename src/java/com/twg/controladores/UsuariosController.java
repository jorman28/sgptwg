package com.twg.controladores;

import com.twg.persistencia.beans.PerfilesBean;
import com.twg.persistencia.beans.TiposDocumentosBean;
import com.twg.persistencia.daos.PerfilesDao;
import com.twg.persistencia.daos.TiposDocumentosDao;
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
        String accion = request.getParameter("accion");
        if(accion == null){
            accion = "";
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
        request.setAttribute("tiposDocumentos", obtenerTiposDocumentos());
        request.setAttribute("perfiles", obtenerPerfiles());
        request.getRequestDispatcher("jsp/usuarios.jsp").forward(request, response);
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
}
