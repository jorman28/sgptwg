package com.twg.negocio;

import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.ProyectosBean;
import com.twg.persistencia.daos.ProyectosDao;
import com.twg.persistencia.daos.VersionesDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Pipe
 */
public class ProyectosNegocio {

    private final ProyectosDao proyectosDao = new ProyectosDao();
    private final VersionesDao versionesDao = new VersionesDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String guardarProyecto(String id, String nombre, String fechaInicio) {
        String error = "";
        ProyectosBean proyecto = new ProyectosBean();
        proyecto.setNombre(nombre);
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

    public List<ProyectosBean> consultarProyectos(Integer id) {
        List<ProyectosBean> listaProyectos = new ArrayList<>();
        try {
            listaProyectos = proyectosDao.consultarProyectos(id);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProyectos;
    }

    public JSONObject consultarProyecto(Integer idProyecto) {
        JSONObject object = new JSONObject();
        List<ProyectosBean> listaProyectos = consultarProyectos(idProyecto);
        if (listaProyectos != null && !listaProyectos.isEmpty()) {
            object.put("idProyecto", listaProyectos.get(0).getId());
            object.put("nombreProyecto", listaProyectos.get(0).getNombre());
            object.put("fechaInicio", listaProyectos.get(0).getFechaInicio() != null ? sdf.format(listaProyectos.get(0).getFechaInicio()) : "");
            List<PersonasBean> personasProyecto = null;
            try {
                personasProyecto = proyectosDao.consultarPersonasProyecto(idProyecto);
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONArray clientes = new JSONArray();
            JSONArray empleados = new JSONArray();
            if (personasProyecto != null && !personasProyecto.isEmpty()) {
                for (PersonasBean persona : personasProyecto) {
                    JSONObject objetoPersona = new JSONObject();
                    objetoPersona.put("id", persona.getId());
                    objetoPersona.put("nombre", persona.getTipoDocumento() + persona.getDocumento() + " " + persona.getNombres() + " " + persona.getApellidos());
                    objetoPersona.put("cargo", persona.getNombreCargo().equalsIgnoreCase("Cliente") ? "Cliente" : persona.getNombreCargo());
                    if (persona.getNombreCargo().equalsIgnoreCase("Cliente")) {
                        clientes.add(objetoPersona);
                    } else {
                        empleados.add(objetoPersona);
                    }
                }
            }
            if (!clientes.isEmpty()) {
                object.put("clientes", clientes);
            }
            if (!empleados.isEmpty()) {
                object.put("empleados", empleados);
            }
        }
        return object;
    }

    public String eliminarProyecto(Integer idProyecto) {
        String error = "";
        try {
            int eliminacion = proyectosDao.eliminarProyecto(idProyecto);
            if (eliminacion == 0) {
                error = "El proyecto no pudo ser eliminado";
            }
            versionesDao.eliminarVersion(null, idProyecto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el proyecto";
        }
        return error;
    }
}
