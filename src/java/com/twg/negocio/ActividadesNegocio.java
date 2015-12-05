package com.twg.negocio;

import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.Actividades_EmpleadosBean;
import com.twg.persistencia.daos.ActividadesDao;
import com.twg.persistencia.daos.Actividades_EmpleadosDao;
import com.twg.persistencia.daos.PersonasDao;
import com.twg.persistencia.daos.VersionesDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesNegocio {

    private final ActividadesDao actividadesDao = new ActividadesDao();
    private final VersionesDao versionesDao = new VersionesDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");    
    private final SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss.SSS");


    public String guardarActividad(String id, String version, String descripcion, String fecha_estimada_inicio, String fecha_estimada_terminacion, String fecha_real_inicio, String fecha_real_terminacion, String tiempo_estimado, String tiempo_invertido, String estado) {
        String error = "";
        ActividadesBean actividad = new ActividadesBean();
        
        actividad.setId(Integer.valueOf(id));        
        actividad.setVersion(Integer.valueOf(version));
        actividad.setDescripcion(descripcion);
        try {
            actividad.setFecha_estimada_inicio(sdf.parse(fecha_estimada_inicio));
        } catch (ParseException ex) {
        }
        try {
            actividad.setFecha_estimada_terminacion(sdf.parse(fecha_estimada_terminacion));
        } catch (ParseException ex) {
        }
        try {
            actividad.setFecha_real_inicio(sdf.parse(fecha_real_inicio));
        } catch (ParseException ex) {
        }
        try {
            actividad.setFecha_real_terminacion(sdf.parse(fecha_real_terminacion));
        } catch (ParseException ex) {
        }
        actividad.setTiempo_estimado(Integer.valueOf(tiempo_estimado));
        actividad.setTiempo_invertido(Integer.valueOf(tiempo_invertido));
        actividad.setEstado(Integer.valueOf(estado));
        try {
            int guardado = 0;
            if (id != null && !id.isEmpty()) {
                actividad.setId(Integer.valueOf(id));
                guardado = actividadesDao.actualizarActividad(actividad);
            } else {
                guardado = actividadesDao.crearActividad(actividad);
            }
            if (guardado == 0) {
                error += "La actividad no pudo ser guardada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "La actividad no pudo ser guardada";
        }
        return error;
    }

    public String validarDatos(String nombre, String fechaInicio) {
        String validacion = "";
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío \n";
        }
        if (fechaInicio == null || fechaInicio.isEmpty()) {
            validacion += "El campo 'Fecha de inicio' no debe estar vacío \n";
        } else {
            try {
                sdf.parse(fechaInicio);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha de inicio' no se encuentra en el formato 'día/mes/año' \n";
            }
        }
        return validacion;
    }

    public List<ActividadesBean> consultarActividades(Integer id, Integer version, String descripcion, Date fecha_estimada_inicio, Date fecha_estimada_terminacion, Date fecha_real_inicio, Date fecha_real_terminacion, Integer tiempo_estimado, Integer tiempo_invertido, Integer estado) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        try {
            listaActividades = actividadesDao.consultarActividades(id, version, descripcion, fecha_estimada_inicio, fecha_estimada_terminacion, fecha_real_inicio, fecha_real_terminacion, tiempo_estimado, tiempo_invertido, estado);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividades;
    }
    
    public List<ActividadesBean> consultarActividades2(Integer id, String versionStr, String descripcion, String fecha_estimada_inicio, 
            String fecha_estimada_terminacion, String fecha_real_inicio, String fecha_real_terminacion, String estadoStr, String responsable) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        try {
            String idsActividades="";
            if(responsable!=null && !responsable.equals("")){
                PersonasDao perDao = new PersonasDao();
                int persona = perDao.consultarIdPersona(responsable, null);
                Actividades_EmpleadosDao actiDao = new Actividades_EmpleadosDao();
                List<Actividades_EmpleadosBean> actiList = actiDao.consultarActividadesEmpleados(persona);
                
                for (Actividades_EmpleadosBean actiList1 : actiList) {
                    idsActividades += actiList1.getActividad()+",";
                }
                if(!idsActividades.equals("")){
                    idsActividades=idsActividades.substring(0, idsActividades.length()-1);
                }
            }

            Integer version = null;
            try {
                version = Integer.valueOf(versionStr);
            } catch (Exception e) {
            }
            Integer estado = null;
            try {
                estado = Integer.valueOf(estadoStr);
            } catch (Exception e) {
            }

            Date fecha_estimada_inicioD = null;
            Date fecha_estimada_terminacionD = null;
            Date fecha_real_inicioD = null;
            Date fecha_real_terminacionD = null;
            try {
                fecha_estimada_inicioD = sdf.parse(fecha_estimada_inicio);
                fecha_estimada_terminacionD = sdf.parse(fecha_estimada_terminacion);
                fecha_real_inicioD = sdf.parse(fecha_real_inicio);
                fecha_real_terminacionD = sdf.parse(fecha_real_terminacion);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            listaActividades = actividadesDao.consultarActividades2(idsActividades, version, descripcion, 
                fecha_estimada_inicioD, fecha_estimada_terminacionD, fecha_real_inicioD, fecha_real_terminacionD, estado, responsable);
            
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividades;
    }

    public JSONObject consultarActividad(Integer idActividad) {
        JSONObject object = new JSONObject();
        List<ActividadesBean> listaActividades = consultarActividades(idActividad, null, null, null, null, null, null, null, null, null);
        if (listaActividades != null && !listaActividades.isEmpty()) {
            object.put("id", listaActividades.get(0).getId());
            object.put("version", listaActividades.get(0).getVersion());
            object.put("descripcion", listaActividades.get(0).getDescripcion());
            object.put("fecha_estimada_inicio", listaActividades.get(0).getFecha_estimada_inicio()!= null ? sdf.format(listaActividades.get(0).getFecha_estimada_inicio()) : "");
            object.put("fecha_estimada_terminacion", listaActividades.get(0).getFecha_estimada_terminacion()!= null ? sdf.format(listaActividades.get(0).getFecha_estimada_terminacion()) : "");
            object.put("fecha_real_inicio", listaActividades.get(0).getFecha_real_inicio()!= null ? sdf.format(listaActividades.get(0).getFecha_real_inicio()) : "");
            object.put("fecha_real_terminacion", listaActividades.get(0).getFecha_real_terminacion()!= null ? sdf.format(listaActividades.get(0).getFecha_real_terminacion()) : "");
            object.put("tiempo_estimado", listaActividades.get(0).getTiempo_estimado()!= null ? stf.format(listaActividades.get(0).getTiempo_estimado()) : "");
            object.put("tiempo_invertido", listaActividades.get(0).getTiempo_invertido()!= null ? stf.format(listaActividades.get(0).getTiempo_invertido()) : "");
            object.put("estado", listaActividades.get(0).getEstado());
        }
        return object;
    }

    public String eliminarActividad(Integer idActividad) {
        String error = "";
        try {
            int eliminacion = actividadesDao.eliminarActividad(idActividad);
            if(eliminacion == 0){
                error = "La actividad no pudo ser eliminada";
            }
            //versionesDao.eliminarVersion(null, id);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando la actividad";
        }
        return error;
    }
}