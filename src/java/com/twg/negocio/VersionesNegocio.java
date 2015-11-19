package com.twg.negocio;

import com.twg.persistencia.beans.VersionesBean;
import com.twg.persistencia.daos.VersionesDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pipe
 */
public class VersionesNegocio {

    private final VersionesDao versionesDao = new VersionesDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String guardarVersion(String id, String nombre, String fechaInicio, String fechaTerminacion, String alcance, String proyecto, String estado) {
        String error = "";
        VersionesBean version = new VersionesBean();
        version.setNombre(nombre);
        version.setAlcance(alcance);
        version.setProyecto(Integer.valueOf(proyecto));
        version.setEstado(Integer.valueOf(estado));
        try {
            version.setFechaInicio(sdf.parse(fechaInicio));
        } catch (ParseException ex) {
        }
        try {
            version.setFechaTerminacion(sdf.parse(fechaTerminacion));
        } catch (ParseException ex) {
        }
        try {
            int guardado = 0;
            if (id != null && !id.isEmpty()) {
                version.setId(Integer.valueOf(id));
                guardado = versionesDao.actualizarVersion(version);
            } else {
                guardado = versionesDao.crearVersion(version);
            }
            if (guardado == 0) {
                error += "La version no pudo ser guardada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(VersionesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "La version no pudo ser guardada";
        }
        return error;
    }

    public String validarDatos(String nombre, String fechaInicio, String fechaTerminacion, String alcance, String proyecto, String estado) {
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
        if (fechaTerminacion == null || fechaTerminacion.isEmpty()) {
            validacion += "El campo 'Fecha de fin' no debe estar vacío \n";
        } else {
            try {
                sdf.parse(fechaTerminacion);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha de fin' no se encuentra en el formato 'día/mes/año' \n";
            }
        }
        if (proyecto == null || proyecto.isEmpty()) {
            validacion += "El campo 'Proyecto' no debe estar vacío \n";
        } else {
            try {
                Integer.valueOf(proyecto);
            } catch (NumberFormatException e) {
                validacion += "El valor ingresado en el campo 'Proyecto' no corresponde al id de un proyecto \n";
            }
        }
        if (estado == null || estado.isEmpty()) {
            validacion += "El campo 'Estado' no debe estar vacío \n";
        } else {
            try {
                Integer.valueOf(estado);
            } catch (NumberFormatException e) {
                validacion += "El valor ingresado en el campo 'Estado' no corresponde al id de un estado \n";
            }
        }
        if (alcance == null || alcance.isEmpty()) {
            validacion += "El campo 'Alcance' no debe estar vacío \n";
        }
        return validacion;
    }
    
    public List<VersionesBean> consultarVersiones(Integer id, Integer idProyecto){
        List<VersionesBean> listaVersiones = new ArrayList<>();
        try {
            listaVersiones = versionesDao.consultarVersiones(id, idProyecto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(VersionesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVersiones;
    }
}
