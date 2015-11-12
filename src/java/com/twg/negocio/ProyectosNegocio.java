package com.twg.negocio;

import com.twg.persistencia.beans.ProyectosBean;
import com.twg.persistencia.daos.ProyectosDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pipe
 */
public class ProyectosNegocio {

    private final ProyectosDao proyectosDao = new ProyectosDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String guardarProyecto(String id, String nombre, String fechaInicio, String responsable) {
        String error = "";
        ProyectosBean proyecto = new ProyectosBean();
        proyecto.setNombre(nombre);
        proyecto.setIdPersona(Integer.valueOf(responsable));
        try {
            proyecto.setFechaInicio(sdf.parse(fechaInicio));
        } catch (ParseException ex) {
        }
        try {
            int guardado = 0;
            if (id != null && !id.isEmpty()) {
                proyecto.setId(Integer.valueOf(id));
                guardado = proyectosDao.actualizarProyecto(proyecto);
            } else {
                guardado = proyectosDao.crearProyecto(proyecto);
            }
            if (guardado == 0) {
                error += "El proyecto no pudo ser guardado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "El proyecto no pudo ser guardado";
        }
        return error;
    }

    public String validarDatos(String nombre, String fechaInicio, String responsable) {
        String validacion = "";
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío \n";
        }
        if (responsable == null || responsable.isEmpty()) {
            validacion += "El campo 'Responsable' no debe estar vacío \n";
        } else {
            try {
                Integer.valueOf(responsable);
            } catch (NumberFormatException e) {
                validacion += "El valor ingresado en el campo 'Responsable' no corresponde al id de un cliente \n";
            }
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
}
