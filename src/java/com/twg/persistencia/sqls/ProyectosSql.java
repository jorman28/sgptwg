package com.twg.persistencia.sqls;

/**
 *
 * @author Pipe
 */
public class ProyectosSql {

    public String contarProyectos() {
        return "SELECT COUNT(*) FROM proyectos";
    }

    public String consultarProyectos(Integer id, String nombre, boolean nombreExacto) {
        String sql = "SELECT * FROM proyectos WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        if (nombre != null && !nombre.isEmpty()) {
            if (nombreExacto) {
                sql += "AND nombre = '" + nombre + "' ";
            } else {
                sql += "AND nombre LIKE '%" + nombre + "%' ";
            }
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

    public String consultarPersonasProyecto() {
        return "   SELECT \n"
                + "    per.id, per.tipo_documento, per.documento, per.nombres, per.apellidos, car.nombre AS cargo\n"
                + "FROM\n"
                + "    personas_proyectos pro\n"
                + "        INNER JOIN\n"
                + "    personas per ON per.id = pro.id_persona\n"
                + "        INNER JOIN\n"
                + "    cargos car ON per.cargo = car.id\n"
                + "WHERE\n"
                + "    pro.id_proyecto = ? "
                + "ORDER BY car.nombre ";
    }

    public String eliminarPersonasProyecto() {
        return "DELETE FROM personas_proyectos WHERE id_proyecto = ? ";
    }

    public String insertarPersonaProyecto() {
        return "INSERT INTO personas_proyectos (id_proyecto, id_persona) VALUES (?,?)";
    }
    
    public String consultarProyectosPorVersion(int idVersion) {
        return "SELECT DISTINCT p.id, p.nombre, p.fecha_inicio \n" +
               "FROM proyectos as p INNER JOIN versiones AS v ON (v.proyecto = p.id)\n" +
               "WHERE v.id = " + idVersion;
    }
}
