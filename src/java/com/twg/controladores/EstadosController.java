package com.twg.controladores;

import com.twg.persistencia.beans.EstadosActividadesBean;
import com.twg.persistencia.beans.EstadosVersionesBean;

import com.twg.persistencia.daos.EstadosActividadesDao;
import com.twg.persistencia.daos.EstadosVersionesDao;
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
 * @author Jorman
 */
public class EstadosController extends HttpServlet {

    private final EstadosActividadesDao estadosActividadesDao = new EstadosActividadesDao();    
    private final EstadosVersionesDao estadosVersionesDao = new EstadosVersionesDao();

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

        String tipoEstado = request.getParameter("tipoEstado");
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        
        Integer idRegistro = null;
        try {
            idRegistro = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            mensajeError = e.getMessage();
        }
        
        List<EstadosActividadesBean> listaEstadosActividades = null;
        try {
            switch(accion){
                case "consultar":
                    listaEstadosActividades = estadosActividadesDao.consultarEstadosActividades(idRegistro, nombre);
                    EstadosActividadesBean estadoActividad = new EstadosActividadesBean();
                    estadoActividad.setId(idRegistro);
                    estadoActividad.setNombre(nombre);
                    enviarDatos(request, estadoActividad);
                    break;
                case "editar":
                    estadoActividad = new EstadosActividadesBean();
                    if(id != null){
                        List<EstadosActividadesBean> estadosActividades = estadosActividadesDao.consultarEstadosActividades(idRegistro);
                        if(estadosActividades != null && !estadosActividades.isEmpty()){
                            estadoActividad = estadosActividades.get(0);
                        }
                    }
                    enviarDatos(request, estadoActividad);
                    break;
                case "guardar":
                    if(id != null){
                        estadoActividad = new EstadosActividadesBean();
                        estadoActividad.setId(idRegistro);
                        estadoActividad.setNombre(nombre);
                        int actualizacion = estadosActividadesDao.actualizarEstadoActividad(estadoActividad);
                        if(actualizacion > 0){
                            mensajeExito = "El estado de la actividad ha sido guardado con éxito";
                        } else {
                            mensajeError = "El estado de la actividad no pudo ser guardado";
                        }
                    }
                    enviarDatos(request, new EstadosActividadesBean());
                    break;
                case "eliminar":
                    if(id != null){
                        int eliminacion = estadosActividadesDao.eliminarEstadoActividad(idRegistro);
                        if(eliminacion > 0){
                            mensajeExito = "El estado de la actividad fue eliminado con éxito";
                        } else {
                            mensajeError = "El estado de la actividad no pudo ser eliminado";
                        }
                    } else {
                        mensajeError = "El usuario no pudo ser eliminado";
                    }
                    enviarDatos(request, new EstadosActividadesBean());
                    break;
                default:
                    enviarDatos(request, new EstadosActividadesBean());
                    break;
            }
            if(listaEstadosActividades == null){
                listaEstadosActividades = estadosActividadesDao.consultarEstadosActividades();
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }
        
        request.setAttribute("mensajeInformacion", mensajeInformacion);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
    }
    
    private void enviarDatos(HttpServletRequest request, EstadosActividadesBean estadoActividad){
        request.setAttribute("id", estadoActividad.getId());
        request.setAttribute("nombre", estadoActividad.getNombre());
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
