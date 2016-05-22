package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class ProyectosSql {

    /**
     * Método encargado de retornar el SQL para contar los proyectos existentes.
     *
     * @return
     */
    public String contarProyectos() {
        return "SELECT COUNT(*) FROM proyectos";
    }

    /**
     * Método encargado de consultar los proyectos, aplicando diferentes filtros
     * según los parámetros que reciba distintos de nulos.
     *
     * @param id
     * @param nombre
     * @param nombreExacto
     * @param idPersona
     * @return
     */
    public String consultarProyectos(Integer id, String nombre, boolean nombreExacto, Integer idPersona) {
        String sql = "SELECT DISTINCT\n"
                + "    pro.*\n"
                + "FROM\n"
                + "    proyectos pro\n"
                + "        LEFT JOIN\n"
                + "    personas_proyectos perpro ON pro.id = perpro.id_persona\n"
                + "WHERE\n"
                + "    fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND pro.id = " + id + " ";
        }
        if (nombre != null && !nombre.isEmpty()) {
            if (nombreExacto) {
                sql += "AND pro.nombre = '" + nombre + "' ";
            } else {
                sql += "AND pro.nombre LIKE '%" + nombre + "%' ";
            }
        }
        if (idPersona != null) {
            sql += "AND perpro.id_persona = " + idPersona + " ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar un nuevo proyecto.
     *
     * @return
     */
    public String insertarProyecto() {
        return "INSERT INTO proyectos (nombre,fecha_inicio) VALUES (?,?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar un proyecto
     * existente.
     *
     * @return
     */
    public String actualizarProyecto() {
        return "UPDATE proyectos SET nombre = ?, fecha_inicio = ? WHERE id = ?";
    }

    /**
     * Método encargado de eliminar lógicamente un proyecto, actualizando la
     * fecha de eliminación con la fecha actual.
     *
     * @return
     */
    public String eliminarProyecto() {
        return "UPDATE proyectos SET fecha_eliminacion = now() WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para consultar las personas que
     * pertenecen a un proyecto específico.
     *
     * @return
     */
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

    /**
     * Método encargado de eliminar físicamente las personas que pertenecen a un
     * proyecto.
     *
     * @return
     */
    public String eliminarPersonasProyecto() {
        return "DELETE FROM personas_proyectos WHERE id_proyecto = ? ";
    }

    /**
     * Método encargado de retornar el SQL para insertar peronas en un proyecto
     * específico.
     *
     * @return
     */
    public String insertarPersonaProyecto() {
        return "INSERT INTO personas_proyectos (id_proyecto, id_persona) VALUES (?,?)";
    }

    /**
     * Método encargado de retornar el SQL para consultar el proyecto de una
     * versión específica.
     *
     * @param idVersion
     * @return
     */
    public String consultarProyectosPorVersion(int idVersion) {
        return "SELECT DISTINCT p.id, p.nombre, p.fecha_inicio \n"
                + "FROM proyectos as p INNER JOIN versiones AS v ON (v.proyecto = p.id)\n"
                + "WHERE v.id = " + idVersion;
    }
}
