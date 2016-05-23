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
     * @param limite
     * @return
     */
    public String consultarActividades(Integer idActividad, Integer proyecto, Integer version, String contiene, Date fecha, Integer estado, Integer responsable, String limite) {
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
                + "            fecha_eliminacion IS NULL\n"
                + "            AND actividad = a.id AND fecha_estimada_inicio IS NOT NULL\n"
                + "        ORDER BY fecha_estimada_inicio ASC\n"
                + "        LIMIT 1) AS fecha_estimada_inicio,\n"
                + "    (SELECT \n"
                + "            fecha_estimada_terminacion\n"
                + "        FROM\n"
                + "            actividades_empleados\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "            AND actividad = a.id AND fecha_estimada_terminacion IS NOT NULL\n"
                + "        ORDER BY fecha_estimada_terminacion DESC\n"
                + "        LIMIT 1) AS fecha_estimada_terminacion,\n"
                + "    (SELECT \n"
                + "            fecha\n"
                + "        FROM\n"
                + "            actividades_esfuerzos\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "                AND actividad = a.id\n"
                + "        ORDER BY fecha ASC\n"
                + "        LIMIT 1) AS fecha_real_inicio,\n"
                + "    (SELECT \n"
                + "            fecha\n"
                + "        FROM\n"
                + "            actividades_esfuerzos\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "                AND actividad = a.id\n"
                + "        ORDER BY fecha DESC\n"
                + "        LIMIT 1) AS ultima_modificacion,\n"
                + "    (SELECT \n"
                + "            CAST(IFNULL(SUM(tiempo_estimado), 0) AS DECIMAL (10 , 2 ))\n"
                + "        FROM\n"
                + "            actividades_empleados\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "            AND actividad = a.id\n"
                + "        ORDER BY fecha_estimada_terminacion DESC\n"
                + "        LIMIT 1) AS tiempo_estimado,\n"
                + "    (SELECT \n"
                + "            IFNULL(SUM(tiempo), 0)\n"
                + "        FROM\n"
                + "            actividades_esfuerzos\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "            AND actividad = a.id) AS tiempo_invertido\n"
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
        if (idActividad != null && idActividad != 0) {
            sql += "AND a.id = " + idActividad + " ";
        }
        if (proyecto != null && proyecto != 0) {
            sql += "AND v.proyecto = " + proyecto + " ";
        }
        if (version != null && version != 0) {
            sql += "AND a.version = " + version + " ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND (a.nombre LIKE '%" + contiene + "%' OR a.descripcion LIKE '%" + contiene + "%') ";
        }
        if (fecha != null) {
            sql += "AND ? BETWEEN ae.fecha_estimada_inicio AND ae.fecha_estimada_terminacion ";
        }
        if (estado != null && estado != 0) {
            sql += "AND a.estado = " + estado + " ";
        }
        if (responsable != null && responsable != 0) {
            sql += "AND ae.empleado = " + responsable + " ";
        }
        if (limite != null && !limite.isEmpty()) {
            sql += "LIMIT " + limite + " ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para el conteo de las actividades
     * relacionadas con los filtros ingresados
     *
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param estado
     * @param responsable
     * @return
     */
    public String contarActividades(Integer proyecto, Integer version, String contiene, Date fecha, Integer estado, Integer responsable) {
        String sql = "SELECT COUNT(*) AS cantidad_actividades\n"
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
        if (proyecto != null && proyecto != 0) {
            sql += "AND v.proyecto = " + proyecto + " ";
        }
        if (version != null && version != 0) {
            sql += "AND a.version = " + version + " ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND (a.nombre LIKE '%" + contiene + "%' OR a.descripcion LIKE '%" + contiene + "%') ";
        }
        if (fecha != null) {
            sql += "AND ? BETWEEN ae.fecha_estimada_inicio AND ae.fecha_estimada_terminacion ";
        }
        if (estado != null && estado != 0) {
            sql += "AND a.estado = " + estado + " ";
        }
        if (responsable != null && responsable != 0) {
            sql += "AND ae.empleado = " + responsable + " ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL con el cual se hará la consulta para
     * el reporte de actividades detallado
     *
     * @param proyecto
     * @param version
     * @param contiene
     * @param fecha
     * @param estado
     * @param responsable
     * @return
     */
    public String detalleActividades(Integer proyecto, Integer version, String contiene, Date fecha, Integer estado, Integer responsable) {
        String sql = "SELECT DISTINCT\n"
                + "    pro.id AS proyecto,\n"
                + "    pro.nombre AS nombre_proyecto,\n"
                + "    ver.id AS version,\n"
                + "    ver.nombre AS nombre_version,\n"
                + "    act.id AS actividad,\n"
                + "    act.nombre AS nombre_actividad,\n"
                + "    est.nombre AS nombre_estado,\n"
                + "    CONCAT(per.nombres, ' ', per.apellidos) AS nombre_persona,\n"
                + "    CONCAT(per.tipo_documento, ' ', per.documento) AS documento,\n"
                + "    ae.fecha_estimada_inicio,\n"
                + "    ae.fecha_estimada_terminacion,\n"
                + "    IFNULL(ae.tiempo_estimado, 0.00) AS tiempo_estimado,\n"
                + "    (SELECT \n"
                + "            CAST(IFNULL(SUM(tiempo), 0) AS DECIMAL (10 , 2 ))\n"
                + "        FROM\n"
                + "            actividades_esfuerzos\n"
                + "        WHERE\n"
                + "            fecha_eliminacion IS NULL\n"
                + "                AND actividad = act.id\n"
                + "                AND empleado = ae.empleado) AS tiempo_invertido\n"
                + "FROM\n"
                + "    actividades act\n"
                + "        INNER JOIN\n"
                + "    estados est ON est.id = act.estado\n"
                + "        INNER JOIN\n"
                + "    versiones ver ON ver.id = act.version\n"
                + "        INNER JOIN\n"
                + "    proyectos pro ON pro.id = ver.proyecto\n"
                + "        LEFT JOIN\n"
                + "    actividades_empleados ae ON ae.actividad = act.id\n"
                + "        LEFT JOIN\n"
                + "    personas per ON per.id = ae.empleado\n"
                + "WHERE\n"
                + "    act.fecha_eliminacion IS NULL\n"
                + "        AND ae.fecha_eliminacion IS NULL\n";
        if (proyecto != null && proyecto != 0) {
            sql += "AND ver.proyecto = " + proyecto + " ";
        }
        if (version != null && version != 0) {
            sql += "AND act.version = " + version + " ";
        }
        if (contiene != null && !contiene.isEmpty()) {
            sql += "AND (act.nombre LIKE '%" + contiene + "%' OR act.descripcion LIKE '%" + contiene + "%') ";
        }
        if (fecha != null) {
            sql += "AND ? BETWEEN ae.fecha_estimada_inicio AND ae.fecha_estimada_terminacion ";
        }
        if (estado != null && estado != 0) {
            sql += "AND act.estado = " + estado + " ";
        }
        if (responsable != null && responsable != 0) {
            sql += "AND ae.empleado = " + responsable + " ";
        }
        sql += "ORDER BY nombre_proyecto, proyecto, nombre_version, version, nombre_actividad, actividad, nombre_persona\n";
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
                + "            act.estado = est.id "
                + "            AND act.fecha_eliminacion IS NULL ";
        if (proyecto != null && proyecto != 0) {
            sql += "                AND pro.id = '" + proyecto + "'\n";
        }
        if (version != null && version != 0) {
            sql += "                AND ver.id = '" + version + "'\n";
        }
        if (persona != null && persona != 0) {
            sql += "                AND actEmp.empleado = '" + persona + "' AND actEmp.fecha_eliminacion IS NULL ";
        }
        sql += "     ) AS actividades\n"
                + "FROM\n"
                + "    estados est\n"
                + "WHERE\n"
                + "    est.estado_final != 'T'\n"
                + "        AND est.tipo_estado = 'ACTIVIDADES' "
                + "        AND est.fecha_eliminacion IS NULL;";
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para consultar el tiempo estimado vs
     * el tiempo invertido en las actividades correspondientes a un proyecto con
     * sus versiones
     *
     * @param proyecto
     * @param version
     * @param responsable
     * @return
     */
    public String consolidadoActividades(Integer proyecto, Integer version, Integer responsable) {
        String sql = "SELECT \n"
                + "    pro.id AS proyecto,\n"
                + "    pro.nombre AS nombre_proyecto,\n"
                + "    ver.id AS version,\n"
                + "    ver.nombre AS nombre_version,\n"
                + "    IFNULL(SUM(aemp.tiempo_estimado), 0) AS tiempo_estimado,\n"
                + "    IFNULL(SUM((SELECT \n"
                + "                    SUM(tiempo)\n"
                + "                FROM\n"
                + "                    actividades_esfuerzos\n"
                + "                WHERE\n"
                + "                    fecha_eliminacion IS NULL\n"
                + "                        AND actividad = act.id"
                + "                        AND empleado = aemp.empleado)), 0) AS tiempo_invertido\n"
                + "FROM\n"
                + "    actividades act\n"
                + "        INNER JOIN\n"
                + "    versiones ver ON ver.id = act.version\n"
                + "        INNER JOIN\n"
                + "    proyectos pro ON pro.id = ver.proyecto\n"
                + "        LEFT JOIN\n"
                + "    actividades_empleados aemp ON aemp.actividad = act.id\n"
                + "WHERE\n"
                + "    act.fecha_eliminacion IS NULL\n"
                + "        AND aemp.fecha_eliminacion IS NULL\n";
        if (proyecto != null) {
            sql += "        AND pro.id = " + proyecto + "\n";
        }
        if (version != null) {
            sql += "        AND ver.id = " + version + "\n";
        }
        if (responsable != null) {
            sql += "        AND aemp.empleado = " + responsable + "\n";
        }
        if (proyecto != null && proyecto != 0) {
            sql += "GROUP BY ver.id";
        } else {
            sql += "GROUP BY pro.id";
        }
        return sql;
    }
}
