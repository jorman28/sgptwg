package com.twg.persistencia.sqls;

import java.util.Date;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ActividadesSql {

    /**
     * Constructor de la clase.
     */
    public ActividadesSql() {
    }

    /**
     * Método encargado de retornar el SQL para consultar las actividades,
     * aplicando diferentes filtros según los parámetros que lleguen distintos
     * de nulos.
     *
     * @param idActividad
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param responsable
     * @param estado
     * @return
     */
    public String consultarActividades(Integer idActividad, Integer proyecto, Integer version, String contiene, Date fecha, Integer estado, Integer responsable) {
        String sql = "SELECT DISTINCT\n"
                + "    a.id,\n"
                + "    a.version,\n"
                + "    a.nombre,\n"
                + "    a.descripcion,\n"
                + "    a.estado,\n"
                + "    e.nombre AS nombre_estado,\n"
                + "    v.nombre AS nombre_version,\n"
                + "    v.proyecto,\n"
                + "    p.nombre AS nombre_proyecto,\n"
                + "    (SELECT \n"
                + "            fecha_estimada_inicio\n"
                + "        FROM\n"
                + "            actividades_empleados\n"
                + "        WHERE\n"
                + "            actividad = a.id\n"
                + "        ORDER BY fecha_estimada_inicio ASC\n"
                + "        LIMIT 1) AS fecha_estimada_inicio,\n"
                + "    (SELECT \n"
                + "            fecha_estimada_terminacion\n"
                + "        FROM\n"
                + "            actividades_empleados\n"
                + "        WHERE\n"
                + "            actividad = a.id\n"
                + "        ORDER BY fecha_estimada_terminacion DESC\n"
                + "        LIMIT 1) AS fecha_estimada_terminacion,\n"
                + "    (SELECT \n"
                + "            CAST(IFNULL(SUM(tiempo_estimado), 0) AS DECIMAL (10 , 2 ))\n"
                + "        FROM\n"
                + "            actividades_empleados\n"
                + "        WHERE\n"
                + "            actividad = a.id\n"
                + "        ORDER BY fecha_estimada_terminacion DESC\n"
                + "        LIMIT 1) AS tiempo_estimado\n"
                + "FROM\n"
                + "    actividades a\n"
                + "        INNER JOIN\n"
                + "    estados e ON e.id = a.estado\n"
                + "        INNER JOIN\n"
                + "    versiones v ON v.id = a.version\n"
                + "        INNER JOIN\n"
                + "    proyectos p ON p.id = v.proyecto\n"
                + "        LEFT JOIN\n"
                + "    actividades_empleados ae ON ae.actividad = a.id\n"
                + "WHERE\n"
                + "    a.fecha_eliminacion IS NULL ";
        if (idActividad != null && idActividad.intValue() != 0) {
            sql += "AND a.id = " + idActividad + " ";
        }
        if (proyecto != null && proyecto.intValue() != 0) {
            sql += "AND v.proyecto = " + proyecto + " ";
        }
        if (version != null && version.intValue() != 0) {
            sql += "AND a.version = " + version + " ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND (a.nombre LIKE '%" + contiene + "%' OR a.descripcion LIKE '%" + contiene + "%') ";
        }
        if (fecha != null) {
            sql += "AND ? BETWEEN ae.fecha_estimada_inicio AND ae.fecha_estimada_terminacion ";
        }
        if (estado != null && estado.intValue() != 0) {
            sql += "AND a.estado = " + estado + " ";
        }
        if (responsable != null && responsable.intValue() != 0) {
            sql += "AND ae.empleado = " + responsable + " ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para consultar la última actividad
     * registrada en el sistema.
     *
     * @return
     */
    public String consultarUtimaActividad() {
        return "SELECT MAX(id) AS id FROM actividades";
    }

    /**
     * Método encargado de retornar el SQL para insertar una nueva actividad.
     *
     * @return
     */
    public String insertarActividad() {
        return "INSERT INTO actividades (version, nombre, descripcion, estado) VALUES (?, ?, ?, ?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar una actividad
     * existente.
     *
     * @return
     */
    public String actualizarActividad() {
        return "UPDATE actividades SET version = ?, nombre = ?, descripcion = ?, estado = ?  WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente una
     * actividad, actualizando la fecha de eliminación con la fecha actual.
     *
     * @return
     */
    public String eliminarActividad() {
        return "UPDATE actividades SET fecha_eliminacion = now() WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para consultar las actividades que se
     * encuentren en un estado diferente a finalizado.
     *
     * @param proyecto
     * @param version
     * @param persona
     * @return
     */
    public String actividadesPorEstados(Integer proyecto, Integer version, Integer persona) {
        String sql = "SELECT \n"
                + "    est.id AS id_estado,\n"
                + "    est.nombre AS estado,\n"
                + "    (SELECT \n"
                + "            COUNT(DISTINCT act.id)\n"
                + "        FROM\n"
                + "            actividades act\n"
                + "                LEFT JOIN\n"
                + "            versiones ver ON ver.id = act.version\n"
                + "                LEFT JOIN\n"
                + "            proyectos pro ON pro.id = ver.proyecto\n"
                + "                LEFT JOIN\n"
                + "            actividades_empleados actEmp ON actEmp.actividad = act.id\n"
                + "        WHERE\n"
                + "            act.estado = est.id ";
        if (proyecto != null && proyecto.intValue() != 0) {
            sql += "                AND pro.id = '" + proyecto + "'\n";
        }
        if (version != null && version.intValue() != 0) {
            sql += "                AND ver.id = '" + version + "'\n";
        }
        if (persona != null && persona.intValue() != 0) {
            sql += "                AND actEmp.empleado = '" + persona + "' ";
        }
        sql += "     ) AS actividades\n"
                + "FROM\n"
                + "    estados est\n"
                + "WHERE\n"
                + "    est.estado_final != 'T'\n"
                + "        AND est.tipo_estado = 'ACTIVIDADES';";
        return sql;
    }
}
