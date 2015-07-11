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
 * / *
 * / * @author Jorman /
 */
public class EstadosController extends HttpServlet {

    private final EstadosActividadesDao estadosActividadesDao = new EstadosActividadesDao();
    private final EstadosVersionesDao estadosVersionesDao = new EstadosVersionesDao();

    private static final String strEstActividades = "Estados de Actividades";
    private static final String strEstVersiones = "Estados de Versiones";

    private String tipoEstado;
    private String nombre;
    //private int resultado;
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
        if (accion == null) {
            accion = "";
        } else {

            tipoEstado = request.getParameter("TipoEstado");
            nombre = request.getParameter("nombre");
            String validacion = "";
            //resultado = 0;

            if (tipoEstado.equals(strEstActividades)) {
                EstadosActividadesBean estadoActividad = null;

                switch (accion) {
                    case "consultar":
                        break;
                    case "editar":
                        break;
                    case "guardar":
                        validacion = obligatorios(true);
                        if (validacion.length() > 0) {
                            mantenerDatos(request);
                            request.setAttribute("msg", validacion);
                        } else {
//                            estadoActividad = EstadosActividadesDao.buscarEstadoAhorro(codigo);
//                            if (estadoAhorro != null) {
//                                estadoAhorro.setCodigo(codigo);
//                                estadoAhorro.setNombre(nombre);
//
//                                resultado = estadoAhorroDao.modificarEstadoAhorro(estadoAhorro);
//                            } else {
//                                estadoAhorro = new EstadoAhorroBean();
//                                estadoAhorro.setCodigo(codigo);
//                                estadoAhorro.setNombre(nombre);
//
//                                resultado = estadoAhorroDao.crearEstadoAhorro(estadoAhorro);
//                            }
//
//                            if (resultado == 1) {
//                                llenarCampos(request, null);
//                                request.setAttribute("msg", "El estado del ahorro fué guardado con éxito.");
//                            } else {
//                                request.setAttribute("msg", "El estado del ahorro no pudo ser guardado.");
//                            }
                        }
                        break;
                    case "eliminar":
                        break;
                    default:
                        break;
                }
                if (tipoEstado.equals(strEstVersiones)) {
                    EstadosVersionesBean estadoVersion = null;

                    switch (accion) {
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
                }
            }

        }

        request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    public String obligatorios(boolean guardado) {
        StringBuilder resultado = new StringBuilder();
        /* Si es guardado de datos valida los demás campos de lo contrario 
         * retorna la validación sobre el clave 
         */
        if (!guardado) {
            return resultado.toString();
        }
        if (nombre == null || nombre.equals("")) {
            resultado.append("El campo 'Nombre' no debe estar vacío. <br>");
        }
        return resultado.toString();
    }
    
    public void llenarCamposEstadoActividad(HttpServletRequest request, EstadosActividadesBean estadoActividad){
        request.setAttribute("nombre", estadoActividad != null ? estadoActividad.getNombre() : "");
    }
    
    public void llenarCamposEstadoVersion(HttpServletRequest request, EstadosVersionesBean estadoActividad){
        request.setAttribute("nombre", estadoActividad != null ? estadoActividad.getNombre() : "");
    }
    
    public void mantenerDatos(HttpServletRequest request){
        request.setAttribute("nombre", nombre != null ? nombre : "");
    }
}
