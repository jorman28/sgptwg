package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEsfuerzosSql {

    /**
     * Método encargado de retornar el SQL para contar las actividades por
     * esfuerzos.
     * @return 
     */
    public String contarActividades_Esfuerzos() {
        return "SELECT COUNT(*) FROM actividades_esfuerzos";
    }

    /**
     * Método encargado de retornar el SQL para consultar los esfuerzos por
     * actividad, aplicando filtros según los parámetros que lleguen
     * distintos de nulo.
     * @param id
     * @param actividad
     * @param empleado
     * @param fecha
     * @param tiempo
     * @param descripcion
     * @return 
     */
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

    /**
     * Método encargado de retornar el SQL para insertar un esfuerzo a una 
     * actividad.
     * @return 
     */
    public String insertarActividad_Esfuerzo() {
        return "INSERT INTO actividades_esfuerzos (actividad, empleado, fecha, tiempo, descripcion) VALUES (?, ?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar los esfuerzos de 
     * una actividad.
     * @return 
     */
    public String actualizarActividad_Esfuerzo() {
        return "UPDATE actividades_esfuerzos SET fecha = ?, tiempo = ?, descripcion = ? WHERE actividad = ? AND empleado = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente todos los
     * esfuerzos de una actividad, actualizando la fecha de eliminación con 
     * la fecha actual.
     * @return 
     */
    public String eliminarActividad_Esfuerzo() {
        return "UPDATE actividades_esfuerzos SET fecha_eliminacion = now() WHERE actividad = ?";
    }
    
    /**
     * Método encargado de retornar el SQL para eliminar físicamente un esfuerzo
     * de una actividad.
     * @param idActividadEsfuerzo
     * @param idActividad
     * @param idEmpleado
     * @return 
     */
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
    
    /**
     * Método encargado de eliminar los esfuerzos de una actividad y un
     * empleado específico.
     * @return 
     */
    public String eliminarActividades_Esfuerzos() {
        return "DELETE FROM actividades_esfuerzos WHERE 1 = 1 AND actividad = ? AND empleado NOT IN (?)";
    }
}
