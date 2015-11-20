package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class ProyectosSql {

    public String contarProyectos() {
        return "SELECT COUNT(*) FROM proyectos";
    }

    public String consultarProyectos(Integer id) {
        String sql = "SELECT * FROM proyectos WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        return sql;
    }

    public String insertarProyecto() {
        return "INSERT INTO proyectos (nombre,fecha_inicio) VALUES (?,?)";
    }

    public String actualizarProyecto() {
        return "UPDATE proyectos SET nombre = ?, fecha_inicio = ? WHERE id = ?";
    }

    public String eliminarProyecto() {
        return "UPDATE proyectos SET fecha_eliminacion = now() WHERE id = ?";
    }
}
