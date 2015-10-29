package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class ProyectosSql {

    public String contarProyectos() {
        return "SELECT COUNT(*) FROM PROYECTOS";
    }

    public String consultarProyectos() {
        return "SELECT * FROM PROYECTOS";
    }

    public String insertarProyecto() {
        return "INSERT INTO PROYECTOS (nombre,fecha_inicio,id_persona) VALUES (?,?,?)";
    }

    public String actualizarProyecto() {
        return "UPDATE PROYECTOS SET nombre = ?, fecha_inicio = ?, id_persona = ? WHERE id = ?";
    }

    public String eliminarProyecto() {
        return "UPDATE PROYECTOS SET fecha_eliminacion = ? WHERE id = ?";
    }
}
