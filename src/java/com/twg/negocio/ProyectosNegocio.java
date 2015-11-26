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

    public String guardarProyecto(String id, String nombre, String fechaInicio, String[] idPersonas) {
        String error = "";
        ProyectosBean proyecto = new ProyectosBean();
        proyecto.setNombre(nombre);
        try {
            proyecto.setFechaInicio(sdf.parse(fechaInicio));
        } catch (ParseException ex) {
        }
        Integer idProyecto;
        try {
            idProyecto = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            idProyecto = null;
        }
        try {
            int guardado = 0;
            if (idProyecto != null) {
                proyecto.setId(idProyecto);
                guardado = proyectosDao.actualizarProyecto(proyecto);
            } else {
                guardado = proyectosDao.crearProyecto(proyecto);
            }
            if (guardado == 0) {
                error += "El proyecto no pudo ser guardado";
            } else if (idPersonas != null && idPersonas.length > 0) {
                if (idProyecto == null) {
                    List<ProyectosBean> listaProyectos = consultarProyectos(null, nombre, true);
                    if (listaProyectos != null && !listaProyectos.isEmpty()) {
                        idProyecto = listaProyectos.get(0).getId();
                    }
                }
                if (idProyecto != null) {
                    proyectosDao.eliminarPersonasProyecto(idProyecto);
                    for (String idPersona : idPersonas) {
                        try {
                            proyectosDao.insertarPersonaProyecto(idProyecto, Integer.valueOf(idPersona));
                        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException | NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error += "El proyecto no pudo ser guardado";
        }
        return error;
    }

    public String validarDatos(Integer idProyecto, String nombre, String fechaInicio) {
        String validacion = "";
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío \n";
        } else {
            List<ProyectosBean> listaProyectos = consultarProyectos(null, nombre, true);
            if (listaProyectos != null && !listaProyectos.isEmpty()) {
                if (idProyecto == null || idProyecto.intValue() != listaProyectos.get(0).getId().intValue()) {
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
        return validacion;
    }

    public List<ProyectosBean> consultarProyectos(Integer id, String nombre, boolean nombreExacto) {
        List<ProyectosBean> listaProyectos = new ArrayList<>();
        try {
            listaProyectos = proyectosDao.consultarProyectos(id, nombre, nombreExacto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProyectos;
    }

    public JSONObject consultarProyecto(Integer idProyecto) {
        JSONObject object = new JSONObject();
        List<ProyectosBean> listaProyectos = consultarProyectos(idProyecto, null, false);
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
