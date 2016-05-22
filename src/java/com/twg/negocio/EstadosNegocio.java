package com.twg.negocio;

import com.twg.persistencia.beans.AccionesAuditadas;
import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.ClasificacionAuditorias;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.daos.ActividadesDao;
import com.twg.persistencia.daos.EstadosDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EstadosNegocio {

    private final EstadosDao estadosDao = new EstadosDao();
    private final AuditoriasNegocio auditoria = new AuditoriasNegocio();

    public JSONObject consultarEstado(Integer id) {
        JSONObject jsonEstado = new JSONObject();
        List<EstadosBean> listaEstados = new ArrayList<>();
        if (id != null) {
            try {
                listaEstados = estadosDao.consultarEstados(id);
                if (listaEstados != null && !listaEstados.isEmpty()) {
                    EstadosBean estado = listaEstados.get(0);
                    jsonEstado.put("id", estado.getId());
                    jsonEstado.put("tipoEstado", estado.getTipoEstado());

                    JSONArray array = new JSONArray();
                    JSONArray arrayEstadoPrevio = new JSONArray();
                    JSONArray arrayEstadoSiguiente = new JSONArray();

                    List<EstadosBean> jsonlistaEstados = consultarEstados(null, estado.getTipoEstado(), null, null, null, null, null);
                    if (jsonlistaEstados != null && !jsonlistaEstados.isEmpty()) {
                        for (EstadosBean estadoBean : jsonlistaEstados) {
                            JSONObject object = new JSONObject();
                            object.put("id", estadoBean.getId());
                            object.put("nombre", estadoBean.getNombre());

                            if (Objects.equals(estadoBean.getId(), estado.getEstadoPrevio())) {
                                JSONObject objectEstadoPrevio = new JSONObject();
                                objectEstadoPrevio.put("estadoPrevioId", estadoBean.getId());
                                objectEstadoPrevio.put("estadoPrevioNombre", estadoBean.getNombre());
                                arrayEstadoPrevio.add(objectEstadoPrevio);
                            }

                            if (Objects.equals(estadoBean.getId(), estado.getEstadoSiguiente())) {
                                JSONObject objectEstadoSiguiente = new JSONObject();
                                objectEstadoSiguiente.put("estadoSiguienteId", estadoBean.getId());
                                objectEstadoSiguiente.put("estadoSiguienteNombre", estadoBean.getNombre());
                                arrayEstadoSiguiente.add(objectEstadoSiguiente);
                            }
                            array.add(object);
                        }
                    }

                    jsonEstado.put("nombre", estado.getNombre());
                    if (arrayEstadoPrevio.size() > 0) {
                        jsonEstado.put("estadoPrevio", arrayEstadoPrevio.get(0));
                    } else {
                        JSONObject objectEstadoPrevio = new JSONObject();
                        objectEstadoPrevio.put("estadoPrevioId", 0);
                        arrayEstadoPrevio.add(objectEstadoPrevio);
                        jsonEstado.put("estadoPrevio", arrayEstadoPrevio.get(0));
                    }
                    if (arrayEstadoSiguiente.size() > 0) {
                        jsonEstado.put("estadoSiguiente", arrayEstadoSiguiente.get(0));
                    } else {
                        JSONObject objectEstadoSiguiente = new JSONObject();
                        objectEstadoSiguiente.put("estadoSiguienteId", 0);
                        arrayEstadoSiguiente.add(objectEstadoSiguiente);
                        jsonEstado.put("estadoSiguiente", arrayEstadoSiguiente.get(0));
                    }
                    jsonEstado.put("estadoPrev", array);
                    jsonEstado.put("estadoSig", array);
                    jsonEstado.put("eFinal", estado.getEstadoFinal());
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jsonEstado;
    }

        /**
     * Método encargado de contar la cantidad total de registros que se
     * encuentran en base de datos con base en los filtros ingresados
     *
     * @param id
     * @param tipoEstado
     * @param nombre
     * @param estadoPrev
     * @param estadoSig
     * @param eFinal
     * @return
     */
    public int cantidadEstados(Integer id, String tipoEstado, String nombre, Integer estadoPrev, Integer estadoSig, String eFinal) {
        int cantidadEstados = 0;
        try {
            cantidadEstados = estadosDao.cantidadEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadEstados;
    }
    
    public List<EstadosBean> consultarEstados(Integer id, String tipoEstado, String nombre, Integer estadoPrev, Integer estadoSig, String eFinal, String limite) {
        List<EstadosBean> listaEstados = new ArrayList<>();
        try {
            listaEstados = estadosDao.consultarEstados(id, tipoEstado, nombre, estadoPrev, estadoSig, eFinal, limite);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(TiposDocumentoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstados;
    }

    public Map<String, Object> crearEstado(Integer id, String tipoEstado, String nombre, Integer estadoPrev,
            Integer estadoSig, String eFinal, String personaSesionStr) {
        EstadosBean estadoBeanNuevo = new EstadosBean();
        estadoBeanNuevo.setTipoEstado(tipoEstado);
        estadoBeanNuevo.setNombre(nombre);
        estadoBeanNuevo.setId(id);
        estadoBeanNuevo.setEstadoPrevio(estadoPrev);
        estadoBeanNuevo.setEstadoSiguiente(estadoSig);
        estadoBeanNuevo.setEstadoFinal(eFinal);
        
        String estadoPrevioNuevo = "Ninguno";
        String estadoSiguienteNuevo = "Ninguno";
        try {
            if(estadoPrev!=null && estadoPrev!=0){
                List<EstadosBean> estadoPrevio = estadosDao.consultarEstados(estadoPrev);
                estadoPrevioNuevo = estadoPrevio.get(0).getNombre();
            }
            if(estadoSig!=null && estadoSig!=0){
                List<EstadosBean> estadoSiguiente = estadosDao.consultarEstados(estadoSig);
                estadoSiguienteNuevo = estadoSiguiente.get(0).getNombre();
            }
        } catch (Exception e) {
        }
        
        Integer personaSesion = null;
        try {
            personaSesion = Integer.parseInt(personaSesionStr);
        } catch (Exception e) {
        }
        
        String mensajeExito = "";
        String mensajeError = validarDatos(estadoBeanNuevo);
        if (mensajeError.isEmpty()) {
            try {
                if (id != null) {
                    List<EstadosBean> estadoBeanAntes = estadosDao.consultarEstados(id);
                    String estadoPrevioAntes = "Ninguno";
                    String estadoSiguienteAntes = "Ninguno";
                    try {
                        if(estadoPrev!=null && estadoPrev!=0){
                            List<EstadosBean> estadoPrevio = estadosDao.consultarEstados(estadoBeanAntes.get(0).getEstadoPrevio());
                            estadoPrevioAntes = estadoPrevio.get(0).getNombre();
                        }
                        if(estadoSig!=null && estadoSig!=0){
                            List<EstadosBean> estadoSiguiente = estadosDao.consultarEstados(estadoBeanAntes.get(0).getEstadoSiguiente());
                            estadoSiguienteAntes = estadoSiguiente.get(0).getNombre();
                        }
                    } catch (Exception e) {
                    }
                    int actualizacion = estadosDao.actualizarEstado(estadoBeanNuevo);
                    if (actualizacion > 0) {
                        mensajeExito = "El estado ha sido modificado con éxito.";
                        //AUDITORIA
                        try {
                            String descripcioAudit = "Se modificó un estado. Antes ("+
                                    " Tipo: "+estadoBeanAntes.get(0).getTipoEstado()+
                                    " Nombre: "+estadoBeanAntes.get(0).getNombre()+
                                    " Previo: "+estadoPrevioAntes+
                                    " Siguiente: "+estadoSiguienteAntes+
                                    " Final: "+(estadoBeanAntes.get(0).getEstadoFinal().equals("T")?"Sí":"No")+
                                    ") Después (Tipo: "+estadoBeanNuevo.getTipoEstado()+
                                    " Nombre: "+estadoBeanNuevo.getNombre()+
                                    " Previo: "+estadoPrevioNuevo+
                                    " Siguiente: "+estadoSiguienteNuevo+
                                    " Final: "+(estadoBeanNuevo.getEstadoFinal().equals("T")?"Sí":"No")+")";
                            String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.ESTADO.getNombre(), AccionesAuditadas.EDICION.getNombre(), descripcioAudit);
                        } catch (Exception e) {
                            Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, e);
                        }
                    } else {
                        mensajeError = "El estado no pudo ser modificado";
                    }
                } else {
                    
                    if (id == null) {
                        int actualizacion = estadosDao.insertarEstado(estadoBeanNuevo);
                        if (actualizacion > 0) {
                            mensajeExito = "El estado ha sido guardado con éxito";
                            //AUDITORIA
                            try {
                                String descripcioAudit = "Se insertó un registro en Estados ("+
                                        " Tipo: "+estadoBeanNuevo.getTipoEstado()+
                                        " Nombre: "+estadoBeanNuevo.getNombre()+
                                        " Previo: "+estadoPrevioNuevo+
                                        " Siguiente: "+estadoSiguienteNuevo+
                                        " Final: "+(estadoBeanNuevo.getEstadoFinal().equals("T")?"Sí":"No")+")";
                                String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.ESTADO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                            } catch (Exception e) {
                                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, e);
                            }
                        } else {
                            mensajeError = "El estado no pudo ser guardado";
                        }
                    } else {

                        List<EstadosBean> existente = estadosDao.consultarEstados(id);
                        if (existente != null && !existente.isEmpty()) {
                            mensajeError = "El estado esta siendo utilizado";
                        } else {
                            estadoBeanNuevo.setId(id);
                            int insercion = estadosDao.insertarEstado(estadoBeanNuevo);
                            if (insercion > 0) {
                                mensajeExito = "El estado ha sido guardado con éxito";
                                //AUDITORIA
                                try {
                                    String descripcioAudit = "Se insertó un registro en Estados (Tipo: "+estadoBeanNuevo.getTipoEstado()+" Nombre: "+estadoBeanNuevo.getNombre()+" Previo: "+estadoPrevioNuevo
                                        +" Siguiente: "+estadoSiguienteNuevo+" Final: "+(estadoBeanNuevo.getEstadoFinal().equals("T")?"Sí":"No")+")";
                                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.ESTADO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                                } catch (Exception e) {
                                    Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, e);
                                }
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

    public Map<String, Object> eliminarEstado(Integer id, String personaSesionStr) {
        String mensajeExito = "";
        String mensajeError = "";
        
        Integer personaSesion = null;
        try {
            personaSesion = Integer.parseInt(personaSesionStr);
        } catch (Exception e) {
        }
        
        if (id != null) {
            try {
                ActividadesDao act = new ActividadesDao();
                List<ActividadesBean> listaActividades = act.consultarActividades(null, null, null, null, null, id, null, null);
                List<EstadosBean> estList = estadosDao.consultarEstadosPS(id);
                if ((listaActividades != null && listaActividades.size() > 0) || (estList != null && estList.size() > 0)) {
                    mensajeError = "El estado no puede ser eliminado porque ya tiene actividades asociadas o está ligado a "
                            + "otro estado.";
                } else {
                    List<EstadosBean> est = estadosDao.consultarEstados(id);
                    int eliminacion = estadosDao.eliminarEstado(id);
                    if (eliminacion > 0) {
                        mensajeExito = "El estado fue eliminado con éxito";
                        //AUDITORIA
                        try {
                            String descripcioAudit = "Se eliminó el estado llamado "+est.get(0).getNombre()+" de tipo "+est.get(0).getTipoEstado();
                            String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.ESTADO.getNombre(), AccionesAuditadas.ELIMINACION.getNombre(), descripcioAudit);
                        } catch (Exception e) {
                            Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, e);
                        }
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
        if (estado.getTipoEstado() == null || estado.getTipoEstado().equals("0") || estado.getTipoEstado().isEmpty()) {
            error += "El campo 'Tipo de Estado' es obligatorio <br/>";
        }
        if (estado.getNombre() == null || estado.getNombre().isEmpty()) {
            error += "El campo 'Nombre' es obligatorio <br/>";
        } else {
            if (estado.getNombre().length() > 30) {
                error += "El campo 'Nombre' no debe contener más de 15 caracteres, has dígitado " + estado.getNombre().length() + " caracteres <br />";
            }
        }
        if (estado.getEstadoPrevio() != null && estado.getEstadoPrevio().intValue() != 0 && estado.getEstadoSiguiente() != null
                && estado.getEstadoSiguiente().intValue() != 0) {
            if (estado.getEstadoPrevio().intValue() == estado.getEstadoSiguiente().intValue()) {
                error += "El estado previo y siguiente no pueden ser iguales <br/>";
            }
        }
        if (estado.getEstadoFinal() != null && estado.getEstadoFinal().equals("T")) {
            List<EstadosBean> ef = new ArrayList<>();
            try {
                //Se consulta el estado anterior en caso de que estén haceindo un update para calidar si cambió el campo Estado Final.
                ef = estadosDao.consultarEstados(estado.getId(), null, null, null, null, null, null);
                /*
                 Si el estado final es el mismo, no hay que hacer la validación para que no exista más de un estado marcado como final.
                 Per si es diferente, sí hay que validar
                 */
                if (ef != null && ef.size() > 0 && !ef.get(0).getEstadoFinal().equals(estado.getEstadoFinal())) {
                    if (estado.getTipoEstado() != null && estado.getTipoEstado().equals("ACTIVIDADES")) {
                        ef = estadosDao.consultarEstados(null, "ACTIVIDADES", null, null, null, "T", null);
                    } else {
                        ef = estadosDao.consultarEstados(null, "VERSIONES", null, null, null, "T", null);
                    }

                    if (ef != null && ef.size() > 0) {
                        error += "Sólo puede existir un estado 'Final' en cada tipo <br/>";
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                error += " Ocurrió un error guardando el estado. Revise el log de aplicación. <br/>";
            }
        }

        return error;
    }
}
