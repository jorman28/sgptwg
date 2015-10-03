package com.twg.negocio;

import com.twg.controladores.EstadosController;
import com.twg.persistencia.beans.EstadosActividadesBean;
import com.twg.persistencia.beans.EstadosVersionesBean;
import com.twg.persistencia.daos.EstadosActividadesDao;
import com.twg.persistencia.daos.EstadosVersionesDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class EstadosNegocio {
    private final EstadosActividadesDao estadosActividadesDao = new EstadosActividadesDao();
    private final EstadosVersionesDao estadosVersionesDao = new EstadosVersionesDao();
    
    public JSONObject consultarEstado(Integer id){
        JSONObject jsonUsuario = new JSONObject();
        if (id != null) {
            try {
                List<EstadosActividadesBean> listaEstadosActividades = estadosActividadesDao.consultarEstadosActividades(id);
                if (listaEstadosActividades != null && !listaEstadosActividades.isEmpty()) {
                    EstadosActividadesBean estadoActividad = listaEstadosActividades.get(0);
                    jsonUsuario.put("id", estadoActividad.getId());
                    jsonUsuario.put("nombre", estadoActividad.getNombre());
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jsonUsuario;
    }
    
    public List<EstadosActividadesBean> consultarEstados(Integer id, String nombre){
        List<EstadosActividadesBean> listaEstadosActividades = new ArrayList<>();
        try {
            listaEstadosActividades = estadosActividadesDao.consultarEstadosActividades(id, nombre);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstadosActividades;
    }
    
    public Map<String, Object> crearEstado(Integer id, String nombre){
        EstadosActividadesBean estadoActividadBean = new EstadosActividadesBean();
        estadoActividadBean.setNombre(nombre);
        estadoActividadBean.setId(id);
        
        String mensajeExito = "";
        String mensajeError = validarDatos(estadoActividadBean);
        if (mensajeError.isEmpty()) {
            try {
                if (id != null) {
                    int actualizacion = estadosActividadesDao.actualizarEstadoActividad(estadoActividadBean);
                    if (actualizacion > 0) {
                        mensajeExito = "El estado ha sido guardado con éxito";
                    } else {
                        mensajeError = "El estado no pudo ser guardado";
                    }
                } else {
                    id = estadosActividadesDao.consultarId(nombre);
                    if (id != null) {
                        List<EstadosActividadesBean> existente = estadosActividadesDao.consultarEstadosActividades(id);
                        if (existente != null && !existente.isEmpty()) {
                            mensajeError = "El estado esta siendo utilizado";
                        } else {
                            estadoActividadBean.setId(id);
                            int insercion = estadosActividadesDao.insertarEstadoActividad(estadoActividadBean);
                            if (insercion > 0) {
                                mensajeExito = "El estado ha sido guardado con éxito";
                            } else {
                                mensajeError = "El estado no pudo ser guardado";
                            }
                        }

                    } else {
                        mensajeError = "La persona seleccionada no está registrada en el sistema";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error insertando el estado. Revise el log de aplicación.";
            }
        }
        Map<String, Object> result = new HashMap<>();
        if(!mensajeError.isEmpty()){
            result.put("mensajeError", mensajeError);
        }
        if(!mensajeExito.isEmpty()){
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }
    
    public Map<String, Object> eliminarEstado(Integer id){
        String mensajeExito = "";
        String mensajeError = "";
        if (id != null) {
            try {
                int eliminacion = estadosActividadesDao.eliminarEstadoActividad(id);
                if (eliminacion > 0) {
                    mensajeExito = "El estado fue eliminado con éxito";
                } else {
                    mensajeError = "El estado no pudo ser eliminado";
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error eliminando el estado. Revise el log de aplicación.";
            }
        } else {
            mensajeError = "El estado no pudo ser eliminado";
        }
        Map<String, Object> result = new HashMap<>();
        if(!mensajeError.isEmpty()){
            result.put("mensajeError", mensajeError);
        }
        if(!mensajeExito.isEmpty()){
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }
    
    private String validarDatos(EstadosActividadesBean estadoActividad) {
        String error = "";
        if (estadoActividad.getNombre()== null || estadoActividad.getNombre().isEmpty()) {
            error += "El campo 'Nombre' es obligatorio <br/>";
        }
            try {
                List<EstadosActividadesBean> lstEstadosActividades = estadosActividadesDao.consultarEstadosActividades(estadoActividad.getNombre());
                if (lstEstadosActividades != null && !lstEstadosActividades.isEmpty()) {
                    if (estadoActividad.getId() != null) {
                        if (estadoActividad.getId().intValue() != lstEstadosActividades.get(0).getId().intValue()) {
                            error += "El usuario a ingresar no está disponible <br/>";
                        }
                    } else {
                        error += "El usuario a ingresar no está disponible <br/>";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return error;
    }
}
