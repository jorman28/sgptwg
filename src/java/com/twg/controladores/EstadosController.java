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
 * @author Pipe
 */
public class EstadosController extends HttpServlet {

    private final EstadosActividadesDao estadosActividadesDao = new EstadosActividadesDao();
    private final EstadosVersionesDao estadosVersionesDao = new EstadosVersionesDao();

    private static final String strConstEstadoActividad = "Estado de Actividad";
    private static final String strConstEstadoVersion = "Estado de Versión";

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

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String tipoEstado = request.getParameter("tipoEstado");
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");

        Integer idEstado = null;
        try {
            idEstado = Integer.valueOf(id);
        } catch (NumberFormatException e) {
        }
        List<EstadosActividadesBean> listaEstadosActividades = null;

        //if (tipoEstado.equals(strConstEstadoActividad)) {
            try {
                switch (accion) {
                    case "consultar":
                        listaEstadosActividades = estadosActividadesDao.consultarEstadosActividades(idEstado, nombre);
                        EstadosActividadesBean estadoActividad = new EstadosActividadesBean();
                        estadoActividad.setId(idEstado);
                        estadoActividad.setNombre(nombre);
                        enviarDatosEstadoActividad(request, estadoActividad);
                        break;
                    case "editar":
                        estadoActividad = new EstadosActividadesBean();
                        if (idEstado != null) {
                            List<EstadosActividadesBean> estadosActividades = estadosActividadesDao.consultarEstadosActividades(idEstado);
                            if (estadosActividades != null && !estadosActividades.isEmpty()) {
                                estadoActividad = estadosActividades.get(0);
                            }
                        }
                        enviarDatosEstadoActividad(request, estadoActividad);
                        break;
                    case "guardar":
                        if (nombre != null) {
                            estadoActividad = new EstadosActividadesBean();
                            estadoActividad.setId(idEstado);
                            estadoActividad.setNombre(nombre);
                            int guardar = estadosActividadesDao.insertarEstadoActividad(estadoActividad);
                            if (guardar > 0) {
                                mensajeExito = "El estado de actividad ha sido guardado con éxito";
                            } else {
                                mensajeError = "El estado de actividad no pudo ser guardado";
                            }
                        }
                        enviarDatosEstadoActividad(request, new EstadosActividadesBean());
                        break;
                    case "eliminar":
                        if (id != null) {
                            int eliminacion = estadosActividadesDao.eliminarEstadoActividad(idEstado);
                            if (eliminacion > 0) {
                                mensajeExito = "El estado de actividad fue eliminado con éxito";
                            } else {
                                mensajeError = "El estado de actividad no pudo ser eliminado";
                            }
                        } else {
                            mensajeError = "El estado de actividad no pudo ser eliminado";
                        }
                        enviarDatosEstadoActividad(request, new EstadosActividadesBean());
                        break;
                    default:
                        enviarDatosEstadoActividad(request, new EstadosActividadesBean());
                        break;
                }
                if (listaEstadosActividades == null) {
                    listaEstadosActividades = estadosActividadesDao.consultarEstadosActividades();
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
            }
            request.setAttribute("estadosActividades", listaEstadosActividades);

        //}
//        List<EstadosVersionesBean> listaEstadosVersiones = null;
//        if (tipoEstado.equals(strConstEstadoVersion)) {
//            try {
//                switch (accion) {
//                    case "consultar":
//                        listaEstadosVersiones = estadosVersionesDao.consultarEstadosVersiones(idEstado, nombre);
//                        EstadosVersionesBean estadoVersion = new EstadosVersionesBean();
//                        estadoVersion.setId(idEstado);
//                        estadoVersion.setNombre(nombre);
//                        enviarDatosEstadoVersion(request, estadoVersion);
//                        break;
//                    case "editar":
//                        estadoVersion = new EstadosVersionesBean();
//                        if (id != null) {
//                            List<EstadosVersionesBean> estadosVersiones = estadosVersionesDao.consultarEstadosVersiones(id);
//                            if (estadosVersiones != null && !estadosVersiones.isEmpty()) {
//                                estadoVersion = estadosVersiones.get(0);
//                            }
//                        }
//                        enviarDatosEstadoVersion(request, estadoVersion);
//                        break;
//                    case "guardar":
//                        if (nombre != null) {
//                            estadoVersion = new EstadosVersionesBean();
//                            estadoVersion.setId(idEstado);
//                            estadoVersion.setNombre(nombre);
//                            int actualizacion = estadosVersionesDao.actualizarEstadoVersion(estadoVersion);
//                            if (actualizacion > 0) {
//                                mensajeExito = "El estado de versión ha sido guardado con éxito";
//                            } else {
//                                mensajeError = "El estado de versión no pudo ser guardado";
//                            }
//                        }
//                        enviarDatosEstadoVersion(request, new EstadosVersionesBean());
//                        break;
//                    case "eliminar":
//                        if (id != null) {
//                            int eliminacion = estadosVersionesDao.eliminarEstadoVersion(idEstado);
//                            if (eliminacion > 0) {
//                                mensajeExito = "El estado de versión fue eliminado con éxito";
//                            } else {
//                                mensajeError = "El estado de versión no pudo ser eliminado";
//                            }
//                        } else {
//                            mensajeError = "El estado de versión no pudo ser eliminado";
//                        }
//                        enviarDatosEstadoVersion(request, new EstadosVersionesBean());
//                        break;
//                    default:
//                        enviarDatosEstadoVersion(request, new EstadosVersionesBean());
//                        break;
//                }
//                if (listaEstadosVersiones == null) {
//                    listaEstadosVersiones = estadosVersionesDao.consultarEstadosVersiones();
//                }
//            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
//                Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
//                mensajeError = "Ocurrió un error procesando los datos. Revise el log de aplicación.";
//            }
//            request.setAttribute("estadosVersiones", listaEstadosVersiones);
//        }

        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        request.getRequestDispatcher("jsp/estados.jsp").forward(request, response);
    }

    private void enviarDatosEstadoActividad(HttpServletRequest request, EstadosActividadesBean estadoActividad) {
        request.setAttribute("id", estadoActividad.getId());
        request.setAttribute("nombre", estadoActividad.getNombre());
    }

    private void enviarDatosEstadoVersion(HttpServletRequest request, EstadosVersionesBean estadoVersion) {
        request.setAttribute("id", estadoVersion.getId());
        request.setAttribute("nombre", estadoVersion.getNombre());
    }

    @Override
    protected void doGet(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    @Override
    protected void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        processRequest(reqeust, response);
    }

    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
