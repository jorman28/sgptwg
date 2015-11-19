package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class VersionesSql {

    public String contarVersiones() {
        return "SELECT COUNT(*) FROM VERSIONES";
    }

    public String consultarVersiones(Integer id, Integer idProyecto) {
        String sql = "  SELECT  "
                + "         ver.id, "
                + "         ver.nombre, "
                + "         ver.fecha_inicio, "
                + "         ver.fecha_terminacion, "
                + "         ver.alcance, "
                + "         ver.proyecto, "
                + "         pro.nombre AS nombre_proyecto, "
                + "         ver.estado, "
                + "         est.nombre AS nombre_estado "
                + "     FROM "
                + "         versiones ver "
                + "             INNER JOIN "
                + "         proyectos pro ON pro.id = ver.proyecto "
                + "             INNER JOIN "
                + "         estados est ON est.id = ver.estado "
                + "     WHERE 1 = 1 ";
        if (id != null) {
            sql += "        AND ver.id = " + id + " ";
        }
        if (idProyecto != null) {
            sql += "        AND pro.id = " + idProyecto + " ";
        }
        return sql;
    }

    public String insertarVersion() {
        return "INSERT INTO VERSIONES (nombre,fecha_inicio,fecha_terminacion,alcance,proyecto,estado) VALUES (?,?,?,?,?,?)";
    }

    public String actualizarVersion() {
        return "UPDATE VERSIONES SET nombre = ?, fecha_inicio = ?, fecha_terminacion = ?, alcance = ?, proyecto = ?, estado = ? WHERE id = ?";
    }

    public String eliminarVersion() {
        return "UPDATE VERSIONES SET fecha_eliminacion = ? WHERE id = ?";
    }
}
