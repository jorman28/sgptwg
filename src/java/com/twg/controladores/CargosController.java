/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.controladores;

import com.twg.persistencia.beans.CargosBean;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.daos.CargosDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author erikasta07
 */
public class CargosController extends HttpServlet {

    private final CargosDao cargosDao = new CargosDao();
    private String mensajeInformacion;
    private String mensajeExito;
    private String mensajeError;
    private String mensajeAlerta;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mensajeInformacion = "";
        mensajeExito = "";
        mensajeError = "";
        mensajeAlerta = "";
        
        String accion = request.getParameter("accion");
        if(accion == null){
            accion = "";
        }
        
        String idCargoStr = request.getParameter("idCargo");
        String descripcion = request.getParameter("descripcion");
        
        Integer idCargo = null;
        try {
            idCargo = Integer.valueOf(idCargoStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        List<CargosBean> listaCargos = null;
        CargosBean cargo = null;
        int res=0;
        try {
            switch(accion){
                case "consultar":
                    if(descripcion!=null && !descripcion.isEmpty()){
                        listaCargos = cargosDao.consultarCargos(descripcion);
                        if(listaCargos!=null&&listaCargos.size()>0){
                            request.setAttribute("descripcion", listaCargos.get(0).getNombre());
                        }else{
                            mensajeError = "No se encontraron registros";
                        }
                    }else{
                        mensajeError = "Debe ingresar un parámetro de búsqueda";
                    }
                    break;
                case "editar":
                    if(idCargo!=null){
                        cargo = cargosDao.consultarCargo(idCargo);
                        if(cargo!=null){
                            request.setAttribute("idCargo", cargo.getId());
                            request.setAttribute("descripcion", cargo.getNombre());
                        }else{
                            mensajeAlerta = "No se logró obtener el identificador del cargo";
                        }
                    }else{
                        mensajeError = "No se logró obtener el identificador del cargo";
                    }
                    break;
                case "crear":
                    try {
                        if(descripcion!=null&&!descripcion.equals("")){
                            if(idCargo!=null){
                                cargo = cargosDao.consultarCargo(idCargo);
                                if(cargo!=null){
                                    cargo.setNombre(descripcion);
                                    res = cargosDao.actualizarCargo(cargo);
                                    if(res==1){
                                        mensajeExito = "El registro se actualizó correctamente";
                                    }else{
                                        mensajeError = "No se logró actualizar el registro";
                                    }
                                }else{
                                    cargo = new CargosBean();
                                    cargo.setNombre(descripcion);
                                    res = cargosDao.insertarCargo(cargo);
                                    if(res==1){
                                        mensajeExito = "El registro se guardó correctamente";
                                    }else{
                                        mensajeError = "No se logró guardar el registro";
                                    }
                                }
                            }else{
                                cargo = new CargosBean();
                                cargo.setNombre(descripcion);
                                res = cargosDao.insertarCargo(cargo);
                                if(res==1){
                                    mensajeExito = "El registro se guardó correctamente";
                                }else{
                                    mensajeError = "No se logró guardar el registro";
                                }
                            }
                        }else{
                            mensajeError = "Debe ingresar el campo 'Descripción'";
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        mensajeError = "Ocurrió un error guardando el registro";
                    }
                    
                    break;
                case "eliminar":
                    if(idCargo!=null){
                        res = cargosDao.eliminarCargo(idCargo);
                        if(res==1){
                            mensajeExito = "El registro se eliminó correctamente";
                        }else{
                            mensajeError = "No se logró eliminar el registro";
                        }
                    }else{
                        mensajeError = "No se logró obtener el identificador del cargo";
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
        }
        
        if(listaCargos == null){
            try {
                listaCargos = cargosDao.consultarCargos(null);
            } catch (Exception e) {
                mensajeError = "Ha currido un error inesperado";
                e.printStackTrace();
            }
            
        }
        
        request.setAttribute("mensajeInformacion", mensajeInformacion);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("listaCargos", listaCargos);
        request.getRequestDispatcher("jsp/cargos.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
