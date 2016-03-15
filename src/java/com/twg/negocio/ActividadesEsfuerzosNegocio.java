package com.twg.negocio;

import com.twg.persistencia.beans.ActividadesEsfuerzosBean;
import com.twg.persistencia.daos.ActividadesEsfuerzosDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesEsfuerzosNegocio {

    private final ActividadesEsfuerzosDao actividadesEsfuerzosDao = new ActividadesEsfuerzosDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss.SSS");

    public String guardarActividadEsfuerzo(String id, String actividad, String empleado, String fecha, String tiempo, String descripcion) {
        String error = "";
        ActividadesEsfuerzosBean actividadEsfuerzo = new ActividadesEsfuerzosBean();

        actividadEsfuerzo.setActividad(Integer.valueOf(actividad));
        actividadEsfuerzo.setEmpleado(Integer.valueOf(empleado));

        try {
            actividadEsfuerzo.setFecha(sdf.parse(fecha));
        } catch (ParseException ex) {
        }
        actividadEsfuerzo.setTiempo(Double.parseDouble(tiempo));
        actividadEsfuerzo.setDescripcion(descripcion);

        try {
            int guardado = 0;
            if (id != null && !id.isEmpty()) {
                actividadEsfuerzo.setId(Integer.valueOf(id));
                guardado = actividadesEsfuerzosDao.actualizarActividadEsfuerzo(actividadEsfuerzo);
            } else {
                guardado = actividadesEsfuerzosDao.crearActividadEsfuerzo(actividadEsfuerzo);
            }
            if (guardado == 0) {
                error += "El registro no pudo ser guardado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesEsfuerzosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "El registro no pudo ser guardado";
        }
        return error;
    }

    public String validarDatos(String actividad, String empleado, String fecha, String tiempo, String descripcion) {
        String validacion = "";

        if (actividad == null || actividad.isEmpty()) {
            validacion += "El campo 'Actividad' no debe estar vacío \n";
        }

        if (empleado == null || empleado.isEmpty()) {
            validacion += "El campo 'Empleado' no debe estar vacío \n";
        }

        if (fecha == null || fecha.isEmpty()) {
            validacion += "El campo 'Fecha' no debe estar vacío \n";
        } else {
            try {
                sdf.parse(fecha);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha de inicio' no se encuentra en el formato 'día/mes/año' \n";
            }
        }

        if (tiempo == null || tiempo.isEmpty()) {
            validacion += "El campo 'Tiempo' no debe estar vacío \n";
        } else {
            try {
                sdf2.parse(tiempo);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Tiempo' no se encuentra en el formato 'horas/minutos/segundos' \n";
            }
        }

        if (descripcion == null || descripcion.isEmpty()) {
            validacion += "El campo 'Descripción' no debe estar vacío \n";
        }
        return validacion;
    }

    public List<ActividadesEsfuerzosBean> consultarActividadesEsfuerzo(Integer id) {
        List<ActividadesEsfuerzosBean> listaActividadesEsfuerzo = new ArrayList<>();
        try {
            listaActividadesEsfuerzo = actividadesEsfuerzosDao.consultarActividadesEsfuerzos(id, null, null, null, null, null);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesEsfuerzosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividadesEsfuerzo;
    }

    public JSONObject consultarActividadEsfuerzo(Integer id) {
        JSONObject object = new JSONObject();
        List<ActividadesEsfuerzosBean> listaActividadesEsfuerzo = consultarActividadesEsfuerzo(id);
        if (listaActividadesEsfuerzo != null && !listaActividadesEsfuerzo.isEmpty()) {
            object.put("id", listaActividadesEsfuerzo.get(0).getId());
            object.put("actividad", listaActividadesEsfuerzo.get(0).getActividad());
            object.put("empleado", listaActividadesEsfuerzo.get(0).getEmpleado());
            object.put("fecha", listaActividadesEsfuerzo.get(0).getFecha() != null ? sdf.format(listaActividadesEsfuerzo.get(0).getFecha()) : "");
            object.put("tiempo", listaActividadesEsfuerzo.get(0).getTiempo() != null ? sdf2.format(listaActividadesEsfuerzo.get(0).getTiempo()) : "");
            object.put("descripcion", listaActividadesEsfuerzo.get(0).getDescripcion());
        }
        return object;
    }

    public String eliminarActividadEsfuerzo(Integer id) {
        String error = "";
        try {
            int eliminacion = actividadesEsfuerzosDao.eliminarActividadEsfuerzo(id, null, null);
            if (eliminacion == 0) {
                error = "El registro no pudo ser eliminado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesEsfuerzosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el registro";
        }
        return error;
    }
}
