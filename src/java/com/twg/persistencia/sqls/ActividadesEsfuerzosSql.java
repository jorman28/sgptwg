package com.twg.persistencia.sqls;

/**
 *
 * @author Jorman
 */
public class ActividadesEsfuerzosSql {

    public String contarActividades_Esfuerzos() {
        return "SELECT COUNT(*) FROM actividades_esfuerzos";
    }

    public String consultarActividades_Esfuerzos(Integer id, Integer actividad, Integer empleado, String fecha, Double tiempo, String descripcion) {
        String sql = "SELECT * FROM actividades_esfuerzos WHERE fecha_eliminacion IS NULL ";
        if (id != null && !id.toString().isEmpty()) {
            sql += "AND id = " + id + " ";
        }
        if (actividad != null && !actividad.toString().isEmpty()) {
            sql += "AND actividad = " + actividad + " ";
        }
        if (empleado != null && !empleado.toString().isEmpty()) {
            sql += "AND empleado = " + empleado + " ";
        }
        if (fecha != null && !fecha.isEmpty()) {
            sql += "AND fecha = " + fecha + " ";
        }
        if (tiempo != null && !tiempo.toString().isEmpty()) {
            sql += "AND tiempo = " + tiempo + " ";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            sql += "AND descripcion = " + descripcion + " ";
        }
        return sql;
    }

    public String insertarActividad_Esfuerzo() {
        return "INSERT INTO actividades_esfuerzos (actividad, empleado, fecha, tiempo, descripcion) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarActividad_Esfuerzo() {
        return "UPDATE actividades_esfuerzos SET fecha = ?, tiempo = ?, descripcion = ? WHERE actividad = ? AND empleado = ?";
    }

    public String eliminarActividad_Esfuerzo() {
        return "UPDATE actividades_esfuerzos SET fecha_eliminacion = now() WHERE actividad = ?";
    }
    
    public String eliminarActividad_Esfuerzo(Integer idActividadEsfuerzo, Integer idActividad, Integer idEmpleado) {
        String sql = "DELETE FROM actividades_esfuerzos WHERE 1 = 1 ";
        if (idActividadEsfuerzo != null && !idActividadEsfuerzo.toString().isEmpty()) {
            sql += "AND id = " + idActividadEsfuerzo + " ";
        }
        if (idActividad != null && !idActividad.toString().isEmpty()) {
            sql += "AND actividad = " + idActividad + " ";
        }
        if (idEmpleado != null && !idEmpleado.toString().isEmpty()) {
            sql += "AND empleado = " + idEmpleado + " ";
        }
        return sql;
    }
    
    public String eliminarActividades_Esfuerzos() {
        return "DELETE FROM actividades_esfuerzos WHERE 1 = 1 AND actividad = ? AND empleado NOT IN (?)";
    }
}
