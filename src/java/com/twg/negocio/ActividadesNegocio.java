package com.twg.negocio;

import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.ActividadesEmpleadosBean;
import com.twg.persistencia.beans.ActividadesEsfuerzosBean;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.daos.ActividadesDao;
import com.twg.persistencia.daos.ActividadesEmpleadosDao;
import com.twg.persistencia.daos.ActividadesEsfuerzosDao;
import com.twg.persistencia.daos.EstadosDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Clase encargada de realizar la conexión entre la vista y las operaciones en
 * base de datos para la tabla de actividades.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesNegocio {

    private final ActividadesDao actividadesDao = new ActividadesDao();
    private final EstadosDao estadosDao = new EstadosDao();
    private final ActividadesEmpleadosDao actividadesEmpleadosDao = new ActividadesEmpleadosDao();
    private final ActividadesEsfuerzosDao actividadesEsfuerzosDao = new ActividadesEsfuerzosDao();

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de guardar un registro de actividad en la base de datos
     *
     * @param idActividad
     * @param idProyecto
     * @param idVersion
     * @param nombre
     * @param descripcion
     * @param idEstado
     * @return
     */
    public Map<String, Object> guardarActividad(Integer idActividad, Integer idProyecto, Integer idVersion, String nombre, String descripcion, Integer idEstado) {
        Map<String, Object> result = new HashMap<>();
        String mensajeExito = "";
        String mensajeError = validarDatos(idActividad, idProyecto, idVersion, nombre, descripcion, idEstado);
        if (mensajeError.isEmpty()) {
            try {
                int guardado;
                ActividadesBean actividad = new ActividadesBean();
                actividad.setVersion(idVersion);
                actividad.setNombre(nombre);
                actividad.setDescripcion(descripcion);
                actividad.setEstado(idEstado);
                if (idActividad != null) {
                    actividad.setId(idActividad);
                    result.put("idActividad", idActividad);
                    guardado = actividadesDao.actualizarActividad(actividad);
                } else {
                    guardado = actividadesDao.crearActividad(actividad);
                    if (guardado != 0) {
                        result.put("idActividad", actividadesDao.consultarUtimaActividad());
                    }
                }
                if (guardado == 0) {
                    mensajeError += "La actividad no pudo ser guardada";
                } else if (guardado == 1) {
                    mensajeExito += "La actividad ha sido guardada con éxito";
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError += "Ocurrió un error guardando la actividad";
            }
        }
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    /**
     * Método encargado de validar la información ingresada por pantalla al
     * momento de crear, duplicar o editar una actividad
     *
     * @param idActividad
     * @param idProyecto
     * @param idVersion
     * @param nombre
     * @param descripcion
     * @param idEstado
     * @return
     */
    public String validarDatos(Integer idActividad, Integer idProyecto, Integer idVersion, String nombre, String descripcion, Integer idEstado) {
        String validacion = "";
        if (idProyecto == null || idProyecto.intValue() == 0) {
            validacion += "El campo 'Proyecto' es obligatorio <br/>";
        }
        if (idVersion == null || idVersion.intValue() == 0) {
            validacion += "El campo 'Versión' es obligatorio <br/>";
        }
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' es obligatorio <br/>";
        } else if (nombre.length() > 80) {
            validacion += "El campo 'Nombre' no debe contener más de 80 caracteres. Has dígitado " + nombre.length() + " caracteres <br/>";
        }
        if (descripcion == null || descripcion.isEmpty()) {
            validacion += "El campo 'Descripción' es obligatorio <br/>";
        } else if (descripcion.length() > 1000) {
            validacion += "El campo 'Descripción' no debe contener más de 1000 caracteres. Has dígitado " + descripcion.length() + " caracteres <br/>";
        }
        if (idEstado == null || idEstado.intValue() == 0) {
            validacion += "El campo 'Estado' es obligatorio <br/>";
        } else /* Actividad existente: se valida contra estados previo y siguiente */ if (idActividad != null) {
            ActividadesBean actividadAntigua = consultarActividadPorId(idActividad);
            if (actividadAntigua != null) {
                //Si el estado seleccionado es distinto al que ya tenía, se valida que sea el previo o el siguiente
                if (actividadAntigua.getEstado() != idEstado) {
                    List<EstadosBean> listaEstados;
                    try {
                        listaEstados = estadosDao.consultarEstados(actividadAntigua.getEstado(), null, null, null, null, null, null);
                    } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                        Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                        listaEstados = null;
                    }
                    if (listaEstados != null && !listaEstados.isEmpty()) {
                        if (listaEstados.get(0).getEstadoPrevio() != null && listaEstados.get(0).getEstadoPrevio() > 0
                                || listaEstados.get(0).getEstadoSiguiente() != null && listaEstados.get(0).getEstadoSiguiente() > 0) {
                            if (listaEstados.get(0).getEstadoPrevio() != idEstado && listaEstados.get(0).getEstadoSiguiente() != idEstado) {
                                validacion += "El estado seleccionado no es válido. <br/>";
                            }
                        }
                    }
                }
            }
        } else {
            /* Actividad nueva: se valida contra estado final unicamente */
            List<EstadosBean> listaEstados;
            try {
                listaEstados = estadosDao.consultarEstados(null, "ACTIVIDADES", null, null, null, "T", null);
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                listaEstados = null;
            }
            if (listaEstados != null && !listaEstados.isEmpty()) {
                if (listaEstados.get(0).getId().intValue() == idEstado.intValue()) {
                    validacion += "El estado seleccionado no es válido para una nueva actividad <br/>";
                }
            }
        }
        return validacion;
    }

    /**
     * Método encargado de asociar un responsable a una actividad
     *
     * @param idActividad
     * @param idPersona
     * @param fechaInicio
     * @param fechaFin
     * @param tiempo
     * @param estimacion
     * @return
     */
    public String guardarActividadPersona(Integer idActividad, Integer idPersona, String fechaInicio, String fechaFin, String tiempo, boolean estimacion) {
        String error = "";
        ActividadesEmpleadosBean actividadesEmpleados = new ActividadesEmpleadosBean();
        actividadesEmpleados.setActividad(idActividad);
        actividadesEmpleados.setEmpleado(idPersona);
        if (estimacion) {
            try {
                actividadesEmpleados.setFechaEstimadaInicio(sdf.parse(fechaInicio));
                actividadesEmpleados.setFechaEstimadaTerminacion(sdf.parse(fechaFin));
                actividadesEmpleados.setTiempoEstimado(Double.valueOf(tiempo));
            } catch (NumberFormatException | ParseException ex) {
                Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<ActividadesEmpleadosBean> listaActividadesEmpleados;
        try {
            listaActividadesEmpleados = actividadesEmpleadosDao.consultarActividadesEmpleados(idActividad, idPersona, null, null, false, true);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            listaActividadesEmpleados = null;
        }
        try {
            int guardado = 0;
            if (listaActividadesEmpleados != null && !listaActividadesEmpleados.isEmpty()) {
                guardado = actividadesEmpleadosDao.actualizarActividadEmpleado(actividadesEmpleados);
                if (guardado == 0) {
                    error = "El reponsable no pudo ser asociado a la actividad";
                }
            } else {
                guardado = actividadesEmpleadosDao.insertarActividadEmpleado(actividadesEmpleados);
                if (guardado == 0) {
                    error = "La estimación de trabajo del responsable no pudo ser guardada";
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error guardando el reponsable de la actividad";
        }
        return error;
    }

    /**
     * Método encargado de consultar los responsables asociados a una actividad
     *
     * @param idActividad
     * @return
     */
    public JSONArray consultarActividadesEmpleados(Integer idActividad) {
        JSONArray responsablesActividad = new JSONArray();
        List<ActividadesEmpleadosBean> actividadesEmplados = consultarActividadesEmpleados(idActividad, null, null, null, false, true);
        if (actividadesEmplados != null && !actividadesEmplados.isEmpty()) {
            for (ActividadesEmpleadosBean actividadEmpleado : actividadesEmplados) {
                JSONObject actividad = new JSONObject();
                actividad.put("idPersona", actividadEmpleado.getEmpleado());
                actividad.put("nombre", actividadEmpleado.getTipoDocumento() + actividadEmpleado.getDocumento() + " " + actividadEmpleado.getNombrePersona());
                actividad.put("cargo", actividadEmpleado.getCargo());
                actividad.put("fechaInicio", actividadEmpleado.getFechaEstimadaInicio() != null ? sdf.format(actividadEmpleado.getFechaEstimadaInicio()) : "");
                actividad.put("fechaFin", actividadEmpleado.getFechaEstimadaTerminacion() != null ? sdf.format(actividadEmpleado.getFechaEstimadaTerminacion()) : "");
                actividad.put("tiempoEstimado", actividadEmpleado.getTiempoEstimado());
                actividad.put("tiempoInvertido", actividadEmpleado.getTiempoInvertido());
                responsablesActividad.add(actividad);
            }
        }
        return responsablesActividad;
    }

    /**
     * Método encargado de consultar los responsables de una actividad o las
     * actividades asociadas a un responsable en un periodo de tiempo
     *
     * @param idActividad
     * @param idEmpleado
     * @param fechaEstimadaInicio
     * @param fechaEstimadaFin
     * @param evaluarEstado
     * @param actividadIgual
     * @return
     */
    public List<ActividadesEmpleadosBean> consultarActividadesEmpleados(Integer idActividad, Integer idEmpleado, Date fechaEstimadaInicio, Date fechaEstimadaFin, boolean evaluarEstado, boolean actividadIgual) {
        List<ActividadesEmpleadosBean> listaActividadesEmpleados = new ArrayList<>();
        try {
            listaActividadesEmpleados = actividadesEmpleadosDao.consultarActividadesEmpleados(idActividad, idEmpleado, fechaEstimadaInicio, fechaEstimadaFin, evaluarEstado, actividadIgual);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividadesEmpleados;
    }

    /**
     * Método encargado de consultar si una persona ya tiene actividades
     * previamente asignadas en las fechas de inicio y fin seleccionadas para
     * una nueva actividad
     *
     * @param idActividad
     * @param idEmpleado
     * @param fechaEstimadaInicio
     * @param fechaEstimadaFin
     * @return
     */
    public JSONArray consultarActividadesAsociadas(Integer idActividad, Integer idEmpleado, Date fechaEstimadaInicio, Date fechaEstimadaFin) {
        JSONArray actividadesAsociadas = new JSONArray();
        List<ActividadesEmpleadosBean> actividadesEmplados = consultarActividadesEmpleados(idActividad, idEmpleado, fechaEstimadaInicio, fechaEstimadaFin, true, false);
        if (actividadesEmplados != null && !actividadesEmplados.isEmpty()) {
            for (ActividadesEmpleadosBean actividadEmpleado : actividadesEmplados) {
                JSONObject actividad = new JSONObject();
                actividad.put("nombre", actividadEmpleado.getNombreActividad());
                actividad.put("estado", actividadEmpleado.getEstado());
                actividad.put("fechaInicio", actividadEmpleado.getFechaEstimadaInicio() != null ? sdf.format(actividadEmpleado.getFechaEstimadaInicio()) : "");
                actividad.put("fechaFin", actividadEmpleado.getFechaEstimadaTerminacion() != null ? sdf.format(actividadEmpleado.getFechaEstimadaTerminacion()) : "");
                actividad.put("tiempo", actividadEmpleado.getTiempoEstimado() != null ? actividadEmpleado.getTiempoEstimado() : 0);
                actividadesAsociadas.add(actividad);
            }
        }
        return actividadesAsociadas;
    }

    /**
     * Método encargado de consultar las actividades de acuerdo a los filtros
     * ingresados en pantalla
     *
     * @param idActividad
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param idEstado
     * @param responsable
     * @param limite
     * @return
     */
    public List<ActividadesBean> consultarActividades(Integer idActividad, Integer proyecto, Integer version, String contiene, String fecha, Integer idEstado, Integer responsable, String limite) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        Date filtroFecha = null;
        if (fecha != null && !fecha.isEmpty()) {
            try {
                filtroFecha = sdf.parse(fecha);
            } catch (ParseException e) {
            }
        }
        try {
            listaActividades = actividadesDao.consultarActividades(idActividad, proyecto, version, contiene, filtroFecha, idEstado, responsable, limite);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividades;
    }

    /**
     * Método encargado de retornar la cantidad de actividades relacionadas con
     * los filtros ingresados en la consulta
     *
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param idEstado
     * @param responsable
     * @return
     */
    public int contarActividades(Integer proyecto, Integer version, String contiene, String fecha, Integer idEstado, Integer responsable) {
        int actividades = 0;
        Date filtroFecha = null;
        if (fecha != null && !fecha.isEmpty()) {
            try {
                filtroFecha = sdf.parse(fecha);
            } catch (ParseException e) {
            }
        }
        try {
            actividades = actividadesDao.contarActividades(proyecto, version, contiene, filtroFecha, idEstado, responsable);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actividades;
    }

    /**
     * Método encargado de consultar la información relacionado con una
     * actividad por medio del id de la misma
     *
     * @param idActividad
     * @return
     */
    public ActividadesBean consultarActividadPorId(Integer idActividad) {
        List<ActividadesBean> listaActividades = consultarActividades(idActividad, null, null, null, null, null, null, null);
        if (listaActividades != null && !listaActividades.isEmpty()) {
            return listaActividades.get(0);
        }
        return null;
    }

    /**
     * Método encargado de realizar la eliminación lógica de una actividad
     *
     * @param idActividad
     * @return
     */
    public String eliminarActividad(Integer idActividad) {
        String error = "";
        try {
            actividadesEmpleadosDao.eliminarActividadEmpleado(idActividad, null);
            actividadesEsfuerzosDao.eliminarActividadEsfuerzo(null, idActividad, null);
            int eliminacion = actividadesDao.eliminarActividad(idActividad);
            if (eliminacion == 0) {
                error = "La actividad no pudo ser eliminada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando la actividad";
        }
        return error;
    }

    /**
     * Método encargado de eliminar la asociación del responsable con una
     * actividad, eliminando además el tiempo invertido en la actividad por
     * dicho responsable
     *
     * @param idActividad
     * @param idEmpleado
     * @return
     */
    public String eliminarActividadEmpleado(Integer idActividad, Integer idEmpleado) {
        String error = "";
        try {
            actividadesEsfuerzosDao.eliminarActividadEsfuerzo(null, idActividad, idEmpleado);
            int eliminacionEmpleado = actividadesEmpleadosDao.eliminarActividadEmpleado(idActividad, idEmpleado);
            if (eliminacionEmpleado == 0) {
                error = "El responsable de la actividad no pudo ser eliminado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el responsable de la actividad";
        }
        return error;
    }

    /**
     * Método encargado de eliminar un registro de tiempo ingresado por un
     * empleado para una actividad
     *
     * @param idEsfuerzo
     * @return
     */
    public String eliminarActividadEsfuerzo(Integer idEsfuerzo) {
        String error = "";
        try {
            int eliminacion = actividadesEsfuerzosDao.eliminarActividadEsfuerzo(idEsfuerzo, null, null);
            if (eliminacion == 0) {
                error = "El registro de tiempo no pudo ser eliminado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando el registro de tiempo";
        }
        return error;
    }

    /**
     * Método encargado de consultar el tiempo que una persona ha invertido en
     * la atención de una actividad
     *
     * @param idActividad
     * @param idEmpleado
     * @return
     */
    public JSONArray consultarHistorialTrabajo(Integer idActividad, Integer idEmpleado) {
        JSONArray historial = new JSONArray();
        try {
            List<ActividadesEsfuerzosBean> listaActividadesEsfuerzos = actividadesEsfuerzosDao.consultarActividadesEsfuerzos(null, idActividad, idEmpleado, null, null);
            if (listaActividadesEsfuerzos != null && !listaActividadesEsfuerzos.isEmpty()) {
                for (ActividadesEsfuerzosBean actividadEsfuerzo : listaActividadesEsfuerzos) {
                    JSONObject esfuerzo = new JSONObject();
                    esfuerzo.put("id", actividadEsfuerzo.getId());
                    esfuerzo.put("actividad", actividadEsfuerzo.getActividad());
                    esfuerzo.put("empleado", actividadEsfuerzo.getEmpleado());
                    esfuerzo.put("fecha", sdf.format(actividadEsfuerzo.getFecha()));
                    esfuerzo.put("tiempo", actividadEsfuerzo.getTiempo());
                    esfuerzo.put("descripcion", actividadEsfuerzo.getDescripcion());
                    historial.add(esfuerzo);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historial;
    }

    /**
     * Método encargado de almacenar el tiempo que ha invertido un responsable
     * en la ejecución de una actividad
     *
     * @param idEsfuerzo
     * @param idActividad
     * @param idEmpleado
     * @param fecha
     * @param tiempo
     * @param descripcion
     * @return
     */
    public String guardarActividadEsfuerzo(Integer idEsfuerzo, Integer idActividad, Integer idEmpleado, String fecha, String tiempo, String descripcion) {
        String error = "";
        ActividadesEsfuerzosBean actividadesEsfuerzos = new ActividadesEsfuerzosBean();
        actividadesEsfuerzos.setActividad(idActividad);
        actividadesEsfuerzos.setEmpleado(idEmpleado);
        actividadesEsfuerzos.setTiempo(Double.valueOf(tiempo));
        try {
            actividadesEsfuerzos.setFecha(sdf.parse(fecha));
        } catch (ParseException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        actividadesEsfuerzos.setDescripcion(descripcion);
        try {
            int guardado = 0;
            if (idEsfuerzo != null) {
                actividadesEsfuerzos.setId(idEsfuerzo);
                guardado = actividadesEsfuerzosDao.actualizarActividadEsfuerzo(actividadesEsfuerzos);
            } else {
                guardado = actividadesEsfuerzosDao.crearActividadEsfuerzo(actividadesEsfuerzos);
            }
            if (guardado == 0) {
                error = "El tiempo invertido en la actividad no pudo ser guardado";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error guardando el tiempo invertido en la actividad";
        }
        return error;
    }

    /**
     * Método encargado de validar los datos ingresados en los registros de
     * trabajo de los empleados en una actividad antes de su almacenamiento en
     * base de datos
     *
     * @param fecha
     * @param tiempo
     * @param descripcion
     * @return
     */
    public String validarActividadEsfuerzo(String fecha, String tiempo, String descripcion) {
        String advertencia = "";
        if (fecha == null || fecha.isEmpty()) {
            advertencia += "El campo 'Fecha' es obligatorio <br>";
        } else {
            try {
                sdf.parse(fecha);
            } catch (ParseException e) {
                advertencia += "El valor ingresado en el campo 'Fecha' no se encuentra en el formato 'día/mes/año' <br>";
            }
        }
        if (tiempo == null || tiempo.isEmpty()) {
            advertencia += "El campo 'Tiempo invertido' es obligatorio <br>";
        } else {
            try {
                if (Double.parseDouble(tiempo) <= 0) {
                    advertencia += "El valor ingresado en el campo 'Tiempo invertido' debe ser un número positivo <br>";
                }
            } catch (NumberFormatException e) {
                advertencia += "El valor ingresado en el campo 'Tiempo invertido' no es un número decimal <br>";
            }
        }
        if (descripcion == null || descripcion.isEmpty()) {
            advertencia += "El campo 'Descripción' es obligatorio <br>";
        } else if (descripcion.length() > 500) {
            advertencia += "El campo 'Descripción' no debe contener más de 500 caracteres. Has dígitado " + descripcion.length() + " caracteres <br/>";
        }
        return advertencia;
    }

    /**
     * Método encargado de validar los datos ingresados en la estimación de
     * trabajo para un empleado en una actividad
     *
     * @param fechaInicio
     * @param fechaFin
     * @param tiempo
     * @return
     */
    public String validarActividadEmpleado(String fechaInicio, String fechaFin, String tiempo) {
        String advertencia = "";
        if (fechaInicio == null || fechaInicio.isEmpty()) {
            advertencia += "El campo 'Fecha de inicio' es obligatorio <br>";
        } else {
            try {
                sdf.parse(fechaInicio);
            } catch (ParseException e) {
                advertencia += "El valor ingresado en el campo 'Fecha de inicio' no se encuentra en el formato 'día/mes/año' <br>";
            }
        }
        if (fechaFin == null || fechaFin.isEmpty()) {
            advertencia += "El campo 'Fecha de fin' es obligatorio <br>";
        } else {
            try {
                sdf.parse(fechaFin);
            } catch (ParseException e) {
                advertencia += "El valor ingresado en el campo 'Fecha de fin' no se encuentra en el formato 'día/mes/año' <br>";
            }
        }
        if (tiempo == null || tiempo.isEmpty()) {
            advertencia += "El campo 'Tiempo estimado' es obligatorio <br>";
        } else {
            try {
                if (Double.parseDouble(tiempo) <= 0) {
                    advertencia += "El valor ingresado en el campo 'Tiempo estimado' debe ser un número positivo <br>";
                }
            } catch (NumberFormatException e) {
                advertencia += "El valor ingresado en el campo 'Tiempo estimado' no es un número decimal <br>";
            }
        }
        return advertencia;
    }

    /**
     * Consulta de datos para el reporte de actividades por estado
     *
     * @param proyecto
     * @param version
     * @param persona
     * @return
     */
    public JRDataSource actividadesPorEstado(Integer proyecto, Integer version, Integer persona) {
        DRDataSource datos = new DRDataSource("estado", "actividades", "porcentaje");
        try {
            List<Map<String, Object>> actividadesPorEstado = actividadesDao.actividadesPorEstado(proyecto, version, persona);
            double totalActividades = 0;
            for (Map<String, Object> estado : actividadesPorEstado) {
                try {
                    totalActividades += (int) estado.get("actividades");
                } catch (Exception e) {
                }
            }
            for (Map<String, Object> estado : actividadesPorEstado) {
                double actividades = ((Integer) estado.get("actividades")).doubleValue();
                if (totalActividades > 0) {
                    datos.add(estado.get("estado"), estado.get("actividades"), actividades / totalActividades);
                } else {
                    datos.add(estado.get("estado"), estado.get("actividades"), 0);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

    /**
     * Método encargado de retornar el dataset del reporte de actividades
     *
     * @param listaActividades
     * @return
     */
    public JRDataSource listaActividades(List<ActividadesBean> listaActividades) {
        DRDataSource datos = new DRDataSource("proyecto",
                "version",
                "actividad",
                "estado",
                "fechaInicio",
                "fechaFin",
                "fechaRealInicio",
                "ultimaModificacion",
                "tiempoEstimado",
                "tiempoInvertido",
                "porcentaje");
        for (ActividadesBean actividad : listaActividades) {
            double porcentajeAvance = 0;
            try {
                porcentajeAvance = actividad.getTiempoInvertido() / actividad.getTiempoEstimado();
            } catch (Exception e) {
            }
            datos.add(actividad.getNombreProyecto(),
                    actividad.getNombreVersion(),
                    actividad.getNombre(),
                    actividad.getNombreEstado(),
                    actividad.getFechaInicio() != null ? sdf.format(actividad.getFechaInicio()) : "",
                    actividad.getFechaFin() != null ? sdf.format(actividad.getFechaFin()) : "",
                    actividad.getFechaRealInicio() != null ? sdf.format(actividad.getFechaRealInicio()) : "",
                    actividad.getUltimaModificacion() != null ? sdf.format(actividad.getUltimaModificacion()) : "",
                    actividad.getTiempoEstimado() != null ? actividad.getTiempoEstimado() : 0,
                    actividad.getTiempoInvertido() != null ? actividad.getTiempoInvertido() : 0,
                    porcentajeAvance);
        }
        return datos;
    }

    /**
     * Método encargado de retornar la información relacionada con los filtros
     * ingresados en la pantalla de gestión de actividades para generar el
     * reporte detallado de actividades.
     *
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param estado
     * @param responsable
     * @return
     */
    public List<Map<String, Object>> actividadesDetalladas(Integer proyecto, Integer version, String contiene, String fecha, Integer estado, Integer responsable) {
        List<Map<String, Object>> listaActividades;
        Date filtroFecha = null;
        if (fecha != null && !fecha.isEmpty()) {
            try {
                filtroFecha = sdf.parse(fecha);
            } catch (ParseException e) {
            }
        }
        try {
            return actividadesDao.detalleActividades(proyecto, version, contiene, filtroFecha, estado, responsable);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            listaActividades = new ArrayList<>();
        }
        return listaActividades;
    }

    /**
     * Método encargado de formatear la información del reporte de actividades
     * detallado para enviarlo al generador del reporte.
     *
     * @param listaActividades
     * @return
     */
    public JRDataSource listaDetalladaActividades(List<Map<String, Object>> listaActividades) {

        DRDataSource datos = new DRDataSource("proyecto",
                "version",
                "actividad",
                "estado",
                "responsable",
                "documento",
                "fechaInicio",
                "fechaFin",
                "tiempoEstimado",
                "tiempoInvertido");
        if (listaActividades != null && !listaActividades.isEmpty()) {
            for (Map<String, Object> actividad : listaActividades) {
                datos.add(actividad.get("proyecto"),
                        actividad.get("version"),
                        actividad.get("actividad"),
                        actividad.get("estado"),
                        actividad.get("responsable") != null ? actividad.get("responsable") : "",
                        actividad.get("documento") != null ? actividad.get("documento") : "",
                        actividad.get("fechaInicio") != null ? sdf.format(actividad.get("fechaInicio")) : "",
                        actividad.get("fechaFin") != null ? sdf.format(actividad.get("fechaFin")) : "",
                        actividad.get("tiempoEstimado"),
                        actividad.get("tiempoInvertido"));
            }
        }
        return datos;
    }

    /**
     * Método encargado de consultar los distintos estados de actividades
     * distintos de finalizado y para cada uno de estos definir la cantidad de
     * actividades asociadas
     *
     * @param proyecto
     * @param version
     * @param persona
     * @return
     */
    public List<Map<String, Object>> listarActividadesPorEstado(Integer proyecto, Integer version, Integer persona) {
        List<Map<String, Object>> actividadesPorEstado = new ArrayList<>();
        try {
            List<Map<String, Object>> estados = actividadesDao.actividadesPorEstado(proyecto, version, persona);
            double totalActividades = 0;
            for (Map<String, Object> estado : estados) {
                try {
                    totalActividades += (Integer) estado.get("actividades");
                } catch (Exception e) {
                }
            }
            for (Map<String, Object> estado : estados) {
                double actividades = ((Integer) estado.get("actividades")).doubleValue();
                if (totalActividades > 0) {
                    estado.put("porcentaje", (Math.round(actividades / totalActividades * 1000d) / 10d) + "%");
                } else {
                    estado.put("porcentaje", 0 + "%");
                }
                actividadesPorEstado.add(estado);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actividadesPorEstado;
    }

    /**
     * Método encargado de retornar la lista de datos relacionados el tiempo
     * estimado e invertido en los proyectos o versiones filtradas en la página
     * de inicio de la aplicación
     *
     * @param proyecto
     * @param version
     * @param participante
     * @return
     */
    public List<ActividadesBean> consolidadoActividades(Integer proyecto, Integer version, Integer participante) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        try {
            listaActividades = actividadesDao.consolidadoActividades(proyecto, version, participante);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividades;
    }

    /**
     * Método encargado de retornar la lista de datos a imprimir en el reporte
     * consolidado de tiempo invertido en proyectos o versiones de proyectos
     *
     * @param proyecto
     * @param version
     * @param persona
     * @return La información en el formato requerido para crear el reporte
     */
    public JRDataSource datosConsolidadoActividades(Integer proyecto, Integer version, Integer persona) {
        DRDataSource datos = new DRDataSource("item",
                "tiempoEstimado",
                "tiempoInvertido");
        List<ActividadesBean> listaActividades = consolidadoActividades(proyecto, version, persona);
        if (listaActividades != null && !listaActividades.isEmpty()) {
            boolean porVersion = proyecto != null && proyecto != 0;
            for (ActividadesBean actividad : listaActividades) {
                datos.add(porVersion ? actividad.getNombreVersion() : actividad.getNombreProyecto(),
                        actividad.getTiempoEstimado(),
                        actividad.getTiempoInvertido());
            }
        }
        return datos;
    }
}
