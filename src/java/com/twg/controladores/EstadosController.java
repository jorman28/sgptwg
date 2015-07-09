/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twg.controladores;

import com.twg.persistencia.beans.EstadosActividadesBean;
import com.twg.persistencia.beans.EstadosVersionesBean;

import com.twg.persistencia.daos.EstadosActividadesDao;
import com.twg.persistencia.daos.EstadosVersionesDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorman
 */
public class EstadosController extends HttpServlet {

private EstadosActividadesDao estadosActividadesDao = new EstadosActividadesDao();
private EstadosVersionesDao estadosVersionesDao = new EstadosVersionesDao();

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
        request.setAttribute("TipoEstado", obtenerTiposEstados());
        request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
    }

    private List<String> obtenerTiposEstados(){
        List<String> tiposEstados = new ArrayList<>();
        tiposEstados.add("Estado de Actividades");        
        tiposEstados.add("Estado de Versiones");
        return tiposEstados;
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
