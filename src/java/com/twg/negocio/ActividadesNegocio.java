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
import org.json.simple.JSONObject;

/**
 *
 * @author Jorman Rincón
 */
public class ActividadesNegocio {

    private final ActividadesDao actividadesDao = new ActividadesDao();
    private final ActividadesEmpleadosDao actividadesEmpleadosDao = new ActividadesEmpleadosDao();
    private final ActividadesEsfuerzosDao actividadesEsfuerzosDao = new ActividadesEsfuerzosDao();

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Map<String, Object> guardarActividad(String id, String proyecto, String version, String[] participantes, String descripcion, String fecha_estimada_inicio, String fecha_estimada_terminacion, String fecha_real_inicio, String fecha_real_terminacion, String tiempo_estimado, String tiempo_invertido, String estado) {

        String mensajeExito = "";
        String mensajeError = validarDatos(id, proyecto, version, participantes, descripcion, fecha_estimada_inicio, fecha_estimada_terminacion, fecha_real_inicio, fecha_real_terminacion, tiempo_estimado, tiempo_invertido, estado);

        if (mensajeError.isEmpty()) {
            try {
                int guardado;
                int guardadoAct_Emp = 0;
                int ultActividad;

                ActividadesBean actividad = new ActividadesBean();

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
                actividad.setEstado(Integer.valueOf(estado));
                if (tiempo_estimado.equals("")) {
                    tiempo_estimado = "0";
                }
                if (tiempo_invertido.equals("")) {
                    tiempo_invertido = "0";
                }
                actividad.setTiempo_estimado(Double.valueOf(tiempo_estimado));
                actividad.setTiempo_invertido(Double.valueOf(tiempo_invertido));

                if (id != null && !id.isEmpty()) { //Condición para modificar
                    actividad.setId(Integer.valueOf(id));
                    guardado = actividadesDao.actualizarActividad(actividad);
                    //En caso de eliminar un participante existente
                    actividadesEmpleadosDao.eliminarActividadesEmpleados(Integer.valueOf(id), participantes);
                    actividadesEsfuerzosDao.eliminarActividadesEsfuerzos(Integer.valueOf(id), participantes);

                    for (String item : participantes) { //Aplica para tablas actividades_empleados y actividades_esfuerzos
                        ActividadesEmpleadosBean actividadEmpleadoBeanAux = actividadesEmpleadosDao.consultarActividadEmpleado(Integer.valueOf(id), Integer.valueOf(item));
                        ActividadesEsfuerzosBean actividadEsfuerzoBeanAux = actividadesEsfuerzosDao.consultarActividadEsfuerzo(null, Integer.valueOf(id), Integer.valueOf(item), null, null, null);

                        //las siguientes líneas es para manejar los datos en la tabla actividades_empleados
                        ActividadesEmpleadosBean actividadEmpleadoBean = new ActividadesEmpleadosBean();
                        actividadEmpleadoBean.setEmpleado(Integer.valueOf(item));
                        actividadEmpleadoBean.setActividad(actividad.getId());

                        //las siguientes líneas es para manejar los datos en la tabla actividades_esfuerzos
                        ActividadesEsfuerzosBean actividadEsfuerzoBean = new ActividadesEsfuerzosBean();
                        actividadEsfuerzoBean.setActividad(actividad.getId());
                        actividadEsfuerzoBean.setEmpleado(Integer.valueOf(item));
                        try {
                            actividadEsfuerzoBean.setFecha(sdf.parse(fecha_estimada_terminacion));
                        } catch (ParseException ex) {
                            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        actividadEsfuerzoBean.setTiempo(Double.valueOf(tiempo_estimado));
                        actividadEsfuerzoBean.setDescripcion(descripcion);

                        //Condición si se añade información del participante
                        if (actividadEmpleadoBeanAux.getActividad() != null && actividadEmpleadoBeanAux.getEmpleado() != null && actividadEsfuerzoBeanAux.getId() != null) {
                            actividadesEsfuerzosDao.actualizarActividadEsfuerzo(actividadEsfuerzoBean);
                            guardadoAct_Emp += 1;
                        } else {
                            //Condición si se añade un nuevo participante
                            actividadesEsfuerzosDao.crearActividadEsfuerzo(actividadEsfuerzoBean);
                            actividadesEmpleadosDao.insertarActividadEmpleado(actividadEmpleadoBean);
                            guardadoAct_Emp += 1;
                        }
                    }

                } else { //Sino es una inserción
                    guardado = actividadesDao.crearActividad(actividad);
                    ultActividad = actividadesDao.consultarUtimaActividad();
                    for (String item : participantes) {
                        //las siguientes líneas es para insertar los datos en la tabla actividades_empleados
                        ActividadesEmpleadosBean actividadEmpleadoBean = new ActividadesEmpleadosBean();
                        actividadEmpleadoBean.setEmpleado(Integer.valueOf(item));
                        actividadEmpleadoBean.setActividad(ultActividad);

                        //las siguientes líneas es para insertar los datos en la tabla actividades_esfuerzos
                        ActividadesEsfuerzosBean actividadEsfuerzoBean = new ActividadesEsfuerzosBean();
                        actividadEsfuerzoBean.setActividad(ultActividad);
                        actividadEsfuerzoBean.setEmpleado(Integer.valueOf(item));
                        try {
                            actividadEsfuerzoBean.setFecha(sdf.parse(fecha_estimada_terminacion));
                        } catch (ParseException ex) {
                            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        actividadEsfuerzoBean.setTiempo(Double.valueOf(tiempo_estimado));
                        actividadEsfuerzoBean.setDescripcion(descripcion);

                        actividadesEsfuerzosDao.crearActividadEsfuerzo(actividadEsfuerzoBean);
                        actividadesEmpleadosDao.insertarActividadEmpleado(actividadEmpleadoBean);
                        guardadoAct_Emp += 1;
                    }
                }
                if (guardado == 0 && guardadoAct_Emp == 0) {
                    mensajeError += "La actividad no pudo ser guardada";
                } else if (guardado == 1 && guardadoAct_Emp > 0) {
                    mensajeExito += "La actividad fue registrada correctamente";
                }
            } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError += "La actividad no pudo ser guardada";
            }
        }
        Map<String, Object> result = new HashMap<>();
        if (!mensajeError.isEmpty()) {
            result.put("mensajeError", mensajeError);
        }
        if (!mensajeExito.isEmpty()) {
            result.put("mensajeExito", mensajeExito);
        }
        return result;
    }

    public String validarDatos(String idAct, String proyecto, String version, String[] participantes, String descripcion, String fecha_estimada_inicio, String fecha_estimada_terminacion, String fecha_real_inicio, String fecha_real_terminacion, String tiempo_estimado, String tiempo_invertido, String estado) {
        String validacion = "";

        if (proyecto == null || proyecto.equals("0")) {
            validacion += "El campo 'Proyecto' no debe estar vacío <br />";
        }

        if (version == null || version.equals("0")) {
            validacion += "El campo 'Versión' no debe estar vacío <br />";
        }

        if (descripcion == null || descripcion.isEmpty()) {
            validacion += "El campo 'Descripción' no debe estar vacío <br />";
        } else {
            if (descripcion.length() > 1000) {
                validacion += "El campo 'Descripción' no debe contener más de 1000 caracteres, has dígitado " + descripcion.length() + " caracteres <br />";
            }
        }

        if (fecha_estimada_inicio == null || fecha_estimada_inicio.isEmpty()) {
            validacion += "El campo 'Fecha estimada de inicio' no debe estar vacío <br />";
        } else {
            try {
                sdf.parse(fecha_estimada_inicio);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha estimada de inicio' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }

        if (fecha_estimada_terminacion == null || fecha_estimada_terminacion.isEmpty()) {
            validacion += "El campo 'Fecha estimada de terminación' no debe estar vacío <br />";
        } else {
            try {
                sdf.parse(fecha_estimada_terminacion);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha estimada de terminación' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }

        if (tiempo_estimado == null || tiempo_estimado.equals("")) {
            validacion += "El campo 'Tiempo estimado' no debe estar vacío <br />";
        } else if (!tiempo_estimado.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            validacion += "El valor ingresado en el campo 'Tiempo estimado' solo debe contener números' <br />";
        }

        if (estado == null || estado.equals("0")) {
            validacion += "El campo 'Estado' no debe estar vacío <br />";
        } else {
            int est = 0;
            try {
                est = Integer.valueOf(estado);
            } catch (NumberFormatException e) {
                validacion += "El valor ingresado en el campo 'Estado' no corresponde al id de un estado <br />";
            }
            EstadosDao eDao = new EstadosDao();
            try {
                if (idAct != null && !idAct.equals("")) {//Actividad existente: se valida contra estados prev y sig
                    int act = 0;
                    try {
                        act = Integer.valueOf(idAct);
                    } catch (NumberFormatException e) {
                    }
                    ActividadesBean actividadAntigua = consultarActividadI(act);
                    if (actividadAntigua != null) {
                        //Si el estado seleccionado es distinto al que ya tenía, se valida que sea el previo o el siguiente
                        if (actividadAntigua.getEstado() != est) {
                            List<EstadosBean> listaEstados = eDao.consultarEstados(actividadAntigua.getEstado(), null, null, null, null, null, null);
                            if (listaEstados != null && !listaEstados.isEmpty()) {
                                if (listaEstados.get(0).getEstadoPrevio() != null && listaEstados.get(0).getEstadoPrevio() > 0
                                        || listaEstados.get(0).getEstadoSiguiente() != null && listaEstados.get(0).getEstadoSiguiente() > 0) {
                                    if (listaEstados.get(0).getEstadoPrevio() != est && listaEstados.get(0).getEstadoSiguiente() != est) {
                                        validacion += "El estado seleccionado no es válido. <br />";
                                    }
                                }
                            }
                        }
                    }
                } else {//Actividad nueva: se valida contra estado final unicamente
                    List<EstadosBean> eBean = eDao.consultarEstados(null, "ACTIVIDADES", null, null, null, "T", null);
                    if (eBean != null && !eBean.isEmpty()) {
                        if (eBean.get(0).getId() == est) {
                            validacion += "El estado seleccionado no es válido para una nueva actividad <br />";
                        }
                    }
                }

            } catch (Exception e) {
            }
        }

        if ((fecha_estimada_inicio == null || fecha_estimada_inicio.isEmpty()) && (fecha_estimada_terminacion == null || fecha_estimada_terminacion.isEmpty())) {
        } else {
            try {
                Date inicio = sdf.parse(fecha_estimada_inicio);
                Date fin = sdf.parse(fecha_estimada_terminacion);
                if (inicio.after(fin)) {
                    validacion += "La fecha estimada de inicio no debe ser mayor que la fecha estimada de terminación <br />";
                }
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha estimada de inicio ó Fecha estimada terminacion' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }

        if ((fecha_real_inicio == null || fecha_real_inicio.isEmpty()) && (fecha_real_terminacion == null || fecha_real_terminacion.isEmpty())) {
        } else {
            try {
                Date inicio = sdf.parse(fecha_real_inicio);
                Date fin = sdf.parse(fecha_real_terminacion);
                if (inicio.after(fin)) {
                    validacion += "La fecha real de inicio no debe ser mayor que la fecha real de terminación <br />";
                }
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha real de inicio ó Fecha real terminacion' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }

        if (participantes == null || participantes.length == 0) {
            validacion += "Se debe añadir al menos 1 participante en la actividad. <br />";
        }

        if (fecha_real_inicio != null && !fecha_real_inicio.isEmpty()) {
            try {
                sdf.parse(fecha_real_inicio);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha real de inicio' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }

        if (fecha_real_terminacion != null && !fecha_real_terminacion.isEmpty()) {
            try {
                sdf.parse(fecha_real_terminacion);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha real de terminación' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }

        if (tiempo_invertido != null && !tiempo_invertido.isEmpty()) {
            if (!tiempo_invertido.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
                validacion += "El valor ingresado en el campo 'Tiempo invertido' solo debe contener números' <br />";
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

    public List<ActividadesEmpleadosBean> consultarActividadesEmpleados(Integer idActividad, Integer idEmpleado) {
        List<ActividadesEmpleadosBean> listaActividadesEmpleados = new ArrayList<>();
        try {
            listaActividadesEmpleados = actividadesEmpleadosDao.consultarActividadesEmpleados(idActividad, idEmpleado);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaActividadesEmpleados;
    }

    //metodo para obtener el listado de actividades que seran cargados en la tabla de consulta
    public List<ActividadesBean> consultarActividades(Integer proyecto, Integer version, String descripcion, String fecha,
            String estado, Integer responsable) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        Date filtroFecha = null;
        if (fecha != null && !fecha.isEmpty()) {
            try {
                filtroFecha = sdf.parse(fecha);
            } catch (ParseException e) {
            }
        }
        Integer filtroEstado;
        try {
            filtroEstado = Integer.valueOf(estado);
        } catch (NumberFormatException e) {
            filtroEstado = null;
        }
        try {
            listaActividades = actividadesDao.consultarActividades(proyecto, version, descripcion, filtroFecha, filtroEstado, responsable);
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
            object.put("fecha_estimada_inicio", listaActividades.get(0).getFecha_estimada_inicio() != null ? sdf.format(listaActividades.get(0).getFecha_estimada_inicio()) : "");
            object.put("fecha_estimada_terminacion", listaActividades.get(0).getFecha_estimada_terminacion() != null ? sdf.format(listaActividades.get(0).getFecha_estimada_terminacion()) : "");
            object.put("fecha_real_inicio", listaActividades.get(0).getFecha_real_inicio() != null ? sdf.format(listaActividades.get(0).getFecha_real_inicio()) : "");
            object.put("fecha_real_terminacion", listaActividades.get(0).getFecha_real_terminacion() != null ? sdf.format(listaActividades.get(0).getFecha_real_terminacion()) : "");
            object.put("tiempo_estimado", listaActividades.get(0).getTiempo_estimado());
            object.put("tiempo_invertido", listaActividades.get(0).getTiempo_invertido());
            object.put("estado", listaActividades.get(0).getEstado());
        }
        return object;
    }

    public ActividadesBean consultarActividadI(Integer idActividad) {
        ActividadesBean actividad = new ActividadesBean();
        List<ActividadesBean> listaActividades = consultarActividades(idActividad, null, null, null, null, null, null, null, null, null);
        if (listaActividades != null && !listaActividades.isEmpty()) {
            actividad.setId(listaActividades.get(0).getId());
            actividad.setVersion(listaActividades.get(0).getVersion());
            actividad.setDescripcion(listaActividades.get(0).getDescripcion());
            actividad.setFecha_estimada_inicio(listaActividades.get(0).getFecha_estimada_inicio());
            actividad.setFecha_estimada_terminacion(listaActividades.get(0).getFecha_estimada_terminacion());
            actividad.setFecha_real_inicio(listaActividades.get(0).getFecha_real_inicio());
            actividad.setFecha_real_terminacion(listaActividades.get(0).getFecha_real_terminacion());
            actividad.setTiempo_estimado(listaActividades.get(0).getTiempo_estimado());
            actividad.setTiempo_invertido(listaActividades.get(0).getTiempo_invertido());
            actividad.setEstado(listaActividades.get(0).getEstado());
        }
        return actividad;
    }

    public ActividadesEmpleadosBean consultarActividad_Empleado(Integer idActividad, Integer idEmpleado) {
        ActividadesEmpleadosBean actividad_empleado = new ActividadesEmpleadosBean();
        List<ActividadesEmpleadosBean> listaActividadesEmpleados = consultarActividadesEmpleados(idActividad, idEmpleado);
        if (listaActividadesEmpleados != null && !listaActividadesEmpleados.isEmpty()) {
            actividad_empleado.setActividad(listaActividadesEmpleados.get(0).getActividad());
            actividad_empleado.setEmpleado(listaActividadesEmpleados.get(0).getEmpleado());
        }
        return actividad_empleado;
    }

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
