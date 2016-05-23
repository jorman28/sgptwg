package com.twg.persistencia.sqls;

import java.util.Date;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEsfuerzosSql {

    /**
     * Método encargado de retornar el SQL para consultar los esfuerzos por
     * actividad, aplicando filtros según los parámetros que lleguen distintos
     * de nulo.
     *
     * @param id
     * @param actividad
     * @param empleado
     * @param fecha
     * @param descripcion
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarActividadesEsfuerzos(Integer id, Integer actividad, Integer empleado, Date fecha, String descripcion) {
        String sql = "SELECT \n"
                + "    ae.id,\n"
                + "    ae.actividad,\n"
                + "    ae.empleado,\n"
                + "    ae.fecha,\n"
                + "    ae.tiempo,\n"
                + "    ae.descripcion\n"
                + "FROM\n"
                + "    actividades_esfuerzos ae\n"
                + "WHERE\n"
                + "    ae.fecha_eliminacion IS NULL ";
        if (id != null && !id.toString().isEmpty()) {
            sql += "AND id = " + id + " ";
        }
        if (actividad != null && actividad.intValue() != 0) {
            sql += "AND actividad = " + actividad + " ";
        }
        if (empleado != null && empleado.intValue() != 0) {
            sql += "AND empleado = " + empleado + " ";
        }
        if (fecha != null) {
            sql += "AND fecha = ? ";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            sql += "AND descripcion LIKE '%" + descripcion + "%' ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar un esfuerzo a una
     * actividad.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String insertarActividadEsfuerzo() {
        return "INSERT INTO actividades_esfuerzos (actividad, empleado, fecha, tiempo, descripcion) VALUES (?, ?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar los esfuerzos de una
     * actividad.
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String actualizarActividadEsfuerzo() {
        return "UPDATE actividades_esfuerzos SET fecha = ?, tiempo = ?, descripcion = ? WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente todos los
     * esfuerzos de una actividad, actualizando la fecha de eliminación con la
     * fecha actual.
     *
     * @param idActividadEsfuerzo
     * @param idActividad
     * @param idEmpleado
     * @return El SQL de la sentencia de base de datos
     */
    public String eliminarActividadEsfuerzo(Integer idActividadEsfuerzo, Integer idActividad, Integer idEmpleado) {
        String sql = "UPDATE actividades_esfuerzos SET fecha_eliminacion = now() WHERE 1 = 1 ";
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
}
