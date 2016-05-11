package com.twg.negocio;

import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.ActividadesEmpleadosBean;
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
            validacion += "El campo 'Proyecto' no debe estar vacío <br/>";
        }
        if (idVersion == null || idVersion.intValue() == 0) {
            validacion += "El campo 'Versión' no debe estar vacío <br/>";
        }
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío <br/>";
        } else if (nombre.length() > 80) {
            validacion += "El campo 'Nombre' no debe contener más de 80 caracteres, has dígitado " + nombre.length() + " caracteres <br/>";
        }
        if (descripcion == null || descripcion.isEmpty()) {
            validacion += "El campo 'Descripción' no debe estar vacío <br/>";
        } else if (descripcion.length() > 1000) {
            validacion += "El campo 'Descripción' no debe contener más de 1000 caracteres, has dígitado " + descripcion.length() + " caracteres <br/>";
        }
        if (idEstado == null || idEstado.intValue() == 0) {
            validacion += "El campo 'Estado' no debe estar vacío <br/>";
        } else {
            /* Actividad existente: se valida contra estados previo y siguiente */
            if (idActividad != null) {
                ActividadesBean actividadAntigua = consultarActividadPorId(idActividad);
                if (actividadAntigua != null) {
                    //Si el estado seleccionado es distinto al que ya tenía, se valida que sea el previo o el siguiente
                    if (actividadAntigua.getEstado() != idEstado) {
                        List<EstadosBean> listaEstados;
                        try {
                            listaEstados = estadosDao.consultarEstados(actividadAntigua.getEstado(), null, null, null, null, null);
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
                    listaEstados = estadosDao.consultarEstados(null, "ACTIVIDADES", null, null, null, "T");
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
        }
        return validacion;
    }

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
            listaActividadesEmpleados = actividadesEmpleadosDao.consultarActividadesEmpleados(idActividad, idPersona);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            listaActividadesEmpleados = null;
        }
        try {
            int guardado = 0;
            if (listaActividadesEmpleados != null && !listaActividadesEmpleados.isEmpty()) {
                guardado = actividadesEmpleadosDao.insertarActividadEmpleado(actividadesEmpleados);
                if (guardado == 0) {
                    error = "El reponsable no pudo ser asociado a la actividad";
                }
            } else {
                guardado = actividadesEmpleadosDao.actualizarActividadEmpleado(actividadesEmpleados);
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
     * Método encargado de consultar la asociación de empleados a una actividad
     *
     * @param idActividad
     * @param idEmpleado
     * @return
     */
    public List<ActividadesEmpleadosBean> consultarActividadesEmpleados(Integer idActividad, Integer idEmpleado) {
        List<ActividadesEmpleadosBean> listaActividadesEmpleados = new ArrayList<>();
        try {
            listaActividadesEmpleados = actividadesEmpleadosDao.consultarActividadesEmpleados(idActividad, idEmpleado);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividadesEmpleados;
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
     * @return
     */
    public List<ActividadesBean> consultarActividades(Integer idActividad, Integer proyecto, Integer version, String contiene, String fecha, Integer idEstado, Integer responsable) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        Date filtroFecha = null;
        if (fecha != null && !fecha.isEmpty()) {
            try {
                filtroFecha = sdf.parse(fecha);
            } catch (ParseException e) {
            }
        }
        try {
            listaActividades = actividadesDao.consultarActividades(idActividad, proyecto, version, contiene, filtroFecha, idEstado, responsable);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividades;
    }

    /**
     * Método encargado de consultar la información relacionado con una
     * actividad por medio del id de la misma
     *
     * @param idActividad
     * @return
     */
    public ActividadesBean consultarActividadPorId(Integer idActividad) {
        List<ActividadesBean> listaActividades = consultarActividades(idActividad, null, null, null, null, null, null);
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
            int eliminacionAct_Esf = actividadesEmpleadosDao.eliminarActividadEmpleado(idActividad, null);
            int eliminacionAct_Empl = actividadesEsfuerzosDao.eliminarActividadEsfuerzo(idActividad);
            int eliminacion = actividadesDao.eliminarActividad(idActividad);
            if (eliminacion == 0 && eliminacionAct_Esf == 0 && eliminacionAct_Empl == 0) {
                error = "La actividad no pudo ser eliminada";
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ocurrió un error eliminando la actividad";
        }
        return error;
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
}
