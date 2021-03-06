package com.twg.negocio;

import com.twg.persistencia.beans.AccionesAuditadas;
import com.twg.persistencia.beans.ClasificacionAuditorias;
import com.twg.persistencia.beans.PersonasBean;
import com.twg.persistencia.beans.ProyectosBean;
import com.twg.persistencia.daos.ProyectosDao;
import com.twg.persistencia.daos.VersionesDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos, para la tabla de proyectos.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ProyectosNegocio {

    private final ProyectosDao proyectosDao = new ProyectosDao();
    private final VersionesDao versionesDao = new VersionesDao();
    private final AuditoriasNegocio auditoria = new AuditoriasNegocio();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de guardar o actualizar la información de un proyecto.
     *
     * @param id
     * @param nombre
     * @param fechaInicio
     * @param idPersonas
     * @param personaSesion
     * @return Cadena con un mensaje de error en caso de que el proceso falle.
     */
    public String guardarProyecto(String id, String nombre, String fechaInicio, String[] idPersonas, Integer personaSesion) {
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
                List<ProyectosBean> proyectoAntes = proyectosDao.consultarProyectos(idProyecto, null, false, null);
                guardado = proyectosDao.actualizarProyecto(proyecto);
                //AUDITORIA
                try {
                    String descripcioAudit = "Se actualizó la información de un proyecto. ANTES ("
                            + " Nombre: " + proyectoAntes.get(0).getNombre()
                            + ", Fecha inicio: " + proyectoAntes.get(0).getFechaInicio()
                            + ") DESPUÉS ( Nombre: " + proyecto.getNombre()
                            + ", Fecha inicio: " + proyecto.getFechaInicio() + ")";
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PROYECTO.getNombre(), AccionesAuditadas.EDICION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                guardado = proyectosDao.crearProyecto(proyecto);
                //AUDITORIA
                try {
                    String descripcioAudit = "Se creó un proyecto con la siguiente información ("
                            + " Nombre: " + proyecto.getNombre()
                            + ", Fecha inicio: " + proyecto.getFechaInicio() + ")";
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PROYECTO.getNombre(), AccionesAuditadas.CREACION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (guardado == 0) {
                error += "El proyecto no pudo ser guardado";
            } else if (idPersonas != null && idPersonas.length > 0) {
                if (idProyecto == null) {
                    List<ProyectosBean> listaProyectos = consultarProyectos(null, nombre, true, null);
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

    /**
     * Método encargado de validar los datos necesarios para poder guardar un
     * proyecto.
     *
     * @param idProyecto
     * @param nombre
     * @param fechaInicio
     * @return Cadena con un mensaje de error en caso de que no cumpla alguna
     * validación.
     */
    public String validarDatos(Integer idProyecto, String nombre, String fechaInicio) {
        String validacion = "";
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío <br />";
        } else if (nombre.length() > 30) {
            validacion += "El campo 'Nombre' no debe contener más de 30 caracteres, has dígitado " + nombre.length() + " caracteres <br />";
        } else {
            List<ProyectosBean> listaProyectos = consultarProyectos(null, nombre, true, null);
            if (listaProyectos != null && !listaProyectos.isEmpty()) {
                if (idProyecto == null || idProyecto.intValue() != listaProyectos.get(0).getId().intValue()) {
                    validacion += "El valor ingresado en el campo 'Nombre' ya existe en el sistema \n";
                }
            }
        }
        if (fechaInicio == null || fechaInicio.isEmpty()) {
            validacion += "El campo 'Fecha de inicio' no debe estar vacío <br />";
        } else {
            try {
                Date fechaInicioProyecto = sdf.parse(fechaInicio);
                if (idProyecto != null) {
                    try {
                        if (versionesDao.versionesPorFecha(idProyecto, fechaInicioProyecto)) {
                            validacion += "La fecha de inicio del proyecto es mayor que alguna de sus versiones <br />";
                        }
                    } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                        Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha de inicio' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }
        return validacion;
    }

    /**
     * Método encargado de consultar los proyectos según los parámetros de
     * búsqueda.
     *
     * @param id
     * @param nombre
     * @param nombreExacto
     * @return Listado de proyectos según los parámetros de búsqueda.
     */
    public List<ProyectosBean> consultarProyectos(Integer id, String nombre, boolean nombreExacto, Integer idPersona) {
        List<ProyectosBean> listaProyectos = new ArrayList<>();
        try {
            listaProyectos = proyectosDao.consultarProyectos(id, nombre, nombreExacto, idPersona);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProyectos;
    }

    /**
     * Método encargado de consultar un proyecto específico.
     *
     * @param idProyecto
     * @return Objeto con todos los atributos de un proyecto.
     */
    public JSONObject consultarProyecto(Integer idProyecto) {
        JSONObject object = new JSONObject();
        List<ProyectosBean> listaProyectos = consultarProyectos(idProyecto, null, false, null);
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

    /**
     * Método para encontrar los proyectos de una versión determinada.
     *
     * @param idVersion
     * @return Objeto con todos los atributos de un proyecto.
     */
    public ProyectosBean consultarProyectoPorVersion(Integer idVersion) {
        ProyectosBean proyBean = new ProyectosBean();
        if (idVersion != null) {
            try {
                List<ProyectosBean> listaProyectos = proyectosDao.consultarProyectosPorVersion(idVersion);
                if (listaProyectos != null && !listaProyectos.isEmpty()) {
                    ProyectosBean proyectoBean = listaProyectos.get(0);
                    proyBean = proyectoBean;
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(EstadosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proyBean;
    }

    /**
     * Método encargado de eliminar un proyecto específico.
     *
     * @param idProyecto
     * @param personaSesion
     * @return Cadena con un mensaje de error en caso de que el proceso falle.
     */
    public String eliminarProyecto(Integer idProyecto, Integer personaSesion) {
        String error = "";
        try {
            List<ProyectosBean> proyectoEliminar = proyectosDao.consultarProyectos(idProyecto, null, false, null);
            int eliminacion = proyectosDao.eliminarProyecto(idProyecto);
            if (eliminacion == 0) {
                error = "El proyecto no pudo ser eliminado";
            } else {
                //AUDITORIA
                try {
                    String descripcioAudit = "Se eliminó el proyecto llamado " + proyectoEliminar.get(0).getNombre();
                    String guardarAuditoria = auditoria.guardarAuditoria(personaSesion, ClasificacionAuditorias.PROYECTO.getNombre(), AccionesAuditadas.ELIMINACION.getNombre(), descripcioAudit);
                } catch (Exception e) {
                    Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            versionesDao.eliminarVersion(null, idProyecto);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ProyectosNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el proyecto";
        }
        return error;
    }
}
