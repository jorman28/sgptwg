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
import org.json.simple.JSONObject;

/**
 *
 * @author Pipe
 */
public class VersionesNegocio {

    private final VersionesDao versionesDao = new VersionesDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String guardarVersion(String id, String nombre, String fechaInicio, String fechaTerminacion, String alcance, String proyecto, String estado, String costo) {
        String error = "";
        VersionesBean version = new VersionesBean();
        version.setNombre(nombre);
        version.setAlcance(alcance);
        version.setProyecto(Integer.valueOf(proyecto));
        version.setEstado(Integer.valueOf(estado));
        if (costo != null) {
            version.setCosto(Double.valueOf(costo));
        }else{
            version.setCosto(0.0);
        }
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

    public String validarDatos(Integer idVersion, String nombre, String fechaInicio, String fechaTerminacion, String alcance, String proyecto, String estado) {
        String validacion = "";
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío \n";
        } else {
            List<VersionesBean> listaVersiones = consultarVersiones(null, null, nombre, true);
            if (listaVersiones != null && !listaVersiones.isEmpty()) {
                if (idVersion == null || idVersion.intValue() != listaVersiones.get(0).getId()) {
                    validacion += "El valor ingresado en el campo 'Nombre' ya existe en el sistema \n";
                }
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
        if (estado == null || estado.equals("0")) {
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

    public List<VersionesBean> consultarVersiones(Integer id, Integer idProyecto, String nombre, boolean nombreExacto) {
        List<VersionesBean> listaVersiones = new ArrayList<>();
        try {
            listaVersiones = versionesDao.consultarVersiones(id, idProyecto, nombre, nombreExacto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(VersionesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVersiones;
    }

    public JSONObject consultarVersion(Integer idVersion) {
        JSONObject object = new JSONObject();
        List<VersionesBean> listaVersiones = consultarVersiones(idVersion, null, null,false);
        if (listaVersiones != null && !listaVersiones.isEmpty()) {
            object.put("idVersion", listaVersiones.get(0).getId());
            object.put("idProyecto", listaVersiones.get(0).getProyecto());
            object.put("nombreVersion", listaVersiones.get(0).getNombre());
            object.put("estado", listaVersiones.get(0).getEstado());
            object.put("costo", listaVersiones.get(0).getCosto());
            object.put("fechaInicio", listaVersiones.get(0).getFechaInicio() != null ? sdf.format(listaVersiones.get(0).getFechaInicio()) : "");
            object.put("fechaFin", listaVersiones.get(0).getFechaTerminacion() != null ? sdf.format(listaVersiones.get(0).getFechaTerminacion()) : "");
            object.put("fechaProyecto", listaVersiones.get(0).getFechaInicioProyecto()!= null ? sdf.format(listaVersiones.get(0).getFechaInicioProyecto()) : "");
            object.put("alcance", listaVersiones.get(0).getAlcance());
        }
        return object;
    }

    public String eliminarVersion(Integer idVersion, Integer idProyecto) {
        String error = "";
        try {
            int eliminacion = versionesDao.eliminarVersion(idVersion, idProyecto);
            if (eliminacion == 0) {
                error = "La versión no pudo ser eliminada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando la versión";
        }
        return error;
    }
}
