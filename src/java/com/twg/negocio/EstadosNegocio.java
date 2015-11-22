package com.twg.negocio;
import com.twg.controladores.EstadosController;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.daos.EstadosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class EstadosNegocio {

    private final EstadosDao estadosDao = new EstadosDao();

    public JSONObject consultarEstado(Integer id) {
        JSONObject jsonUsuario = new JSONObject();
        if (id != null) {
            try {
                List<EstadosBean> listaEstados = estadosDao.consultarEstados(id);
                if (listaEstados != null && !listaEstados.isEmpty()) {
                    EstadosBean estado = listaEstados.get(0);
                    jsonUsuario.put("id", estado.getId());
                    jsonUsuario.put("nombre", estado.getNombre());
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jsonUsuario;
    }

    public List<EstadosBean> consultarEstados(Integer id, String nombre) {
        List<EstadosBean> listaEstados = new ArrayList<>();
        try {
            listaEstados = estadosDao.consultarEstados(id, nombre);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstados;
    }

    public Map<String, Object> crearEstado(Integer id, String nombre) {
        EstadosBean estadoBean = new EstadosBean();
        estadoBean.setNombre(nombre);
        estadoBean.setId(id);

        String mensajeExito = "";
        String mensajeError = validarDatos(estadoBean);
        if (mensajeError.isEmpty()) {
            try {
                if (id != null) {
                    int actualizacion = estadosDao.actualizarEstado(estadoBean);
                    if (actualizacion > 0) {
                        mensajeExito = "El estado ha sido modificado con éxito";
                    } else {
                        mensajeError = "El estado no pudo ser modificado";
                    }
                } else {
                    if (id == null) {
                        int actualizacion = estadosDao.insertarEstado(estadoBean);
                        if (actualizacion > 0) {
                            mensajeExito = "El estado ha sido guardado con éxito";
                        } else {
                            mensajeError = "El estado no pudo ser guardado";
                        }
                    } else {

                        List<EstadosBean> existente = estadosDao.consultarEstados(id);
                        if (existente != null && !existente.isEmpty()) {
                            mensajeError = "El estado esta siendo utilizado";
                        } else {
                            estadoBean.setId(id);
                            int insercion = estadosDao.insertarEstado(estadoBean);
                            if (insercion > 0) {
                                mensajeExito = "El estado ha sido guardado con éxito";
                            } else {
                                mensajeError = "El estado no pudo ser guardado";
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = "Ocurrió un error insertando el estado. Revise el log de aplicación.";
            }
        }
        Map<String, Object> result = new HashMap<>();
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    public Map<String, Object> eliminarEstado(Integer id) {
        String mensajeExito = "";
        String mensajeError = "";
        if (id != null) {
            try {
                int eliminacion = estadosDao.eliminarEstado(id);
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
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    private String validarDatos(EstadosBean estado) {
        String error = "";
        if (estado.getNombre() == null || estado.getNombre().isEmpty()) {
            error += "El campo 'Nombre' es obligatorio <br/>";
        }
//        try {
//            List<EstadosBean> lstEstados = estadosDao.consultarEstados(estado.getNombre());
//            if (lstEstados != null && !lstEstados.isEmpty()) {
//                if (estado.getId() != null) {
//                    if (estado.getId().intValue() != lstEstados.get(0).getId().intValue()) {
//                        error += "El estado a ingresar no está disponible <br/>";
//                    }
//                } else {
//                    error += "El estado a ingresar no está disponible <br/>";
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
//            Logger.getLogger(EstadosController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return error;
    }
}
