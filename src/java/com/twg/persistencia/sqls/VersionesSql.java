package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 * 
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class VersionesSql {

    /**
     * Método encargado de retornar el SQL para contar las versiones existentes.
     * 
     * @return 
     */
    public String contarVersiones() {
        return "SELECT COUNT(*) FROM versiones WHERE fecha_eliminacion IS NULL";
    }

    /**
     * Método encargado de consultar las versiones, aplicando diferentes filtros
     * según los parámetros que lleguen distintos de nulos.
     * 
     * @param id
     * @param idProyecto
     * @param nombre
     * @param nombreExacto
     * @return 
     */
    public String consultarVersiones(Integer id, Integer idProyecto, String nombre, boolean nombreExacto) {
        String sql = "  SELECT  "
                + "         ver.id, "
                + "         ver.nombre, "
                + "         ver.fecha_inicio, "
                + "         ver.fecha_terminacion, "
                + "         ver.alcance, "
                + "         ver.proyecto, "
                + "         ver.fecha_eliminacion, "
                + "         ver.costo, "
                + "         pro.nombre AS nombre_proyecto, "
                + "         ver.estado, "
                + "         est.nombre AS nombre_estado, "
                + "         pro.fecha_inicio AS fecha_proyecto "
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
        if (nombre != null && !nombre.isEmpty()) {
            if (nombreExacto) {
                sql += "    AND ver.nombre = '" + nombre + "' ";
            } else {
                sql += "    AND ver.nombre LIKE '%" + nombre + "%' ";
            }
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para insertar una nueva versión.
     * 
     * @return 
     */
    public String insertarVersion() {
        return "INSERT INTO versiones (nombre,fecha_inicio,fecha_terminacion,alcance,proyecto,estado,costo) VALUES (?,?,?,?,?,?,?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar una versión existente.
     * 
     * @return 
     */
    public String actualizarVersion() {
        return "UPDATE versiones SET nombre = ?, fecha_inicio = ?, fecha_terminacion = ?, alcance = ?, proyecto = ?, estado = ?, costo = ? WHERE id = ?";
    }

    /**
     * Método encargado de eliminar lógicamente una versión, actualizando 
     * la fecha de eliminación con la fecha actual.
     * 
     * @param idVersion
     * @param idProyecto
     * @return 
     */
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

    /**
     * Método encargado de retornar un SQL que consulta las versiones que tengan
     * una fecha de inicio inferior.
     * 
     * @return 
     */
    public String versionesPorFecha() {
        return "SELECT id, nombre FROM versiones WHERE proyecto = ? AND fecha_inicio < ?";
    }
}
