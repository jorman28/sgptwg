package com.twg.persistencia.sqls;

import java.util.Date;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesEmpleadosSql {

    /**
     * Constructor de la clase
     */
    public ActividadesEmpleadosSql() {
    }

    /**
     * Método encargado de retornar el SQL para consultar las actividades de un
     * empleado específico o los empleados asociados a una actividad.
     *
     * @param idActividad
     * @param idEmpleado
     * @param fechaEstimadaInicio
     * @param fechaEstimadaFin
     * @param evaluarEstado Indica si se evalúa el estado cerrado de la
     * actividad
     * @param actividadIgual Indica si el id de la actividad mandado como
     * parámetro debe ser igual o diferente
     * @param eliminados Indica si se tiene en cuenta los registros que ya
     * fueron eliminados logicamente
     * @return El SQL de la sentencia de base de datos
     */
    public String consultarActividadEmpleado(Integer idActividad, Integer idEmpleado, Date fechaEstimadaInicio, Date fechaEstimadaFin, boolean evaluarEstado, boolean actividadIgual, boolean eliminados) {
        String sql = "SELECT DISTINCT\n"
                + "    ae.actividad,\n"
                + "    a.nombre AS nombre_actividad,\n"
                + "    e.nombre AS nombre_estado,\n"
                + "    ae.empleado,\n"
                + "    p.tipo_documento,\n"
                + "    p.documento,\n"
                + "    CONCAT(p.nombres, ' ', p.apellidos) AS nombre_persona,\n"
                + "    c.nombre AS nombre_cargo,\n"
                + "    ae.fecha_estimada_inicio,\n"
                + "    ae.fecha_estimada_terminacion,\n"
                + "    ae.tiempo_estimado,\n"
                + "    (SELECT \n"
                + "            IFNULL(SUM(tiempo), 0)\n"
                + "        FROM\n"
                + "            actividades_esfuerzos\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "            AND actividad = ae.actividad\n"
                + "                AND empleado = ae.empleado) AS tiempo_invertido,\n"
                + "    ae.fecha_eliminacion\n"
                + "FROM\n"
                + "    actividades_empleados ae\n"
                + "        INNER JOIN\n"
                + "    personas p ON ae.empleado = p.id\n"
                + "        INNER JOIN\n"
                + "    cargos c ON c.id = p.cargo\n"
                + "        INNER JOIN\n"
                + "    actividades a ON a.id = ae.actividad\n"
                + "        INNER JOIN\n"
                + "    estados e ON e.id = a.estado\n"
                + "WHERE\n"
                + "    1 = 1\n";
        if (!eliminados) {
            sql += "    AND ae.fecha_eliminacion IS NULL\n";
        }
        if (idActividad != null && idActividad != 0) {
            if (actividadIgual) {
                sql += "        AND ae.actividad = " + idActividad + "\n";
            } else {
                sql += "        AND ae.actividad != " + idActividad + "\n";
            }
        }
        if (idEmpleado != null && idEmpleado != 0) {
            sql += "        AND ae.empleado = " + idEmpleado + "\n";
        }
        if (fechaEstimadaInicio != null && fechaEstimadaFin != null) {
            sql += "        AND ? BETWEEN ae.fecha_estimada_inicio AND ae.fecha_estimada_terminacion\n";
            sql += "        AND ? BETWEEN ae.fecha_estimada_inicio AND ae.fecha_estimada_terminacion\n";
        }
        if (evaluarEstado) {
            sql += "        AND e.estado_final != 'T';";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL de actualización de datos de una
     * persona asociada a una actividad
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String actualizarActividadEmpleado() {
        return "UPDATE actividades_empleados SET fecha_estimada_inicio = ?, fecha_estimada_terminacion = ?, tiempo_estimado = ?, fecha_eliminacion = null WHERE actividad = ? AND empleado = ?";
    }

    /**
     * Método encargado de retornar el SQL para insertar una actividad a un
     * empleado específico
     *
     * @return El SQL de la sentencia de base de datos
     */
    public String insertarActividadEmpleado() {
        return "INSERT INTO actividades_empleados (actividad, empleado) VALUES (?,?)";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente una
     * actividad de un empleado, actualizando la fecha de eliminación con la
     * fecha actual.
     *
     * @param idActividad
     * @param idEmpleado
     * @return El SQL de la sentencia de base de datos
     */
    public String eliminarActividadEmpleado(Integer idActividad, Integer idEmpleado) {
        String sql = "UPDATE actividades_empleados SET fecha_eliminacion = now() WHERE 1 = 1 ";
        if (idActividad != null) {
            sql += "AND actividad = " + idActividad + " ";
        }
        if (idEmpleado != null) {
            sql += "AND empleado = " + idEmpleado + " ";
        }
        return sql;
    }

}
