package com.twg.negocio;

import com.twg.persistencia.beans.EstadosBean;
import com.twg.persistencia.beans.VersionesBean;
import com.twg.persistencia.daos.EstadosDao;
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
        try {
            version.setCosto(Double.valueOf(costo));
        } catch (Exception e) {
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

    public String validarDatos(Integer idVersion, String nombre, String fechaInicio, String fechaTerminacion, String alcance, String proyecto, String estado, String costo) {
        String validacion = "";
        if (nombre == null || nombre.isEmpty()) {
            validacion += "El campo 'Nombre' no debe estar vacío <br />";
        } else {
            if (nombre.length() > 30) {
                validacion += "El campo 'Nombre' no debe contener más de 30 caracteres, has dígitado " + nombre.length() + " caracteres <br />";
            } else {
                int proyectoInt = 0;
                try {
                    proyectoInt = Integer.valueOf(proyecto);
                } catch (Exception e) {
                }
                List<VersionesBean> listaVersiones = consultarVersiones(null, proyectoInt, nombre, true);
                if (listaVersiones != null && !listaVersiones.isEmpty()) {
                    if (idVersion == null || idVersion.intValue() != listaVersiones.get(0).getId()) {
                        validacion += "El valor ingresado en el campo 'Nombre' ya existe dentro del proyecto <br />";
                    }
                }
            }
        }
        if (fechaInicio == null || fechaInicio.isEmpty()) {
            validacion += "El campo 'Fecha de inicio' no debe estar vacío <br />";
        } else {
            try {
                sdf.parse(fechaInicio);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha de inicio' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }
        if (fechaTerminacion == null || fechaTerminacion.isEmpty()) {
            validacion += "El campo 'Fecha de fin' no debe estar vacío <br />";
        } else {
            try {
                sdf.parse(fechaTerminacion);
            } catch (ParseException e) {
                validacion += "El valor ingresado en el campo 'Fecha de fin' no se encuentra en el formato 'día/mes/año' <br />";
            }
        }
        if (proyecto == null || proyecto.isEmpty()) {
            validacion += "El campo 'Proyecto' no debe estar vacío <br />";
        } else {
            try {
                Integer.valueOf(proyecto);
            } catch (NumberFormatException e) {
                validacion += "El valor ingresado en el campo 'Proyecto' no corresponde al id de un proyecto <br />";
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
                if (idVersion != null) {//Versión existente: se valida contra estados prev y sig
                    List<VersionesBean> versionAntigua = consultarVersiones(idVersion, null, null, false);
                    if (versionAntigua != null && !versionAntigua.isEmpty()) {
                        if (versionAntigua.get(0).getEstado() != est) {
                            List<EstadosBean> listaEstados = eDao.consultarEstados(versionAntigua.get(0).getEstado(), null, null, null, null, null, null);
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
                } else {//Versión nueva: se valida contra estado final unicamente
                    List<EstadosBean> eBean = eDao.consultarEstados(null, "VERSIONES", null, null, null, "T", null);
                    if (eBean != null && !eBean.isEmpty()) {
                        if (eBean.get(0).getId() == est) {
                            validacion += "El estado seleccionado no es válido para una nueva versión <br />";
                        }
                    }
                }

            } catch (Exception e) {
            }
        }

        if (alcance == null || alcance.isEmpty()) {
            validacion += "El campo 'Alcance' no debe estar vacío <br />";
        } else {
            if (alcance.length() > 1000) {
                validacion += "El campo 'Alcance' no debe contener más de 1000 caracteres, has dígitado " + alcance.length() + " caracteres <br />";
            }
        }

        if (costo != null && !costo.isEmpty()) {
            if (!costo.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
                validacion += "El valor ingresado en el campo 'Tiempo invertido' solo debe contener números' <br />";
            }
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
        List<VersionesBean> listaVersiones = consultarVersiones(idVersion, null, null, false);
        if (listaVersiones != null && !listaVersiones.isEmpty()) {
            object.put("idVersion", listaVersiones.get(0).getId());
            object.put("idProyecto", listaVersiones.get(0).getProyecto());
            object.put("nombreVersion", listaVersiones.get(0).getNombre());
            object.put("estado", listaVersiones.get(0).getEstado());
            object.put("costo", listaVersiones.get(0).getCosto());
            object.put("fechaInicio", listaVersiones.get(0).getFechaInicio() != null ? sdf.format(listaVersiones.get(0).getFechaInicio()) : "");
            object.put("fechaFin", listaVersiones.get(0).getFechaTerminacion() != null ? sdf.format(listaVersiones.get(0).getFechaTerminacion()) : "");
            object.put("fechaProyecto", listaVersiones.get(0).getFechaInicioProyecto() != null ? sdf.format(listaVersiones.get(0).getFechaInicioProyecto()) : "");
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
