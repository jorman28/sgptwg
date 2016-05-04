package com.twg.negocio;

import com.twg.persistencia.beans.ActividadesBean;
import com.twg.persistencia.beans.ActividadesEmpleadosBean;
import com.twg.persistencia.beans.ActividadesEsfuerzosBean;
import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.daos.ActividadesDao;
import com.twg.persistencia.daos.ActividadesEmpleadosDao;
import com.twg.persistencia.daos.ActividadesEsfuerzosDao;
import com.twg.persistencia.daos.EstadosDao;
import com.twg.persistencia.daos.PersonasDao;
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
    //private final ActividadesEsfuerzosDao actividadesEsfuerzosDao = new ActividadesEsfuerzosDao();
    private final PersonasDao personas = new PersonasDao();

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Map<String, Object> guardarActividad(String id, String proyecto, String version, String descripcion, String estado, String[] participantes, List<ActividadesEmpleadosBean> lstActividadesEmpleados) {

        String mensajeExito = "";
        String mensajeError = validarDatos(id, proyecto, version, descripcion, estado, participantes, lstActividadesEmpleados);

        if (mensajeError.isEmpty()) {
            try {
                int guardado;
                int guardadoAct_Emp = 0;
                int ultActividad;

                ActividadesBean actividad = new ActividadesBean();

                actividad.setVersion(Integer.valueOf(version));
                actividad.setDescripcion(descripcion);
                actividad.setEstado(Integer.valueOf(estado));

                if (id != null && !id.isEmpty()) { //Condición para modificar
                    actividad.setId(Integer.valueOf(id));
                    guardado = actividadesDao.actualizarActividad(actividad);
                    //En caso de eliminar un participante existente
                    actividadesEmpleadosDao.eliminarActividadesEmpleados(Integer.valueOf(id), participantes);
                    //actividadesEsfuerzosDao.eliminarActividadesEsfuerzos(Integer.valueOf(id), participantes); //PENDIENTE SABER QUE PASA SI SE ELIMINA A UNA PERSONA DE LA ACTIVIDAD

                    for (ActividadesEmpleadosBean actividadEmpleadoBean : lstActividadesEmpleados) { //Aplica para tablas actividades_empleados y actividades_esfuerzos
                        ActividadesEmpleadosBean actividadEmpleadoBeanAux = actividadesEmpleadosDao.consultarActividadEmpleado(Integer.valueOf(id), actividadEmpleadoBean.getEmpleado());

                        //las siguientes líneas es para manejar los datos en la tabla actividades_empleados
                        actividadEmpleadoBean.setActividad(actividad.getId());

                        //Condición si se añade información del participante
                        if (actividadEmpleadoBeanAux.getActividad() != null && actividadEmpleadoBeanAux.getEmpleado() != null) {
                            actividadesEmpleadosDao.actualizarActividadEmpleado(actividadEmpleadoBean);
                            guardadoAct_Emp += 1;
                        } else {
                            //Condición si se añade un nuevo participante
                            actividadesEmpleadosDao.insertarActividadEmpleado(actividadEmpleadoBean);
                            guardadoAct_Emp += 1;
                        }
                    }

                } else { //Sino es una inserción
                    guardado = actividadesDao.crearActividad(actividad);
                    ultActividad = actividadesDao.consultarUtimaActividad();
                    for (ActividadesEmpleadosBean actividadEmpleadoBean : lstActividadesEmpleados) {
                        //las siguientes líneas es para insertar los datos en la tabla actividades_empleados
                        actividadEmpleadoBean.setActividad(ultActividad);
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

    public String validarDatos(String idAct, String proyecto, String version, String descripcion, String estado, String[] participantes, List<ActividadesEmpleadosBean> lstActividadesEmpleados) {
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
                            List<EstadosBean> listaEstados = eDao.consultarEstados(actividadAntigua.getEstado(), null, null, null, null, null);
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
                    List<EstadosBean> eBean = eDao.consultarEstados(null, "ACTIVIDADES", null, null, null, "T");
                    if (eBean != null && !eBean.isEmpty()) {
                        if (eBean.get(0).getId() == est) {
                            validacion += "El estado seleccionado no es válido para una nueva actividad <br />";
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

        if (participantes == null || participantes.length == 0) {
            validacion += "Se debe añadir al menos 1 participante en la actividad. <br />";
        }

        if (lstActividadesEmpleados != null && lstActividadesEmpleados.size() > 0) {
            for (ActividadesEmpleadosBean item : lstActividadesEmpleados) {
                try {
                    String NombrePersona = personas.consultarPersonas(String.valueOf(item.getEmpleado()), null, null, null, null, null, null, null, null, null).get(0).getNombre();
                    if (item.getFecha_estimada_inicio() == null || item.getFecha_estimada_inicio().toString().isEmpty()) {
                        validacion += "Para " + NombrePersona + ", El campo 'Fecha estimada de inicio' no debe estar vacío<br />";
                    }

                    if (item.getFecha_estimada_terminacion() == null || item.getFecha_estimada_terminacion().toString().isEmpty()) {
                        validacion += "Para " + NombrePersona + ", El campo 'Fecha estimada de terminación' no debe estar vacío <br />";
                    }

                    if (item.getTiempo_estimado() == null || String.valueOf(item.getFecha_estimada_terminacion()).equals("")) {
                        validacion += "Para " + NombrePersona + ", El campo 'Tiempo estimado' no debe estar vacío <br />";
                    }

                    if (item.getFecha_estimada_inicio() != null && item.getFecha_estimada_terminacion() != null) {
                        Date inicio = item.getFecha_estimada_inicio();
                        Date fin = item.getFecha_estimada_terminacion();
                        if (inicio.after(fin)) {
                            validacion += "Para " + NombrePersona + ", La fecha estimada de inicio no debe ser mayor que la fecha estimada de terminación <br />";
                        }
                    }

                    if (item.getFecha_real_inicio() != null && item.getFecha_real_terminacion() != null) {
                        Date inicio = item.getFecha_real_inicio();
                        Date fin = item.getFecha_real_terminacion();
                        if (inicio.after(fin)) {
                            validacion += "Para " + NombrePersona + ", La fecha real de inicio no debe ser mayor que la fecha real de terminación <br />";
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
                    Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return validacion;
    }

    public List<ActividadesBean> consultarActividades(Integer id, Integer version, String descripcion, Integer estado) {
        List<ActividadesBean> listaActividades = new ArrayList<>();
        try {
            listaActividades = actividadesDao.consultarActividades(id, version, descripcion, estado);
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
        List<ActividadesBean> listaActividades = consultarActividades(idActividad, null, null, null);
        if (listaActividades != null && !listaActividades.isEmpty()) {
            object.put("id", listaActividades.get(0).getId());
            object.put("version", listaActividades.get(0).getVersion());
            object.put("descripcion", listaActividades.get(0).getDescripcion());
            object.put("estado", listaActividades.get(0).getEstado());
        }
        return object;
    }

    public ActividadesBean consultarActividadI(Integer idActividad) {
        ActividadesBean actividad = new ActividadesBean();
        List<ActividadesBean> listaActividades = consultarActividades(idActividad, null, null, null);
        if (listaActividades != null && !listaActividades.isEmpty()) {
            actividad.setId(listaActividades.get(0).getId());
            actividad.setVersion(listaActividades.get(0).getVersion());
            actividad.setDescripcion(listaActividades.get(0).getDescripcion());
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
            //int eliminacionAct_Empl = actividadesEsfuerzosDao.eliminarActividadEsfuerzo(idActividad); //REVISAR SI AL ELIMINAR UNA PERSONA SE ELMINA DE EMPLEADOS ESFUERZOS
            int eliminacion = actividadesDao.eliminarActividad(idActividad);
            if (eliminacion == 0 && eliminacionAct_Esf == 0 /*&& eliminacionAct_Empl == 0*/) {
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
