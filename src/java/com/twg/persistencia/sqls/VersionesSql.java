package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class VersionesSql {

    public String contarVersiones() {
        return "SELECT COUNT(*) FROM VERSIONES WHERE fecha_eliminacion IS NULL";
    }

    public String consultarVersiones(Integer id, Integer idProyecto) {
        String sql = "  SELECT  "
                + "         ver.id, "
                + "         ver.nombre, "
                + "         ver.fecha_inicio, "
                + "         ver.fecha_terminacion, "
                + "         ver.alcance, "
                + "         ver.proyecto, "
                + "         ver.fecha_eliminacion, "
                + "         pro.nombre AS nombre_proyecto, "
                + "         ver.estado, "
                + "         est.nombre AS nombre_estado "
                + "     FROM "
                + "         versiones ver "
                + "             INNER JOIN "
                + "         proyectos pro ON pro.id = ver.proyecto "
                + "             INNER JOIN "
                + "         estados est ON est.id = ver.estado "
                + "     WHERE 1 = 1 AND ver.fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "        AND ver.id = " + id + " ";
        }
        if (idProyecto != null) {
            sql += "        AND pro.id = " + idProyecto + " ";
        }
        return sql;
    }

    public String insertarVersion() {
        return "INSERT INTO versiones (nombre,fecha_inicio,fecha_terminacion,alcance,proyecto,estado) VALUES (?,?,?,?,?,?)";
    }

    public String actualizarVersion() {
        return "UPDATE versiones SET nombre = ?, fecha_inicio = ?, fecha_terminacion = ?, alcance = ?, proyecto = ?, estado = ? WHERE id = ?";
    }

    public String eliminarVersion(Integer idVersion, Integer idProyecto) {
        String sql = "UPDATE versiones SET fecha_eliminacion = now() WHERE 1 = 1 ";
        if (idVersion != null) {
            sql += "AND id = " + idVersion + " ";
        }
        if (idProyecto != null) {
            sql += "AND proyecto = " + idProyecto + " ";
        }
        return sql;
    }
}
