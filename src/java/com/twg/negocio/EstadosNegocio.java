package com.twg.negocio;
import com.twg.controladores.EstadosController;
import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.daos.ActividadesDao;
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
        JSONObject jsonEstado = new JSONObject();
        List<EstadosBean> listaEstados = new ArrayList<>();
        if (id != null) {
            try {
                listaEstados = estadosDao.consultarEstados(id);
                if (listaEstados != null && !listaEstados.isEmpty()) {
                    EstadosBean estado = listaEstados.get(0);
                    jsonEstado.put("id", estado.getId());
                    jsonEstado.put("tipoEstado", estado.getTipo_estado());
                    jsonEstado.put("nombre", estado.getNombre());
                    jsonEstado.put("estadoPrev", estado.getEstadoPrev());
                    jsonEstado.put("estadoSig", estado.getEstadoSig());
                    jsonEstado.put("eFinal", estado.geteFinal() );
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jsonEstado;
    }

    public List<EstadosBean> consultarEstados(Integer id, String tipoEstado,String nombre, Integer estadoPrev, 
            Integer estadoSig, String eFinal) {
        List<EstadosBean> listaEstados = new ArrayList<>();
        try {
            listaEstados = estadosDao.consultarEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstados;
    }

    public Map<String, Object> crearEstado(Integer id, String tipoEstado, String nombre, Integer estadoPrev, 
            Integer estadoSig, String eFinal) {
        EstadosBean estadoBean = new EstadosBean();
        estadoBean.setTipo_estado(tipoEstado);        
        estadoBean.setNombre(nombre);
        estadoBean.setId(id);
        estadoBean.setEstadoPrev(estadoPrev);
        estadoBean.setEstadoSig(estadoSig);
        estadoBean.seteFinal(eFinal);

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
                ActividadesDao act = new ActividadesDao();
                List<ActividadesBean> actList = act.consultarActiv2(null, null, null, null, id, null);
                List<EstadosBean> estList = estadosDao.consultarEstadosPS(id);
                if((actList!=null && actList.size()>0) || (estList!=null && estList.size()>0)){
                    mensajeError = "El estado no puede ser eliminado porque ya tiene actividades asociadas o está ligado a "
                            + "otro estado.";
                }else{
                    int eliminacion = estadosDao.eliminarEstado(id);
                    if (eliminacion > 0) {
                        mensajeExito = "El estado fue eliminado con éxito";
                    } else {
                        mensajeError = "El estado no pudo ser eliminado";
                    }
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
        if (estado.getTipo_estado() == null || estado.getTipo_estado().equals("0") || estado.getTipo_estado().isEmpty()) {
            error += "El campo 'Tipo de Estado' es obligatorio <br/>";
        }
        if (estado.getNombre() == null || estado.getNombre().isEmpty()) {
            error += "El campo 'Nombre' es obligatorio <br/>";
        }
        if (estado.getEstadoPrev() != null && estado.getEstadoPrev().intValue() != 0 && estado.getEstadoSig() != null && 
                estado.getEstadoSig().intValue() != 0){
            if( estado.getEstadoPrev().intValue() == estado.getEstadoSig().intValue()){
                error += "El estado previo y siguiente no pueden ser iguales <br/>";
            }
        }
        if(estado.geteFinal() != null && estado.geteFinal().equals("T")){
            List<EstadosBean> ef = new ArrayList<>();
            try {
                if(estado.getTipo_estado() != null && estado.getTipo_estado().equals("ACTIVIDADES")){
                    ef = estadosDao.consultarEstados(null, "ACTIVIDADES", null, null, null, "T");
                }else{
                    ef = estadosDao.consultarEstados(null, "VERSIONES", null, null, null, "T");
                }
                
                if(ef != null && ef.size() > 0){
                    error += "Sólo puede existir un estado 'Final' en cada tipo <br/>";
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                error += " Ocurrió un error guardando el estado. Revise el log de aplicación. <br/>";
            }
        }

        return error;
    }
}
