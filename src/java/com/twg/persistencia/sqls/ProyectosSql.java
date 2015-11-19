package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class ProyectosSql {

    public String contarProyectos() {
        return "SELECT COUNT(*) FROM PROYECTOS";
    }

    public String consultarProyectos(Integer id) {
        String sql = "SELECT * FROM PROYECTOS WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        return sql;
    }

    public String insertarProyecto() {
        return "INSERT INTO PROYECTOS (nombre,fecha_inicio) VALUES (?,?)";
    }

    public String actualizarProyecto() {
        return "UPDATE PROYECTOS SET nombre = ?, fecha_inicio = ? WHERE id = ?";
    }

    public String eliminarProyecto() {
        return "UPDATE PROYECTOS SET fecha_eliminacion = now() WHERE id = ?";
    }
}
