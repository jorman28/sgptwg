package com.twg.persistencia.sqls;

import java.util.Date;

/**
 *
 * @author Jorman Rincón
 * @author Andrés Giraldo
 */
public class ActividadesSql {

    public ActividadesSql() {
    }

    public String consultarActividades() {
        return "SELECT * FROM actividades WHERE fecha_eliminacion IS NULL ";
    }

    public String consultarActividades(Integer id, Integer version, String descripcion, Date fecha_estimada_inicio, Date fecha_estimada_terminacion, Date fecha_real_inicio, Date fecha_real_terminacion, Integer tiempo_estimado, Integer tiempo_invertido, Integer estado) {
        String sql = "SELECT * FROM actividades WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        if (version != null && !version.toString().isEmpty()) {
            sql += "AND version = '" + version + "' ";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            sql += "AND descripcion LIKE '%" + descripcion + "%' ";
        }
        if (fecha_estimada_inicio != null && !fecha_estimada_inicio.toString().isEmpty()) {
            sql += "AND fecha_estimada_inicio = '" + fecha_estimada_inicio + "' ";
        }
        if (fecha_estimada_terminacion != null && !fecha_estimada_terminacion.toString().isEmpty()) {
            sql += "AND fecha_estimada_terminacion = '" + fecha_estimada_terminacion + "' ";
        }
        if (fecha_real_inicio != null && !fecha_real_inicio.toString().isEmpty()) {
            sql += "AND fecha_real_inicio = '" + fecha_real_inicio + "' ";
        }
        if (fecha_real_terminacion != null && !fecha_real_terminacion.toString().isEmpty()) {
            sql += "AND fecha_real_terminacion = '" + fecha_real_terminacion + "' ";
        }
        if (tiempo_estimado != null && !tiempo_estimado.toString().isEmpty()) {
            sql += "AND tiempo_estimado = '" + tiempo_estimado + "' ";
        }
        if (tiempo_invertido != null && !tiempo_invertido.toString().isEmpty()) {
            sql += "AND tiempo_invertido = '" + tiempo_invertido + "' ";
        }
        if (estado != null && !estado.toString().isEmpty()) {
            sql += "AND estado = '" + estado + "' ";
        }
        return sql;
    }

    public String consultarActividades(String id, Integer version, String descripcion, String fecha, Integer estado) {
        String sql = "SELECT a.id, a.version, a.descripcion, a.fecha_estimada_inicio, a.fecha_estimada_terminacion, "
                + "a.fecha_real_inicio, a.fecha_real_terminacion, a.tiempo_estimado, a.tiempo_invertido, a.estado, e.nombre as nombree, v.nombre as nombrev "
                + "FROM actividades a INNER JOIN versiones v ON v.id=a.version INNER JOIN estados e ON e.id=a.estado"
                + " WHERE a.fecha_eliminacion IS NULL ";
        if (id != null && !id.equals("")) {
            sql += "AND a.id in (" + id + ") ";
        }
        if (version != null && !version.toString().isEmpty()) {
            sql += "AND a.version = '" + version + "' ";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            sql += "AND a.descripcion LIKE '%" + descripcion + "%' ";
        }
        if (fecha != null && !fecha.isEmpty()) {
            sql += "AND (a.fecha_estimada_inicio = '" + fecha + "' OR a.fecha_estimada_terminacion = '"
                    + fecha + "' OR a.fecha_real_inicio = '" + fecha + "' OR a.fecha_real_terminacion = '" + fecha + "')";
        }
        if (estado != null && !estado.toString().isEmpty()) {
            sql += "AND a.estado = '" + estado + "' ";
        }
        return sql;
    }

    public String consultarUtimaActividad() {
        return "SELECT MAX(id) AS id FROM actividades";
    }

    public String insertarActividad() {
        return "INSERT INTO actividades (descripcion, fecha_estimada_inicio, fecha_estimada_terminacion, fecha_real_inicio, fecha_real_terminacion, tiempo_estimado, tiempo_invertido, version, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarActividad() {
        return "UPDATE actividades SET  descripcion = ?, fecha_estimada_inicio=?, fecha_estimada_terminacion=?, fecha_real_inicio=?, fecha_real_terminacion=?, tiempo_estimado=?, tiempo_invertido=?, version=?, estado=?  WHERE id = ?";
    }

    public String eliminarActividad() {
        return "UPDATE actividades SET fecha_eliminacion = now() WHERE id = ?";
    }

    public String actividadesPorEstados(Integer proyecto, Integer version, Integer persona) {
        String sql = "SELECT \n"
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
            sql += "                AND pro.id = ''\n";
        }
        if (version != null && version.intValue() != 0) {
            sql += "                AND ver.id = ''\n";
        }
        if (persona != null && persona.intValue() != 0) {
            sql += "                AND actEmp.empleado = '' ";
        }
        sql += "     ) AS actividades\n"
                + "FROM\n"
                + "    estados est\n"
                + "WHERE\n"
                + "    est.estado_final != 'T'\n"
                + "        AND est.tipo_estado = 'ACTIVIDADES';";
        return sql;
    }

    public static void main(String[] args) {
        System.out.println(new ActividadesSql().actividadesPorEstados(null, null, null));
    }
}
